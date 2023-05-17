package com.tg.user.user.domain;

import com.tg.user.user.domain.dto.UserInformationResponseDto;

public interface UserService {
    Long login(String code);

    UserInformationResponseDto getUserInformation(Long targetUserId);
}
