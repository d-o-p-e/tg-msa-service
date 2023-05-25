package com.tg.campaign.campaign.domain;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public interface CampaignRepository {

    Campaign save(Campaign campaign);
    List<Campaign> findAll();

    Optional<Campaign> findById(Long campaignId);
}
