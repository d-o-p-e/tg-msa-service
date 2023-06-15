package com.tg.campaign.user.infra;

import com.tg.campaign.user.domain.UserRepository;
import com.tg.campaign.user.domain.dto.UserCreateEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCreateKafkaEventListener {

    private final UserRepository userRepository;

    //유저 생성 이벤트
    @KafkaListener(topics = "user-creation", groupId = "user-campaign-0", containerFactory = "createUserMessageListener")
    public void userCreateEventListener(UserCreateEventDto userCreateEventDto){
        userRepository.save(userCreateEventDto.toEntity());
    }
}