package com.tg.campaign.user.domain.dto;

import com.tg.campaign.common.Event;
import com.tg.campaign.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateEventDto extends Event {

    private Long userId;
    private String nickname;
    private String email;
    private String profileImageUrl;

    public User toEntity() {
        return User.builder()
            .id(userId)
            .nickname(nickname)
            .email(email)
            .build();
    }
}
