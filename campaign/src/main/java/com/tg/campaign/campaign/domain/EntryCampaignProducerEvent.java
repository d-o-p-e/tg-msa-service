package com.tg.campaign.campaign.domain;

import com.tg.campaign.common.Event;

public interface EntryCampaignProducerEvent {
    void sendMessage(Event event);
}
