package com.tg.user.user.application;

import com.tg.user.user.domain.OAuthService;
import com.tg.user.user.domain.User;
import com.tg.user.user.domain.UserRepository;
import com.tg.user.user.domain.UserService;
import com.tg.user.user.domain.dto.KakaoUserInformation;
import com.tg.user.user.domain.dto.UserInformationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OAuthService oAuthService;

    @Override
    public Long login(String code) {
        KakaoUserInformation kakaoUserInformation = oAuthService.requestUserInformation(code);
        User user = getOrCreateUser(kakaoUserInformation);
        return user.getId();
    }

    @Transactional
    public User getOrCreateUser(KakaoUserInformation kakaoUserInformation) {
        Optional<User> optionalUser = userRepository.findByProviderId(kakaoUserInformation.getProviderId());
        return optionalUser.orElseGet(() -> userRepository.save(User.builder()
                .providerId(kakaoUserInformation.getProviderId())
                .build()));
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
