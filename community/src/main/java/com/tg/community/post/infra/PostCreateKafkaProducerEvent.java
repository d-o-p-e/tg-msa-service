package com.tg.community.post.infra;

import com.tg.community.common.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCreateKafkaProducerEvent {

    private static final String TOPIC = "post-creation";
    private final KafkaTemplate<String, Event> kafkaTemplate;

    public void sendMessage(Event event) {
        this.kafkaTemplate.send(TOPIC, event);
    }
}
