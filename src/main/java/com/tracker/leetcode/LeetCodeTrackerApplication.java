package com.tracker.leetcode;

import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.security.Security;
import java.util.TimeZone;
import java.util.UUID;

@SpringBootApplication
@ComponentScan({"com.tracker.leetcode"})
@PropertySource(value = {"classpath:config.properties", "classpath:git.properties"})
public class LeetCodeTrackerApplication {

    @PostConstruct
    public void init() {
        Security.setProperty("networkaddress.cache.ttl", "60");         // @ToDo: Optimize DNS Caching further.
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        ThreadContext.put("LEETCODE_TRACKER_REQUEST_ID", UUID.randomUUID().toString());
        SpringApplication.run(LeetCodeTrackerApplication.class, args);
    }
}