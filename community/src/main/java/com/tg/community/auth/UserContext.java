package com.tg.community.auth;

import com.tg.community.auth.domain.SessionUserVo;

public class UserContext {
    public static final ThreadLocal<SessionUserVo> CONTEXT = new ThreadLocal<>();
    public static SessionUserVo getContext() {
        return UserContext.CONTEXT.get();
    }
}
