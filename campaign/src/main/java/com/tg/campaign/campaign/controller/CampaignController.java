package com.tg.campaign.campaign.controller;

import com.tg.campaign.auth.Auth;
import com.tg.campaign.auth.UserContext;
import com.tg.campaign.auth.domain.SessionUserVo;
import com.tg.campaign.campaign.domain.CampaignService;
import com.tg.campaign.campaign.domain.dto.CampaignResponseDto;
import com.tg.campaign.campaign.domain.dto.CreateCampaignRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "CAMPAIGN", description = "캠페인 API")
@RestController
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;

    @Operation(summary = "캠페인 조회", description = "진행중인 캠페인을 목록을 조회합니다.")
    @GetMapping("/")
    public ResponseEntity<List<CampaignResponseDto>> getCampaign() {
        return ResponseEntity.ok().body(campaignService.getCampaign());
    }

//    @Operation(summary = "캠페인 생성", description = "캠페인을 생성합니다.")
//    @PostMapping("/")
//    public ResponseEntity<Void> createPost(@ModelAttribute CreateCampaignRequestDto createCampaignRequestDto) {
//        campaignService.create(createCampaignRequestDto);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @Operation(summary = "캠페인 응모", description = "캠페인에 응모합니다.")
//    @Auth
//    @PostMapping("/{campaignId}")
//    public ResponseEntity<Void> enterCampaign(@PathVariable Long campaignId) {
//        SessionUserVo sessionUserVo = UserContext.getContext();
//        return campaignService.enterCampaign(sessionUserVo.getId(), campaignId);
//    }

    @Operation(summary = "단일 캠페인 응모", description = "단일 캠페인에 응모합니다.")
    @Auth
    @PostMapping("/")
    public ResponseEntity<Void> enterOneCampaign() {
        SessionUserVo sessionUserVo = UserContext.getContext();
        return campaignService.enterOneCampaign(sessionUserVo.getId());
    }
}
