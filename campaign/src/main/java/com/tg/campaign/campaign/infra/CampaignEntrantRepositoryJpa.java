package com.tg.campaign.campaign.infra;

import com.tg.campaign.campaign.domain.CampaignEntrant;
import com.tg.campaign.campaign.domain.CampaignEntrantRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignEntrantRepositoryJpa extends JpaRepository<CampaignEntrant, Long>, CampaignEntrantRepository {
    CampaignEntrant save(CampaignEntrant campaignEntrant);
}