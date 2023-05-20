package com.tg.user.user.infra;

import com.tg.user.user.domain.User;
import com.tg.user.user.domain.UserRepository;
import com.tg.user.user.domain.dto.EntryCampaignEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntryCampaignKafkaEventListener {

    private final UserRepository userRepository;

    @KafkaListener(topics = "user-creation", groupId = "user-community-0", containerFactory = "createUserMessageListener")
    public void userCreateEventListener(EntryCampaignEventDto entryCampaignEventDto){
        User user = userRepository.findById(entryCampaignEventDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        user.deductMileage();
    }
}