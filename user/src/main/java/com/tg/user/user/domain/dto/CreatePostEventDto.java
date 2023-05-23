package com.tg.user.user.domain.dto;

import com.tg.user.common.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePostEventDto extends Event {

    private Long userId;

    public CreatePostEventDto(Long userId) {
        super();
        this.userId = userId;
    }
}
