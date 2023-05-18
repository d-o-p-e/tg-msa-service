package com.tg.campaign.auth.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tg.campaign.auth.domain.SessionRepository;
import com.tg.campaign.auth.domain.SessionUserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
@RequiredArgsConstructor
public class SessionRedisRepository implements SessionRepository {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

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
}