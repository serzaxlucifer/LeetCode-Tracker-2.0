package com.tracker.leetcode.service;

import com.tracker.leetcode.entity.TargetType;
import com.tracker.leetcode.entity.RateLimit;
import com.tracker.leetcode.enums.RateLimitingStrategy;

public interface RateLimiter {
    /**
     * Tries to acquire 1 permit to access resources.
     * @return true if allowed, false if rate-limited
     */
    boolean tryAcquire(String entityValue, TargetType targetType, RateLimit rateLimitConfig);

    RateLimitingStrategy getStrategy();

    /**
     * Optionally, return the number of permits allowed or metadata.
     */
    default String info(String entityValue, TargetType targetType) { return this.getClass().getSimpleName(); }

    default String getRedisKey(String entityValue, TargetType targetType) {
        return targetType + "::" + entityValue + "::" + this.getStrategy().getAbbreviation();
    }
}
