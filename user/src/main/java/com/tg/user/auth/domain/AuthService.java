package com.tg.user.auth.domain;

public interface AuthService {
    void createSession(Long userId, String sessionId);

    SessionUserVo getSession(String sessionId);

    void expireSession(String sessionId);

    void extendExpiration(String sessionId);
}
