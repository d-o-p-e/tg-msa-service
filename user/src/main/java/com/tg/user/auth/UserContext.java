package com.tg.user.auth;

import com.tg.user.auth.domain.SessionUserVo;

public class UserContext {
    public static final ThreadLocal<SessionUserVo> CONTEXT = new ThreadLocal<>();
    public static SessionUserVo getContext() {
        return UserContext.CONTEXT.get();
    }
}
