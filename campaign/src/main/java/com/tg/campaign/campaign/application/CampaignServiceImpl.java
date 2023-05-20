package com.tg.campaign.campaign.application;

import com.tg.campaign.campaign.domain.Campaign;
import com.tg.campaign.campaign.domain.CampaignEntrant;
import com.tg.campaign.campaign.domain.CampaignEntrantRepository;
import com.tg.campaign.campaign.domain.CampaignRepository;
import com.tg.campaign.campaign.domain.CampaignService;
import com.tg.campaign.campaign.domain.dto.CampaignResponseDto;
import com.tg.campaign.user.domain.User;
import com.tg.campaign.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final UserRepository userRepository;
    private final CampaignRepository campaignRepository;
    private final CampaignEntrantRepository campaignEntrantRepository;

    public List<CampaignResponseDto> getCampaign() {
        List<Campaign> campaignList = campaignRepository.findAll();
        return CampaignResponseDto.of(campaignList);
    }

    @Transactional
    public ResponseEntity<Void> enterCampaign(Long userId, Long campaignId) {
        User user = userRepository.getReferenceById(userId);
        Campaign campaign = campaignRepository.findById(campaignId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 캠페인입니다."));
        user.withdraw();
        campaignEntrantRepository.save(new CampaignEntrant(campaign, user));
        return ResponseEntity.ok().build();
    }
}
