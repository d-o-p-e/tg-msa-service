package com.tg.community.auth.domain;

public interface SessionRepository {

    SessionUserVo getSession(String key);
}
