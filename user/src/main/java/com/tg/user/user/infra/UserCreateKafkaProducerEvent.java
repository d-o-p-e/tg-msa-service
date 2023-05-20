package com.tg.user.user.infra;

import com.tg.user.common.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCreateKafkaProducerEvent {

    private static final String TOPIC = "user-creation";
    private final KafkaTemplate<String, Event> kafkaTemplate;

    public void sendMessage(Event event) {
        this.kafkaTemplate.send(TOPIC, event);
    }
}