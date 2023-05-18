package com.tg.campaign.campaign.infra;


import com.tg.campaign.campaign.domain.Campaign;
import com.tg.campaign.campaign.domain.CampaignRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CampaignRepositoryJpa extends JpaRepository<Campaign, Long>, CampaignRepository {
    List<Campaign> findAll();

    Optional<Campaign> findById(Long campaignId);
}