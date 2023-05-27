package com.tg.campaign.campaign.domain;

import com.tg.campaign.campaign.domain.dto.CampaignResponseDto;
import com.tg.campaign.campaign.domain.dto.CreateCampaignRequestDto;
import com.tg.campaign.campaign.domain.dto.UserMileageResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CampaignService {
    List<CampaignResponseDto> getCampaign();

    ResponseEntity<Void> enterCampaign(Long userId, Long campaignId);

    @Transactional
    ResponseEntity<Void> enterOneCampaign(Long userId);

    void create(CreateCampaignRequestDto createCampaignRequestDto);

    UserMileageResponseDto getMileage(Long id);
}
