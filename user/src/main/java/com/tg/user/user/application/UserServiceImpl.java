package com.tg.user.user.application;

import com.tg.user.user.domain.OAuthService;
import com.tg.user.user.domain.User;
import com.tg.user.user.domain.UserRepository;
import com.tg.user.user.domain.UserService;
import com.tg.user.user.domain.dto.KakaoUserInformation;
import com.tg.user.user.domain.dto.UserCreateEventDto;
import com.tg.user.user.domain.dto.UserInformationResponseDto;
import com.tg.user.user.domain.dto.UserPostSummaryResponseDto;
import com.tg.user.user.infra.UserCreateKafkaProducerEvent;
import com.tg.user.user.infra.UserPostSummaryFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OAuthService oAuthService;
    private final UserCreateKafkaProducerEvent userCreateKafkaProducerEvent;
    private final UserPostSummaryFeignClient userPostSummaryFeignClient;

    //로그인
    @Override
    public Long login(String code) {
        KakaoUserInformation kakaoUserInformation = oAuthService.requestUserInformation(code);
        User user = getOrCreateUser(kakaoUserInformation);
        return user.getId();
    }

    // 유저 생성
    @Transactional
    public User getOrCreateUser(KakaoUserInformation kakaoUserInformation) {
        Optional<User> optionalUser = userRepository.findByProviderId(kakaoUserInformation.getProviderId());
        if (optionalUser.isPresent()) {
            log.info("기존 유저 로그인: {}, {}", optionalUser.get().getId(), kakaoUserInformation);
            return optionalUser.get();
        }

        User user = userRepository.save(User.builder()
                .providerId(kakaoUserInformation.getProviderId())
                .email(kakaoUserInformation.getKakao_account().getEmail())
                .nickname(kakaoUserInformation.getProperties().getNickname())
                .profileImageUrl(kakaoUserInformation.getProperties().getProfileImage())
                .build());
        if(user.getEmail() == null) {
            user.updateNickname("익명의 유저");
        }
        log.info("신규 유저 가입: {}, {}", user.getId(), kakaoUserInformation);
        //TODO mapper 객체로 변환: Mapstruct
        userCreateKafkaProducerEvent.sendMessage(new UserCreateEventDto(user));
        return user;
    }

    // 유저 정보 조회
    @Override
    public UserInformationResponseDto getUserInformation(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        UserPostSummaryResponseDto userPostSummaryResponseDto = userPostSummaryFeignClient.call(userId);
        return UserInformationResponseDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImageUrl())
                .workoutCount(userPostSummaryResponseDto.getWorkoutCount())
                .algorithmCount(userPostSummaryResponseDto.getAlgorithmCount())
                .earlyBirdCount(userPostSummaryResponseDto.getEarlyBirdCount())
                .build();
    }
}
