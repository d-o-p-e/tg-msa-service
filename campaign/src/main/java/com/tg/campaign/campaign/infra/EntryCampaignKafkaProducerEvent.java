package com.tg.campaign.campaign.infra;

import com.tg.campaign.campaign.domain.EntryCampaignProducerEvent;
import com.tg.campaign.common.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntryCampaignKafkaProducerEvent implements EntryCampaignProducerEvent {

    private static final String TOPIC = "campaign-entry";
    private final KafkaTemplate<String, Event> kafkaTemplate;

    @Override
    public void sendMessage(Event event) {
        this.kafkaTemplate.send(TOPIC, event);
    }
}