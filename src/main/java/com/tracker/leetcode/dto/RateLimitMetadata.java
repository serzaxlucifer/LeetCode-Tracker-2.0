package com.tracker.leetcode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RateLimitMetadata implements Serializable {

    private static final long serialVersionUID = 1;

    @JsonProperty("refill_factor")
    private Double refillFactor;

}
