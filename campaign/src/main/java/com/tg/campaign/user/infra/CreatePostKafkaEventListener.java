package com.tg.campaign.user.infra;

import com.tg.campaign.campaign.domain.dto.CreatePostEventDto;
import com.tg.campaign.campaign.domain.dto.EntryCampaignEventDto;
import com.tg.campaign.user.domain.User;
import com.tg.campaign.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreatePostKafkaEventListener {

    private final UserRepository userRepository;

    @Transactional
    @KafkaListener(topics = "post-creation", groupId = "user-campaign-1", containerFactory = "createPostMessageListener")
    public void createPostEventListener(CreatePostEventDto createPostEventDto){
        User user = userRepository.findById(createPostEventDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        user.addMileage();
    }
}