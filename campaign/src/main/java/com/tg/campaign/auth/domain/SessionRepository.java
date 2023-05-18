package com.tg.campaign.auth.domain;

public interface SessionRepository {

    SessionUserVo getSession(String key);
}
