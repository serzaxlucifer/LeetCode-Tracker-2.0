package com.tracker.leetcode.service.impl;

import com.tracker.leetcode.components.SystemLoadMonitor;
import com.tracker.leetcode.entity.TargetType;
import com.tracker.leetcode.enums.RateLimitingStrategy;
import com.tracker.leetcode.service.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;

public class PerformanceRateLimiter implements RateLimiter {

    @Autowired
    private ConfigPropertyService configPropertyService;

    private final String CPU_LIMIT_THROTTLING = "cpu.throttling.limit";

    private final String MEMORY_LIMIT_THROTTLING = "memory.throttling.limit";

    @Override
    public boolean tryAcquire(String entityValue, TargetType targetType, RateLimit rateLimitConfig) {
        return !(SystemLoadMonitor.getCpuLoad() > getMaxPermittedCPULoad()) &&
                !(SystemLoadMonitor.getAvailableMemory() > getMaxPermittedMemoryLoad());
    }

    private double getMaxPermittedCPULoad() {
        return configPropertyService.getDouble(CPU_LIMIT_THROTTLING);
    }

    private double getMaxPermittedMemoryLoad() {
        return configPropertyService.getDouble(MEMORY_LIMIT_THROTTLING);
    }

    @Override
    public RateLimitingStrategy getStrategy() {
        return RateLimitingStrategy.SYSTEM_LOAD;
    }
}
