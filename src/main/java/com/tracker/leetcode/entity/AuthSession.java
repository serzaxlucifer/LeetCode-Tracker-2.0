package com.tracker.leetcode.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Entity
@Table(name = "auth_sessions")
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthSession extends AbstractEntity {

   @Column(name = "user_id")
   private Long userId;

   @Column(name = "session_id")
   private Long sessionId;

   @Column(name = "device_name")
   private String deviceName;

   @Column(name = "device_fingerprint")
   private String deviceFingerprint;

   @Column(name = "ip_address")
   private String ipAddress;

   @Column(name = "user_agent")
   private String userAgent;

   @Column(name = "hmac_public_key")
   private String hmacPublicKey;

   @Column(name = "hmac_expiry")
   private Instant hmacExpiry;

   @Column(name = "refresh_token_hash")
   private String refreshTokenHash;

   @Column(name = "refresh_token_expiry")
   private Instant refreshTokenExpiry;

   @Column(name = "jwt_expiry")
   private Instant jwtExpiry;

   @Column(name = "expired")
   private Boolean expired = false;

   @Column(name = "last_used_at")
   private Instant lastUsedAt;
}
