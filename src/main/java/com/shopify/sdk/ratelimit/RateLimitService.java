package com.shopify.sdk.ratelimit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service for managing rate limiting across different Shopify API types.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RateLimitService {
    
    private final RateLimitConfig config;
    private final Map<String, RateLimiter> rateLimiters = new ConcurrentHashMap<>();
    
    // Rate limiter keys
    private static final String REST_LIMITER_KEY = "rest";
    private static final String GRAPHQL_LIMITER_KEY = "graphql";
    
    /**
     * Acquires permission to make a REST API request.
     */
    public Mono<Void> acquireRestPermit() {
        return getRestRateLimiter().acquire();
    }
    
    /**
     * Acquires permission to make a GraphQL API request.
     */
    public Mono<Void> acquireGraphQLPermit() {
        return getGraphQLRateLimiter().acquire();
    }
    
    /**
     * Acquires permission to make a GraphQL API request with custom cost.
     */
    public Mono<Void> acquireGraphQLPermit(int cost) {
        return getGraphQLRateLimiter().acquire(cost);
    }
    
    /**
     * Updates REST rate limiter based on Shopify API response headers.
     */
    public void updateRestLimitsFromHeaders(Map<String, String> headers) {
        String callLimit = headers.get("X-Shopify-Shop-Api-Call-Limit");
        
        if (callLimit != null && callLimit.contains("/")) {
            try {
                String[] parts = callLimit.split("/");
                int used = Integer.parseInt(parts[0]);
                int max = Integer.parseInt(parts[1]);
                int remaining = max - used;
                
                getRestRateLimiter().updateFromHeaders(remaining, max);
                
                log.debug("Updated REST rate limiter from headers: {}/{} ({}% used)", 
                    used, max, (used * 100.0 / max));
                    
            } catch (NumberFormatException e) {
                log.warn("Failed to parse REST API call limit header: {}", callLimit);
            }
        }
    }
    
    /**
     * Updates GraphQL rate limiter based on GraphQL response extensions.
     */
    public void updateGraphQLLimitsFromResponse(Map<String, Object> extensions) {
        if (extensions == null) {
            return;
        }
        
        Object cost = extensions.get("cost");
        if (cost instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> costData = (Map<String, Object>) cost;
            
            Object throttleStatus = costData.get("throttleStatus");
            if (throttleStatus instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> throttleData = (Map<String, Object>) throttleStatus;
                
                Object currentlyAvailable = throttleData.get("currentlyAvailable");
                Object maximumAvailable = throttleData.get("maximumAvailable");
                
                if (currentlyAvailable instanceof Number && maximumAvailable instanceof Number) {
                    int available = ((Number) currentlyAvailable).intValue();
                    int maximum = ((Number) maximumAvailable).intValue();
                    
                    getGraphQLRateLimiter().updateFromHeaders(available, maximum);
                    
                    log.debug("Updated GraphQL rate limiter from response: {}/{} available", 
                        available, maximum);
                }
            }
        }
    }
    
    /**
     * Checks if a REST request can be made without waiting.
     */
    public boolean canMakeRestRequest() {
        return getRestRateLimiter().tryAcquire();
    }
    
    /**
     * Checks if a GraphQL request can be made without waiting.
     */
    public boolean canMakeGraphQLRequest() {
        return getGraphQLRateLimiter().tryAcquire();
    }
    
    /**
     * Checks if a GraphQL request with specific cost can be made without waiting.
     */
    public boolean canMakeGraphQLRequest(int cost) {
        return getGraphQLRateLimiter().tryAcquire(cost);
    }
    
    /**
     * Gets the current state of all rate limiters.
     */
    public RateLimitStatus getStatus() {
        return RateLimitStatus.builder()
            .restState(getRestRateLimiter().getState())
            .graphQLState(getGraphQLRateLimiter().getState())
            .config(config)
            .build();
    }
    
    /**
     * Resets all rate limiters to full capacity.
     */
    public void resetAll() {
        rateLimiters.values().forEach(RateLimiter::reset);
        log.info("Reset all rate limiters");
    }
    
    /**
     * Resets REST rate limiter.
     */
    public void resetRest() {
        getRestRateLimiter().reset();
        log.info("Reset REST rate limiter");
    }
    
    /**
     * Resets GraphQL rate limiter.
     */
    public void resetGraphQL() {
        getGraphQLRateLimiter().reset();
        log.info("Reset GraphQL rate limiter");
    }
    
    /**
     * Gets or creates the REST API rate limiter.
     */
    private RateLimiter getRestRateLimiter() {
        return rateLimiters.computeIfAbsent(REST_LIMITER_KEY, key -> 
            new RateLimiter(
                "REST API", 
                config.getRestBucketCapacity(), 
                config.getMaxRestRequestsPerSecond()
            )
        );
    }
    
    /**
     * Gets or creates the GraphQL API rate limiter.
     */
    private RateLimiter getGraphQLRateLimiter() {
        return rateLimiters.computeIfAbsent(GRAPHQL_LIMITER_KEY, key -> 
            new RateLimiter(
                "GraphQL API", 
                config.getGraphQLBucketCapacity(), 
                config.getMaxGraphQLRequestsPerSecond()
            )
        );
    }
    
    /**
     * Creates a custom rate limiter with specified parameters.
     */
    public RateLimiter createCustomRateLimiter(String name, int capacity, int refillRate) {
        RateLimiter limiter = new RateLimiter(name, capacity, refillRate);
        rateLimiters.put(name, limiter);
        return limiter;
    }
    
    /**
     * Gets a custom rate limiter by name.
     */
    public RateLimiter getRateLimiter(String name) {
        return rateLimiters.get(name);
    }
    
    /**
     * Status of all rate limiters.
     */
    @lombok.Data
    @lombok.Builder
    public static class RateLimitStatus {
        private final RateLimiter.RateLimiterState restState;
        private final RateLimiter.RateLimiterState graphQLState;
        private final RateLimitConfig config;
        
        public boolean isRestThrottled() {
            return restState.getAvailableTokens() < 5; // Consider throttled if < 5 requests available
        }
        
        public boolean isGraphQLThrottled() {
            return graphQLState.getAvailableTokens() < 100; // Consider throttled if < 100 cost available
        }
        
        public boolean isAnyThrottled() {
            return isRestThrottled() || isGraphQLThrottled();
        }
    }
}