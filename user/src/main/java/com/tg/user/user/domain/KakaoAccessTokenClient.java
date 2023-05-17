package com.tg.user.user.domain;

import com.tg.user.user.domain.dto.KakaoAccessTokenResponseDto;

public interface KakaoAccessTokenClient {
    KakaoAccessTokenResponseDto call(
            String contentType,
            String grantType,
            String clientId,
            String redirectUri,
            String code
    );
}
