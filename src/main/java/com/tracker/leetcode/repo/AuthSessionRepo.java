package com.tracker.leetcode.repo;

import com.tracker.leetcode.entity.AuthSession;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthSessionRepo extends JpaRepository<AuthSession, Integer> {

    List<AuthSession> findBySessionId(Long sessionId);

    Optional<AuthSession> findByUserIdAndSessionId(Long userId, Long sessionId);

    void deleteBySessionId(Long sessionId);

    @Modifying
    @Transactional
    @Query("UPDATE AuthSession a SET a.expired = true WHERE a.sessionId = :sessionId")
    int revokeBySessionId(Long sessionId);

    @Modifying
    @Transactional
    @Query("UPDATE AuthSession a SET a.expired = true WHERE a.userId = :userId AND a.sessionId = :sessionId")
    int revokeByUserIdAndSessionId(Long userId, Long sessionId);
}
