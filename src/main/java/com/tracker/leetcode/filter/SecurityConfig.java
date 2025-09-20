package com.tracker.leetcode.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())                 // Disable CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // Allow all /auth endpoints
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable())            // Disable default login page
                .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }
}
