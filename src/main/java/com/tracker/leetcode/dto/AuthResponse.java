package com.tracker.leetcode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AuthResponse {
    @JsonProperty("jwt")
    private String JWT;

    @JsonProperty("jwt_expiry")
    private Instant jwtExpiry;

    @JsonProperty("hmac_private_key")
    private String hmacKey;

    @JsonProperty("hmac_expiry")
    private Instant hmacExpiry;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("refresh_token_expiry")
    private Instant refreshTokenExpiry;

}
