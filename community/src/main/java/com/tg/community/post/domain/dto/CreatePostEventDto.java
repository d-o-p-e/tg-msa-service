package com.tg.community.post.domain.dto;

import com.tg.community.common.Event;
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
