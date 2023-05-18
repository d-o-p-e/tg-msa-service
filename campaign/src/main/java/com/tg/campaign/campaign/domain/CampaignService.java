package com.tg.campaign.campaign.domain;

import com.tg.campaign.campaign.domain.dto.CampaignResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CampaignService {
    List<CampaignResponseDto> getCampaign();

    ResponseEntity<Void> drawCampaign(Long userId, Long campaignId);
}
