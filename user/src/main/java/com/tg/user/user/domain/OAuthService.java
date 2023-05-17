package com.tg.user.user.domain;

import com.tg.user.user.domain.dto.KakaoUserInformation;

public interface OAuthService {
    KakaoUserInformation requestUserInformation(String code);
}
