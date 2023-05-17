package com.tg.user.auth.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tg.user.auth.domain.SessionRepository;
import com.tg.user.auth.domain.SessionUserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class SessionRedisRepository implements SessionRepository {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @Value("${session.expiration}")
    private Long sessionExpiration;

    @Override
    public void saveSession(String key, SessionUserVo sessionUserVo) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        try {
            String stringValue = objectMapper.writeValueAsString(sessionUserVo);
            valueOps.set(key, stringValue, sessionExpiration, TimeUnit.MILLISECONDS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SessionUserVo getSession(String key) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        String stringValue = valueOps.get(key);
        if (stringValue != null) {
            try {
                return objectMapper.readValue(stringValue, SessionUserVo.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public void extendExpiration(String key) {
        redisTemplate.expire(key, sessionExpiration, TimeUnit.MILLISECONDS);
    }

    @Override
    public void expireSession(String key) {
        redisTemplate.delete(key);
    }
}