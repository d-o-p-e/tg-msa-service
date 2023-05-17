package com.tg.user.auth.domain;

public interface SessionRepository {
    void saveSession(String key, SessionUserVo sessionUserVo);

    SessionUserVo getSession(String key);

    void extendExpiration(String key);

    void expireSession(String key);
}
