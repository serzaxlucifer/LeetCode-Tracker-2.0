package com.tracker.leetcode.service.impl;

import com.tracker.leetcode.entity.TargetType;
import com.tracker.leetcode.entity.RateLimit;
import com.tracker.leetcode.enums.RateLimitingStrategy;
import com.tracker.leetcode.service.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Objects;

public class NonBurstableRateLimiter implements RateLimiter {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String LUA_SCRIPT =
                    "local key = KEYS[1]\n" +
                    "local capacity = tonumber(ARGV[1])\n" +
                    "local refillRate = tonumber(ARGV[2])\n" +
                    "local now = tonumber(ARGV[3])\n" +
                    "local ttl = tonumber(ARGV[4])\n" +

                    // Get bucket state
                    "local bucket = redis.call('HMGET', key, 'tokens', 'timestamp')\n" +
                    "local tokens = tonumber(bucket[1])\n" +
                    "local lastRefill = tonumber(bucket[2])\n" +

                    // Initialize if first time
                    "if tokens == nil then\n" +
                    "  tokens = capacity\n" +
                    "  lastRefill = now\n" +
                    "end\n" +

                    // Refill tokens based on elapsed time
                    "local delta = math.max(0, now - lastRefill)\n" +
                    "local refill = math.floor(delta * refillRate)\n" +
                    "tokens = math.min(capacity, tokens + refill)\n" +
                    "lastRefill = lastRefill + refill / refillRate\n" +

                    // Try consume 1 token
                    "if tokens > 0 then\n" +
                    "  tokens = tokens - 1\n" +
                    "  redis.call('HSET', key, 'tokens', tokens, 'timestamp', lastRefill)\n" +
                    "  redis.call('PEXPIRE', key, ttl)\n" +
                    "  return 1\n" +
                    "else\n" +
                    "  redis.call('HSET', key, 'tokens', tokens, 'timestamp', lastRefill)\n" +
                    "  redis.call('PEXPIRE', key, ttl)\n" +
                    "  return 0\n" +
                    "end";

    @Override
    public boolean tryAcquire(String entityValue, TargetType targetType, RateLimit rateLimitConfig) {
        String key = getRedisKey(entityValue, targetType);
        if (Objects.isNull(rateLimitConfig)) {
            rateLimitConfig = getRateLimit(entityValue, targetType);
        }

        int capacity = rateLimitConfig.getMaxRequests();
        int windowSeconds = (int) rateLimitConfig.getRefreshInterval();
        double refillFactor = rateLimitConfig.getMetadata().getRefillFactor();
        double refillRate = ((double) capacity / (windowSeconds * 1000.0)) * refillFactor;

        long now = System.currentTimeMillis();

        Long result = redisTemplate.execute((connection, keys) ->
                        connection.scriptingCommands().eval(
                                LUA_SCRIPT.getBytes(),
                                ReturnType.INTEGER,
                                1,
                                key.getBytes(),
                                String.valueOf(capacity).getBytes(),
                                String.valueOf(refillRate).getBytes(),
                                String.valueOf(now).getBytes(),
                                String.valueOf(windowSeconds * 1000).getBytes()
                        ),
                false,          // dont batch, execute immediately
                true            // deserialize result
        );

        return result != null && result == 1;

    }

    private RateLimit getRateLimit(String entityValue, TargetType targetType) {
        // Get from DB
    }

    @Override
    public RateLimitingStrategy getStrategy() {
        return RateLimitingStrategy.NON_BURSTABLE;
    }
}
