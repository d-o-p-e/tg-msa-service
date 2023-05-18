package com.tg.campaign.campaign.domain.dto;

import com.tg.campaign.campaign.domain.Campaign;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class CampaignResponseDto {
    private Long campaignId;
    private String title;
    private String imageUrl;

    public static List<CampaignResponseDto> of(List<Campaign> campaignList) {
        return campaignList.stream()
            .map(campaign -> new CampaignResponseDto(
                campaign.getId(),
                campaign.getTitle(),
                campaign.getImageUrl()))
            .collect(Collectors.toList());
    }
}
