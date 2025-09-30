package com.tracker.leetcode.entity;

import com.tracker.leetcode.dto.RateLimitMetadata;
import com.tracker.leetcode.enums.RateLimitingStrategy;
import lombok.Data;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import jakarta.persistence.*;

@Data
@Entity
@TypeDefs({@TypeDef(name = "psql_enum", typeClass = PostgreSQLEnumType.class),
    @TypeDef(name = "jsonb", typeClass = PostgresJsonBinaryTree.class)
})
@Table(
        name = "rate_limit_configs",
        uniqueConstraints = @UniqueConstraint(columnNames = {"target_type", "target_identifier"})
)
public class RateLimit extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private TargetType targetType;

    @Column(name = "target_identifier")
    private String targetIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "rate_limiting_strategy", nullable = false)
    private RateLimitingStrategy rateLimitingStrategy;

    @Column(name = "max_requests", nullable = false)
    private Integer maxRequests;

    @Column(name = "refresh_interval", nullable = false)
    private Integer refreshInterval; // in seconds

    @Column(name = "block_duration_ms", nullable = false)
    private Long blockDurationMs;

    @Type(type = "jsonb", parameters = {
            @Parameter(name = "returnedClass", value = "com.tracker.leetcode.dto.RateLimitMetadata")
    })
    @Column(name = "metadata", columnDefinition = "jsonb")
    private RateLimitMetadata metadata;
}

