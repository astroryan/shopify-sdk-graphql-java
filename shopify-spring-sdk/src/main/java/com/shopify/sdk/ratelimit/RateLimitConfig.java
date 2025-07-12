package com.shopify.sdk.ratelimit;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;

/**
 * Configuration for rate limiting.
 */
@Data
@Builder(toBuilder = true)
public class RateLimitConfig {
    
    /**
     * Maximum number of requests per second for REST API.
     */
    @Builder.Default
    private final int maxRestRequestsPerSecond = 2;
    
    /**
     * Maximum number of requests per second for GraphQL API.
     */
    @Builder.Default
    private final int maxGraphQLRequestsPerSecond = 10;
    
    /**
     * Bucket capacity for REST API rate limiting.
     */
    @Builder.Default
    private final int restBucketCapacity = 40;
    
    /**
     * Bucket capacity for GraphQL API rate limiting.
     */
    @Builder.Default
    private final int graphQLBucketCapacity = 1000;
    
    /**
     * Maximum wait time when rate limited.
     */
    @Builder.Default
    private final Duration maxWaitTime = Duration.ofMinutes(1);
    
    /**
     * Whether to automatically retry when rate limited.
     */
    @Builder.Default
    private final boolean autoRetryOnRateLimit = true;
    
    /**
     * Base retry delay when rate limited.
     */
    @Builder.Default
    private final Duration baseRetryDelay = Duration.ofMillis(500);
    
    /**
     * Maximum number of retry attempts.
     */
    @Builder.Default
    private final int maxRetryAttempts = 3;
    
    /**
     * Whether to use exponential backoff for retries.
     */
    @Builder.Default
    private final boolean useExponentialBackoff = true;
    
    /**
     * Multiplier for exponential backoff.
     */
    @Builder.Default
    private final double backoffMultiplier = 2.0;
    
    /**
     * Maximum jitter to add to retry delays (percentage).
     */
    @Builder.Default
    private final double jitterPercentage = 0.1; // 10%
    
    /**
     * Whether to respect Retry-After header from Shopify.
     */
    @Builder.Default
    private final boolean respectRetryAfterHeader = true;
}