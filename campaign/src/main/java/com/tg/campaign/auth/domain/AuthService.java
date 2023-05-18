package com.tg.campaign.auth.domain;

public interface AuthService {

    SessionUserVo getSession(String sessionId);
}
