package com.tracker.leetcode.enums;

public enum RateLimitingStrategy {
    BURSTABLE("b"), NON_BURSTABLE("nb"), SYSTEM_LOAD("sys_load");

    private final String abbreviation;

    RateLimitingStrategy(String b) {
        abbreviation = b;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}