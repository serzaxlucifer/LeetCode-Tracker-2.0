package com.tracker.leetcode.controller;

import com.tracker.leetcode.dto.AuthResponse;
import com.tracker.leetcode.enums.AuthProvider;
import com.tracker.leetcode.service.AuthSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthSessionService authSessionService;

    @GetMapping
    public ResponseEntity<AuthResponse> login(@RequestParam("authProvider") String authProvider,
        @RequestParam("token") String token) {
        AuthProvider provider = AuthProvider.fromString(authProvider);
        if (provider == AuthProvider.UNKNOWN) {
            return ResponseEntity.badRequest().body(null);
        }

        AuthResponse response = new AuthResponse();//authSessionService.createSession(provider, token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/refresh-session")
    public ResponseEntity<AuthResponse> refreshSession(@RequestParam("jwt") String jwt,
        @RequestParam("refresh_token") String refreshToken) {
        AuthResponse newKeys = new AuthResponse(); //authSessionService.refreshSession(jwt, refreshToken);
        return ResponseEntity.ok(newKeys);
    }
}
