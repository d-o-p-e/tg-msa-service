package com.tg.campaign.auth.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class SessionUserVo {
    private Long id;
    private String nickname;
    private String email;
    private String profileImageUrl;
}
