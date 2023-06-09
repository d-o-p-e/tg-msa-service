package com.tg.community.user.domain.dto;

import com.tg.community.common.Event;
import com.tg.community.user.domain.User;
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
            .profileImageUrl(profileImageUrl)
            .build();
    }
}
