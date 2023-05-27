package com.tg.campaign.campaign.application;

import com.tg.campaign.campaign.domain.Campaign;
import com.tg.campaign.campaign.domain.CampaignEntrant;
import com.tg.campaign.campaign.domain.CampaignEntrantRepository;
import com.tg.campaign.campaign.domain.CampaignRepository;
import com.tg.campaign.campaign.domain.CampaignService;
import com.tg.campaign.campaign.domain.MediaService;
import com.tg.campaign.campaign.domain.dto.CreateCampaignRequestDto;
import com.tg.campaign.campaign.domain.dto.EntryCampaignEventDto;
import com.tg.campaign.campaign.domain.EntryCampaignProducerEvent;
import com.tg.campaign.campaign.domain.dto.CampaignResponseDto;
import com.tg.campaign.user.domain.User;
import com.tg.campaign.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final MediaService mediaService;
    private final UserRepository userRepository;
    private final CampaignRepository campaignRepository;
    private final CampaignEntrantRepository campaignEntrantRepository;
    private final EntryCampaignProducerEvent entryCampaignProducerEvent;

    @Value("${aws.s3.directory.image}")
    private String s3ImageDirectory;

    public List<CampaignResponseDto> getCampaign() {
        List<Campaign> campaignList = campaignRepository.findAll();
        return CampaignResponseDto.of(campaignList);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> enterCampaign(Long userId, Long campaignId) {
        User user = userRepository.getReferenceById(userId);
        user.deductMileage();
        Campaign campaign = campaignRepository.findById(campaignId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 캠페인입니다."));
        campaignEntrantRepository.save(new CampaignEntrant(campaign, user));
        entryCampaignProducerEvent.sendMessage(new EntryCampaignEventDto(user));
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Void> enterOneCampaign(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        user.deductMileage();
        campaignEntrantRepository.save(new CampaignEntrant(null, user));
        entryCampaignProducerEvent.sendMessage(new EntryCampaignEventDto(user));
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public void create(CreateCampaignRequestDto createCampaignRequestDto) {
        String fileName = mediaService.uploadFile(createCampaignRequestDto.getImage(), s3ImageDirectory);
        Campaign campaign = Campaign.builder()
                        .title(createCampaignRequestDto.getTitle())
                        .imageUrl(fileName)
                        .build();
        campaignRepository.save(campaign);
    }
}
