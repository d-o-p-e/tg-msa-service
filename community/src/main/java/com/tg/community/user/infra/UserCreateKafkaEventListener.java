package com.tg.community.user.infra;

import com.tg.community.user.domain.UserRepository;
import com.tg.community.user.domain.dto.UserCreateEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCreateKafkaEventListener {

    private final UserRepository userRepository;

    @KafkaListener(topics = "user-creation", groupId = "user-community-0", containerFactory = "createUserMessageListener")
    public void userCreateEventListener(UserCreateEventDto userCreateEventDto){
        userRepository.save(userCreateEventDto.toEntity());
    }
}