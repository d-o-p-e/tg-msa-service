package com.tg.campaign.campaign.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CreateCampaignRequestDto {
    private Long campaignId;
    private String title;
    private MultipartFile image;
}
