package com.tg.user.user.domain;

import com.tg.user.user.domain.dto.KakaoUserInformation;
import com.tg.user.user.domain.dto.UserInformationResponseDto;

public interface UserService {
    Long login(String code);

    User getOrCreateUser(KakaoUserInformation kakaoUserInformation);

    UserInformationResponseDto getUserInformation(Long targetUserId);

    Long testLogin(String code);
}
