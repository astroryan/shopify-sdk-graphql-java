package com.shopify.sdk.retry;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.util.Set;

/**
 * Configuration for retry logic.
 */
@Data
@Builder(toBuilder = true)
public class RetryConfig {
    
    /**
     * Maximum number of retry attempts.
     */
    @Builder.Default
    private final int maxAttempts = 3;
    
    /**
     * Base delay between retry attempts.
     */
    @Builder.Default
    private final Duration baseDelay = Duration.ofMillis(500);
    
    /**
     * Maximum delay between retry attempts.
     */
    @Builder.Default
    private final Duration maxDelay = Duration.ofSeconds(30);
    
    /**
     * Multiplier for exponential backoff.
     */
    @Builder.Default
    private final double backoffMultiplier = 2.0;
    
    /**
     * Maximum jitter percentage to add to delays.
     */
    @Builder.Default
    private final double jitterPercentage = 0.1;
    
    /**
     * Whether to use exponential backoff.
     */
    @Builder.Default
    private final boolean useExponentialBackoff = true;
    
    /**
     * Whether to respect Retry-After header from responses.
     */
    @Builder.Default
    private final boolean respectRetryAfterHeader = true;
    
    /**
     * HTTP status codes that should trigger retries.
     */
    @Builder.Default
    private final Set<Integer> retryableStatusCodes = Set.of(
        429, // Too Many Requests
        500, // Internal Server Error
        502, // Bad Gateway
        503, // Service Unavailable
        504  // Gateway Timeout
    );
    
    /**
     * Exception types that should trigger retries.
     */
    @Builder.Default
    private final Set<Class<? extends Throwable>> retryableExceptions = Set.of(
        java.net.ConnectException.class,
        java.net.SocketTimeoutException.class,
        java.io.IOException.class
    );
    
    /**
     * Maximum total time to spend on retries.
     */
    @Builder.Default
    private final Duration maxTotalRetryTime = Duration.ofMinutes(5);
    
    /**
     * Whether to enable retry logic.
     */
    @Builder.Default
    private final boolean enabled = true;
}