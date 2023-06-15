package com.tg.user.user.application;

import com.tg.user.user.domain.KakaoAccessTokenClient;
import com.tg.user.user.domain.KakaoInformationClient;
import com.tg.user.user.domain.OAuthService;
import com.tg.user.user.domain.dto.KakaoAccessTokenResponseDto;
import com.tg.user.user.domain.dto.KakaoUserInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {

    private final KakaoAccessTokenClient kakaoAccessTokenClient;
    private final KakaoInformationClient kakaoInformationClient;

    @Value("${oauth.kakao.client-id}")
    private String kakaoClientId;

    @Value("${oauth.kakao.redirect-uri}")
    private String redirectUri;

    // 카카오 토큰 발급
    public KakaoUserInformation requestUserInformation(String code) {
        KakaoAccessTokenResponseDto result = kakaoAccessTokenClient.call(
                "application/x-www-form-urlencoded",
                "authorization_code",
                kakaoClientId,
                redirectUri,
                code
        );
        return kakaoInformationClient.call(
                "application/x-www-form-urlencoded;charset=utf-8",
                "Bearer " + result.getAccessToken()
        );
    }
}
