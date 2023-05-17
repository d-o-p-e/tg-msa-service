package com.tg.community.auth.domain;

public interface AuthService {

    SessionUserVo getSession(String sessionId);
}
