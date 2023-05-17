package com.tg.user.user.domain;

import com.tg.user.user.domain.dto.KakaoUserInformation;

public interface KakaoInformationClient {
    KakaoUserInformation call(
            String contentType,
            String accessToken
    );
}
