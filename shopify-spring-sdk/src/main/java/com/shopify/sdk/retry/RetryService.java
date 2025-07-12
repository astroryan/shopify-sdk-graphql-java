package com.shopify.sdk.retry;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.function.Predicate;

/**
 * Service for handling automatic retries with exponential backoff.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RetryService {
    
    private final RetryConfig config;
    private final Random random = new Random();
    
    /**
     * Creates a retry spec for Shopify API calls.
     */
    public Retry createRetrySpec() {
        if (!config.isEnabled()) {
            return Retry.max(0);
        }
        
        return Retry.backoff(config.getMaxAttempts(), config.getBaseDelay())
            .maxBackoff(config.getMaxDelay())
            .jitter(config.getJitterPercentage())
            .filter(this::shouldRetry)
            .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                log.error("Retry exhausted after {} attempts for operation: {}", 
                    retrySignal.totalRetries(), retrySignal.failure().getMessage());
                return retrySignal.failure();
            })
            .doBeforeRetry(retrySignal -> {
                Throwable failure = retrySignal.failure();
                long attempt = retrySignal.totalRetries() + 1;
                
                Duration delay = calculateDelay(attempt, failure);
                
                log.warn("Retrying operation (attempt {}/{}) after {}ms due to: {}", 
                    attempt, config.getMaxAttempts(), delay.toMillis(), 
                    failure.getMessage());
            });
    }
    
    /**
     * Creates a custom retry spec with specific parameters.
     */
    public Retry createCustomRetrySpec(int maxAttempts, Duration baseDelay, Duration maxDelay) {
        return Retry.backoff(maxAttempts, baseDelay)
            .maxBackoff(maxDelay)
            .jitter(config.getJitterPercentage())
            .filter(this::shouldRetry)
            .doBeforeRetry(retrySignal -> {
                log.warn("Retrying operation (attempt {}/{}) due to: {}", 
                    retrySignal.totalRetries() + 1, maxAttempts, 
                    retrySignal.failure().getMessage());
            });
    }
    
    /**
     * Wraps a Mono with retry logic.
     */
    public <T> Mono<T> withRetry(Mono<T> mono) {
        return mono.retryWhen(createRetrySpec());
    }
    
    /**
     * Wraps a Mono with custom retry logic.
     */
    public <T> Mono<T> withCustomRetry(Mono<T> mono, int maxAttempts, Duration baseDelay) {
        return mono.retryWhen(createCustomRetrySpec(maxAttempts, baseDelay, config.getMaxDelay()));
    }
    
    /**
     * Determines if an exception should trigger a retry.
     */
    private boolean shouldRetry(Throwable throwable) {
        // Check for retryable exception types
        for (Class<? extends Throwable> retryableException : config.getRetryableExceptions()) {
            if (retryableException.isAssignableFrom(throwable.getClass())) {
                log.debug("Exception {} is retryable (type match)", throwable.getClass().getSimpleName());
                return true;
            }
        }
        
        // Check for retryable HTTP status codes
        if (throwable instanceof WebClientResponseException) {
            WebClientResponseException webEx = (WebClientResponseException) throwable;
            int statusCode = webEx.getStatusCode().value();
            
            boolean isRetryable = config.getRetryableStatusCodes().contains(statusCode);
            
            if (isRetryable) {
                log.debug("HTTP status {} is retryable", statusCode);
            } else {
                log.debug("HTTP status {} is not retryable", statusCode);
            }
            
            return isRetryable;
        }
        
        log.debug("Exception {} is not retryable", throwable.getClass().getSimpleName());
        return false;
    }
    
    /**
     * Calculates the delay for a retry attempt.
     */
    private Duration calculateDelay(long attemptNumber, Throwable failure) {
        Duration baseDelay = config.getBaseDelay();
        
        // Check for Retry-After header in HTTP responses
        if (config.isRespectRetryAfterHeader() && failure instanceof WebClientResponseException) {
            WebClientResponseException webEx = (WebClientResponseException) failure;
            String retryAfter = webEx.getHeaders().getFirst("Retry-After");
            
            if (retryAfter != null) {
                try {
                    long retryAfterSeconds = Long.parseLong(retryAfter);
                    Duration retryAfterDuration = Duration.ofSeconds(retryAfterSeconds);
                    
                    log.debug("Using Retry-After header value: {}s", retryAfterSeconds);
                    
                    // Respect the server's suggestion but cap at max delay
                    return retryAfterDuration.compareTo(config.getMaxDelay()) <= 0 ? 
                        retryAfterDuration : config.getMaxDelay();
                        
                } catch (NumberFormatException e) {
                    log.warn("Invalid Retry-After header value: {}", retryAfter);
                }
            }
        }
        
        // Use exponential backoff if enabled
        if (config.isUseExponentialBackoff()) {
            long delayMs = (long) (baseDelay.toMillis() * Math.pow(config.getBackoffMultiplier(), attemptNumber - 1));
            baseDelay = Duration.ofMillis(delayMs);
        }
        
        // Cap at max delay
        if (baseDelay.compareTo(config.getMaxDelay()) > 0) {
            baseDelay = config.getMaxDelay();
        }
        
        // Add jitter to prevent thundering herd
        if (config.getJitterPercentage() > 0) {
            long jitterMs = (long) (baseDelay.toMillis() * config.getJitterPercentage() * random.nextDouble());
            baseDelay = baseDelay.plusMillis(jitterMs);
        }
        
        return baseDelay;
    }
    
    /**
     * Creates a predicate to filter retryable exceptions.
     */
    public Predicate<Throwable> retryablePredicate() {
        return this::shouldRetry;
    }
    
    /**
     * Gets the retry configuration.
     */
    public RetryConfig getConfig() {
        return config;
    }
    
    /**
     * Creates a retry context for tracking retry attempts.
     */
    public RetryContext createContext(String operationName) {
        return new RetryContext(operationName);
    }
    
    /**
     * Context for tracking retry attempts.
     */
    public static class RetryContext {
        private final String operationName;
        private final Instant startTime;
        private int attemptCount = 0;
        private Duration totalDelay = Duration.ZERO;
        
        public RetryContext(String operationName) {
            this.operationName = operationName;
            this.startTime = Instant.now();
        }
        
        public void recordAttempt(Duration delay) {
            attemptCount++;
            totalDelay = totalDelay.plus(delay);
        }
        
        public String getOperationName() {
            return operationName;
        }
        
        public int getAttemptCount() {
            return attemptCount;
        }
        
        public Duration getTotalDelay() {
            return totalDelay;
        }
        
        public Duration getTotalTime() {
            return Duration.between(startTime, Instant.now());
        }
        
        public Instant getStartTime() {
            return startTime;
        }
    }
}