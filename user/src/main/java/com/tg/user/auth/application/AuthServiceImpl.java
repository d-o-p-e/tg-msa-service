package com.tg.user.auth.application;

import com.tg.user.auth.domain.AuthService;
import com.tg.user.auth.domain.SessionRepository;
import com.tg.user.auth.domain.SessionUserVo;
import com.tg.user.user.domain.User;
import com.tg.user.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    @Override
    public void createSession(Long userId, String sessionId) {
        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        sessionRepository.saveSession(sessionId, user.toSessionUserVo());
    }

    @Override
    public SessionUserVo getSession(String sessionId) {
        return sessionRepository.getSession(sessionId);
    }

    @Override
    public void expireSession(String sessionId) {
        sessionRepository.expireSession(sessionId);
    }

    @Override
    public void extendExpiration(String sessionId) {
        sessionRepository.extendExpiration(sessionId);
    }
}
