package com.tg.campaign.campaign.domain.dto;

import com.tg.campaign.common.Event;
import com.tg.campaign.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EntryCampaignEventDto extends Event {

    private Long userId;
    private String nickname;
    private String email;

    public EntryCampaignEventDto(User user) {
        super();
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }
}
