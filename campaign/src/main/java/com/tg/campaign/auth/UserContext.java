package com.tg.campaign.auth;

import com.tg.campaign.auth.domain.SessionUserVo;

public class UserContext {
    public static final ThreadLocal<SessionUserVo> CONTEXT = new ThreadLocal<>();
    public static SessionUserVo getContext() {
        return UserContext.CONTEXT.get();
    }
}
