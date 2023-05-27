package com.tg.user.user.infra;

import com.tg.user.user.domain.User;
import com.tg.user.user.domain.UserRepository;
import com.tg.user.user.domain.dto.UserCreateEventDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserCreateKafkaProducerEventTest {

    @Autowired UserCreateKafkaProducerEvent userCreateKafkaProducerEvent;
    @Autowired UserRepository userRepository;

    @Test
    void sendMessage() {
        User user = User.builder()
                .nickname("nickname")
                .email("email")
                .profileImageUrl("profileImageUrl")
                .build();
        userRepository.save(user);
        UserCreateEventDto userCreateEventDto = new UserCreateEventDto();

        userCreateKafkaProducerEvent.sendMessage(userCreateEventDto);
    }
}