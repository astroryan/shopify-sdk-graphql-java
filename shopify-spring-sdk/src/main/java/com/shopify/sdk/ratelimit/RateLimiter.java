package com.shopify.sdk.ratelimit;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Token bucket rate limiter implementation.
 */
@Slf4j
public class RateLimiter {
    
    private final int capacity;
    private final int refillRate; // tokens per second
    private final AtomicInteger tokens;
    private final AtomicLong lastRefill;
    private final String name;
    
    public RateLimiter(String name, int capacity, int refillRate) {
        this.name = name;
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.tokens = new AtomicInteger(capacity);
        this.lastRefill = new AtomicLong(System.currentTimeMillis());
    }
    
    /**
     * Attempts to acquire a token for request execution.
     * 
     * @return Mono that completes when a token is acquired
     */
    public Mono<Void> acquire() {
        return acquire(1);
    }
    
    /**
     * Attempts to acquire multiple tokens for request execution.
     * 
     * @param requestedTokens number of tokens to acquire
     * @return Mono that completes when tokens are acquired
     */
    public Mono<Void> acquire(int requestedTokens) {
        return Mono.defer(() -> {
            refillTokens();
            
            int currentTokens = tokens.get();
            if (currentTokens >= requestedTokens) {
                if (tokens.compareAndSet(currentTokens, currentTokens - requestedTokens)) {
                    log.debug("Rate limiter '{}' acquired {} tokens, {} remaining", 
                        name, requestedTokens, tokens.get());
                    return Mono.empty();
                }
                // Retry if CAS failed
                return acquire(requestedTokens);
            }
            
            // Not enough tokens, calculate wait time
            long waitTimeMs = calculateWaitTime(requestedTokens);
            
            log.debug("Rate limiter '{}' delaying request by {}ms, current tokens: {}, requested: {}", 
                name, waitTimeMs, currentTokens, requestedTokens);
            
            return Mono.delay(Duration.ofMillis(waitTimeMs))
                .then(acquire(requestedTokens));
        });
    }
    
    /**
     * Attempts to acquire a token without waiting.
     * 
     * @return true if token was acquired, false otherwise
     */
    public boolean tryAcquire() {
        return tryAcquire(1);
    }
    
    /**
     * Attempts to acquire multiple tokens without waiting.
     * 
     * @param requestedTokens number of tokens to acquire
     * @return true if tokens were acquired, false otherwise
     */
    public boolean tryAcquire(int requestedTokens) {
        refillTokens();
        
        int currentTokens = tokens.get();
        if (currentTokens >= requestedTokens) {
            return tokens.compareAndSet(currentTokens, currentTokens - requestedTokens);
        }
        
        return false;
    }
    
    /**
     * Gets the current number of available tokens.
     */
    public int getAvailableTokens() {
        refillTokens();
        return tokens.get();
    }
    
    /**
     * Gets the maximum capacity of the bucket.
     */
    public int getCapacity() {
        return capacity;
    }
    
    /**
     * Gets the refill rate (tokens per second).
     */
    public int getRefillRate() {
        return refillRate;
    }
    
    /**
     * Gets the name of this rate limiter.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Resets the rate limiter to full capacity.
     */
    public void reset() {
        tokens.set(capacity);
        lastRefill.set(System.currentTimeMillis());
        log.debug("Rate limiter '{}' reset to full capacity", name);
    }
    
    /**
     * Updates the bucket based on Shopify's rate limit headers.
     */
    public void updateFromHeaders(int remainingRequests, int maxRequests) {
        if (remainingRequests >= 0 && maxRequests > 0) {
            // Update tokens based on actual API response
            int newTokens = Math.min(remainingRequests, capacity);
            tokens.set(newTokens);
            
            log.debug("Rate limiter '{}' updated from headers: {} remaining of {} max, set to {} tokens", 
                name, remainingRequests, maxRequests, newTokens);
        }
    }
    
    /**
     * Refills tokens based on elapsed time.
     */
    private void refillTokens() {
        long now = System.currentTimeMillis();
        long lastRefillTime = lastRefill.get();
        long elapsedMs = now - lastRefillTime;
        
        if (elapsedMs > 0) {
            // Calculate tokens to add based on elapsed time
            long tokensToAdd = (elapsedMs * refillRate) / 1000;
            
            if (tokensToAdd > 0) {
                int currentTokens = tokens.get();
                int newTokens = Math.min(capacity, currentTokens + (int) tokensToAdd);
                
                if (tokens.compareAndSet(currentTokens, newTokens)) {
                    lastRefill.set(now);
                    
                    if (newTokens != currentTokens) {
                        log.trace("Rate limiter '{}' refilled {} tokens ({}->{})", 
                            name, tokensToAdd, currentTokens, newTokens);
                    }
                }
            }
        }
    }
    
    /**
     * Calculates how long to wait for the requested tokens to become available.
     */
    private long calculateWaitTime(int requestedTokens) {
        int currentTokens = tokens.get();
        int tokensNeeded = requestedTokens - currentTokens;
        
        if (tokensNeeded <= 0) {
            return 0;
        }
        
        // Time to wait for tokens to be refilled
        return (tokensNeeded * 1000L) / refillRate;
    }
    
    /**
     * Gets a summary of the current state.
     */
    public RateLimiterState getState() {
        refillTokens();
        return RateLimiterState.builder()
            .name(name)
            .availableTokens(tokens.get())
            .capacity(capacity)
            .refillRate(refillRate)
            .lastRefill(Instant.ofEpochMilli(lastRefill.get()))
            .build();
    }
    
    @lombok.Data
    @lombok.Builder
    public static class RateLimiterState {
        private final String name;
        private final int availableTokens;
        private final int capacity;
        private final int refillRate;
        private final Instant lastRefill;
        
        public double getUtilizationPercentage() {
            return (double) (capacity - availableTokens) / capacity * 100;
        }
    }
}