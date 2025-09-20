package com.tracker.leetcode.service.impl;

import com.tracker.leetcode.entity.TargetType;
import com.tracker.leetcode.enums.RateLimitingStrategy;
import com.tracker.leetcode.service.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class BurstableRateLimiter implements RateLimiter {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final Logger log = LoggerFactory.getLogger(BurstableRateLimiter.class);

    @Override
    public boolean tryAcquire(String entityValue, TargetType targetType, RateLimit rateLimitConfig) {
        String key = getRedisKey(entityValue, targetType);
        Long remainingPermits = redisTemplate.opsForValue().decrement(key);
        if (remainingPermits == null) {
            if (Objects.isNull(rateLimitConfig)) {
                rateLimitConfig = getRateLimit(entityValue, targetType);
            }
            boolean set = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(
                    key,
                    String.valueOf(rateLimitConfig.getMetadata().getLimit() - 1),
                    rateLimitConfig.getMetadata().getWindow(),
                    TimeUnit.SECONDS
            ));
            if (set) {
                return true;
            } else {
                remainingPermits = redisTemplate.opsForValue().decrement(key);
            }
        }
        return remainingPermits != null && remainingPermits >= 0;
    }

    private RateLimit getRateLimit(String entityValue, TargetType targetType) {
        // Get from DB
    }

    @Override
    public String info(String entityValue, TargetType targetType) {
        return redisTemplate.opsForValue().get(getRedisKey(entityValue, targetType));
    }

    @Override
    public RateLimitingStrategy getStrategy() {
        return RateLimitingStrategy.BURSTABLE;
    }
}
