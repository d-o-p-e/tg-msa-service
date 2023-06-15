package com.tg.campaign.auth.application;

import com.tg.campaign.auth.domain.AuthService;
import com.tg.campaign.auth.domain.SessionRepository;
import com.tg.campaign.auth.domain.SessionUserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SessionRepository sessionRepository;
    // 인증
    @Override
    public SessionUserVo getSession(String sessionId) {
        return sessionRepository.getSession(sessionId);
    }

}
