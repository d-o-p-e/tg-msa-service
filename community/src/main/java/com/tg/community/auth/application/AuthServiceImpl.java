package com.tg.community.auth.application;

import com.tg.community.auth.domain.AuthService;
import com.tg.community.auth.domain.SessionRepository;
import com.tg.community.auth.domain.SessionUserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SessionRepository sessionRepository;
    @Override
    public SessionUserVo getSession(String sessionId) {
        return sessionRepository.getSession(sessionId);
    }

}
