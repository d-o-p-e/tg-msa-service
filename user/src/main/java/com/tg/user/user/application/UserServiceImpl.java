package com.tg.user.user.application;

import com.tg.user.user.domain.OAuthService;
import com.tg.user.user.domain.User;
import com.tg.user.user.domain.UserRepository;
import com.tg.user.user.domain.UserService;
import com.tg.user.user.domain.dto.KakaoUserInformation;
import com.tg.user.user.domain.dto.UserCreateEventDto;
import com.tg.user.user.domain.dto.UserInformationResponseDto;
import com.tg.user.user.infra.UserCreateKafkaProducerEvent;
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

    @Override
    public Long login(String code) {
        KakaoUserInformation kakaoUserInformation = oAuthService.requestUserInformation(code);
        User user = getOrCreateUser(kakaoUserInformation);
        return user.getId();
    }

    @Transactional
    public User getOrCreateUser(KakaoUserInformation kakaoUserInformation) {
        Optional<User> optionalUser = userRepository.findByProviderId(kakaoUserInformation.getProviderId());
        if (optionalUser.isPresent()) {
            log.info("기존 유저 로그인: {}", optionalUser.get().getId());
            return optionalUser.get();
        }

        User user = userRepository.save(User.builder()
                .providerId(kakaoUserInformation.getProviderId())
                .build());
        log.info("신규 유저 가입: {}", user.getId());
        //TODO mapper 객체로 변환: Mapstruct
        userCreateKafkaProducerEvent.sendMessage(new UserCreateEventDto(user));
        return user;
    }

    @Override
    public UserInformationResponseDto getUserInformation(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        int workoutCount = 0;
        int algorithmCount = 0;
        int earlyBirdCount = 0;
        return UserInformationResponseDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImageUrl())
                .workoutCount(workoutCount)
                .algorithmCount(algorithmCount)
                .earlyBirdCount(earlyBirdCount)
                .build();
    }
}
