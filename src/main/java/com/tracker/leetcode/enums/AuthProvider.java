package com.tracker.leetcode.enums;

import java.util.HashMap;
import java.util.Map;

public enum AuthProvider {
    GOOGLE("google"), GITHUB("github"), BYPASS("esmeralda"), UNKNOWN("unknown");

    private String authProviderName;

    private static final Map<String, AuthProvider> NAME_MAP = new HashMap<>();

    static {
        for (AuthProvider provider : AuthProvider.values()) {
            NAME_MAP.put(provider.authProviderName, provider);
        }
    }

    public String getAuthProviderName() {
        return authProviderName;
    }

    AuthProvider (String name) {
        this.authProviderName = name;
    }

    public static AuthProvider fromString(String name) {
        if (name == null) return UNKNOWN;
        return NAME_MAP.getOrDefault(name.toLowerCase(), UNKNOWN);
    }
}
