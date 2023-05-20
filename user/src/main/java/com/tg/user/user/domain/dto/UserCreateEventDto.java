package com.tg.user.user.domain.dto;

import com.tg.user.common.Event;
import com.tg.user.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateEventDto extends Event {

    private Long userId;
    private String nickname;
    private String email;
    private String profileImageUrl;

    public UserCreateEventDto(User user) {
        super();
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.profileImageUrl = user.getProfileImageUrl();
    }
}
