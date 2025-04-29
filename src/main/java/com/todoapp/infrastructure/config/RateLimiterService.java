package com.todoapp.infrastructure.config;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

    private final RedisTemplate<String, String> redisTemplate;

    public RateLimiterService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isAllowed(String key, long maxRequests, long timeWindowSeconds) {
        String redisKey = "rate_limiter:" + key;
        Long current = redisTemplate.opsForValue().increment(redisKey);

        if (current == 1)
            redisTemplate.expire(redisKey, Duration.ofSeconds(timeWindowSeconds));

        return current <= maxRequests;
    }
}
