package com.shopify.sdk.monitoring;

import lombok.Data;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * Tracks API metrics and statistics.
 */
@Data
public class ApiMetrics {
    
    // Request counters
    private final LongAdder totalRequests = new LongAdder();
    private final LongAdder successfulRequests = new LongAdder();
    private final LongAdder failedRequests = new LongAdder();
    private final LongAdder retryAttempts = new LongAdder();
    private final LongAdder rateLimitedRequests = new LongAdder();
    
    // Timing statistics
    private final AtomicLong totalResponseTimeMs = new AtomicLong(0);
    private final AtomicLong minResponseTimeMs = new AtomicLong(Long.MAX_VALUE);
    private final AtomicLong maxResponseTimeMs = new AtomicLong(0);
    
    // Error statistics
    private final LongAdder timeoutErrors = new LongAdder();
    private final LongAdder connectionErrors = new LongAdder();
    private final LongAdder authenticationErrors = new LongAdder();
    private final LongAdder serverErrors = new LongAdder();
    private final LongAdder clientErrors = new LongAdder();
    
    // Rate limiting statistics
    private final AtomicLong totalRateLimitWaitTimeMs = new AtomicLong(0);
    private final AtomicLong maxRateLimitWaitTimeMs = new AtomicLong(0);
    
    // Timestamps
    private final Instant startTime = Instant.now();
    private volatile Instant lastRequestTime = Instant.now();
    private volatile Instant lastSuccessTime;
    private volatile Instant lastFailureTime;
    
    /**
     * Records a request attempt.
     */
    public void recordRequest() {
        totalRequests.increment();
        lastRequestTime = Instant.now();
    }
    
    /**
     * Records a successful request.
     */
    public void recordSuccess(Duration responseTime) {
        successfulRequests.increment();
        lastSuccessTime = Instant.now();
        recordResponseTime(responseTime);
    }
    
    /**
     * Records a failed request.
     */
    public void recordFailure(Duration responseTime, Throwable error) {
        failedRequests.increment();
        lastFailureTime = Instant.now();
        
        if (responseTime != null) {
            recordResponseTime(responseTime);
        }
        
        // Categorize the error
        categorizeError(error);
    }
    
    /**
     * Records a retry attempt.
     */
    public void recordRetry() {
        retryAttempts.increment();
    }
    
    /**
     * Records rate limiting occurrence.
     */
    public void recordRateLimit(Duration waitTime) {
        rateLimitedRequests.increment();
        
        long waitTimeMs = waitTime.toMillis();
        totalRateLimitWaitTimeMs.addAndGet(waitTimeMs);
        
        // Update max wait time
        long currentMax = maxRateLimitWaitTimeMs.get();
        while (waitTimeMs > currentMax) {
            if (maxRateLimitWaitTimeMs.compareAndSet(currentMax, waitTimeMs)) {
                break;
            }
            currentMax = maxRateLimitWaitTimeMs.get();
        }
    }
    
    /**
     * Records response time.
     */
    private void recordResponseTime(Duration responseTime) {
        long responseTimeMs = responseTime.toMillis();
        
        totalResponseTimeMs.addAndGet(responseTimeMs);
        
        // Update min response time
        long currentMin = minResponseTimeMs.get();
        while (responseTimeMs < currentMin) {
            if (minResponseTimeMs.compareAndSet(currentMin, responseTimeMs)) {
                break;
            }
            currentMin = minResponseTimeMs.get();
        }
        
        // Update max response time
        long currentMax = maxResponseTimeMs.get();
        while (responseTimeMs > currentMax) {
            if (maxResponseTimeMs.compareAndSet(currentMax, responseTimeMs)) {
                break;
            }
            currentMax = maxResponseTimeMs.get();
        }
    }
    
    /**
     * Categorizes errors for statistics.
     */
    private void categorizeError(Throwable error) {
        if (error == null) {
            return;
        }
        
        String errorMessage = error.getMessage();
        String errorClass = error.getClass().getSimpleName();
        
        // Check for timeout errors
        if (errorMessage != null && (errorMessage.contains("timeout") || errorMessage.contains("timed out")) ||
            errorClass.contains("Timeout")) {
            timeoutErrors.increment();
        }
        // Check for connection errors
        else if (errorMessage != null && errorMessage.contains("connection") ||
                 errorClass.contains("Connection")) {
            connectionErrors.increment();
        }
        // Check for authentication errors
        else if (errorMessage != null && (errorMessage.contains("401") || errorMessage.contains("403") ||
                                         errorMessage.contains("Unauthorized") || errorMessage.contains("Forbidden"))) {
            authenticationErrors.increment();
        }
        // Check for server errors (5xx)
        else if (errorMessage != null && errorMessage.matches(".*5\\d\\d.*")) {
            serverErrors.increment();
        }
        // Check for client errors (4xx, excluding auth)
        else if (errorMessage != null && errorMessage.matches(".*4\\d\\d.*")) {
            clientErrors.increment();
        }
    }
    
    /**
     * Gets the total number of requests.
     */
    public long getTotalRequests() {
        return totalRequests.sum();
    }
    
    /**
     * Gets the number of successful requests.
     */
    public long getSuccessfulRequests() {
        return successfulRequests.sum();
    }
    
    /**
     * Gets the number of failed requests.
     */
    public long getFailedRequests() {
        return failedRequests.sum();
    }
    
    /**
     * Gets the success rate as a percentage.
     */
    public double getSuccessRate() {
        long total = getTotalRequests();
        return total > 0 ? (getSuccessfulRequests() * 100.0) / total : 0.0;
    }
    
    /**
     * Gets the failure rate as a percentage.
     */
    public double getFailureRate() {
        long total = getTotalRequests();
        return total > 0 ? (getFailedRequests() * 100.0) / total : 0.0;
    }
    
    /**
     * Gets the average response time in milliseconds.
     */
    public double getAverageResponseTimeMs() {
        long total = getSuccessfulRequests() + getFailedRequests();
        return total > 0 ? (double) totalResponseTimeMs.get() / total : 0.0;
    }
    
    /**
     * Gets the minimum response time in milliseconds.
     */
    public long getMinResponseTimeMs() {
        long min = minResponseTimeMs.get();
        return min == Long.MAX_VALUE ? 0 : min;
    }
    
    /**
     * Gets the maximum response time in milliseconds.
     */
    public long getMaxResponseTimeMs() {
        return maxResponseTimeMs.get();
    }
    
    /**
     * Gets the number of retry attempts.
     */
    public long getRetryAttempts() {
        return retryAttempts.sum();
    }
    
    /**
     * Gets the number of rate limited requests.
     */
    public long getRateLimitedRequests() {
        return rateLimitedRequests.sum();
    }
    
    /**
     * Gets the total time spent waiting for rate limits.
     */
    public Duration getTotalRateLimitWaitTime() {
        return Duration.ofMillis(totalRateLimitWaitTimeMs.get());
    }
    
    /**
     * Gets the maximum time spent waiting for a single rate limit.
     */
    public Duration getMaxRateLimitWaitTime() {
        return Duration.ofMillis(maxRateLimitWaitTimeMs.get());
    }
    
    /**
     * Gets the uptime of this metrics instance.
     */
    public Duration getUptime() {
        return Duration.between(startTime, Instant.now());
    }
    
    /**
     * Gets requests per second based on uptime.
     */
    public double getRequestsPerSecond() {
        double uptimeSeconds = getUptime().toMillis() / 1000.0;
        return uptimeSeconds > 0 ? getTotalRequests() / uptimeSeconds : 0.0;
    }
    
    /**
     * Resets all metrics.
     */
    public void reset() {
        totalRequests.reset();
        successfulRequests.reset();
        failedRequests.reset();
        retryAttempts.reset();
        rateLimitedRequests.reset();
        
        totalResponseTimeMs.set(0);
        minResponseTimeMs.set(Long.MAX_VALUE);
        maxResponseTimeMs.set(0);
        
        timeoutErrors.reset();
        connectionErrors.reset();
        authenticationErrors.reset();
        serverErrors.reset();
        clientErrors.reset();
        
        totalRateLimitWaitTimeMs.set(0);
        maxRateLimitWaitTimeMs.set(0);
        
        lastRequestTime = Instant.now();
        lastSuccessTime = null;
        lastFailureTime = null;
    }
    
    /**
     * Creates a summary of the current metrics.
     */
    public MetricsSummary getSummary() {
        return MetricsSummary.builder()
            .totalRequests(getTotalRequests())
            .successfulRequests(getSuccessfulRequests())
            .failedRequests(getFailedRequests())
            .successRate(getSuccessRate())
            .failureRate(getFailureRate())
            .averageResponseTimeMs(getAverageResponseTimeMs())
            .minResponseTimeMs(getMinResponseTimeMs())
            .maxResponseTimeMs(getMaxResponseTimeMs())
            .retryAttempts(getRetryAttempts())
            .rateLimitedRequests(getRateLimitedRequests())
            .totalRateLimitWaitTime(getTotalRateLimitWaitTime())
            .maxRateLimitWaitTime(getMaxRateLimitWaitTime())
            .requestsPerSecond(getRequestsPerSecond())
            .uptime(getUptime())
            .startTime(startTime)
            .lastRequestTime(lastRequestTime)
            .lastSuccessTime(lastSuccessTime)
            .lastFailureTime(lastFailureTime)
            .build();
    }
    
    @lombok.Data
    @lombok.Builder
    public static class MetricsSummary {
        private final long totalRequests;
        private final long successfulRequests;
        private final long failedRequests;
        private final double successRate;
        private final double failureRate;
        private final double averageResponseTimeMs;
        private final long minResponseTimeMs;
        private final long maxResponseTimeMs;
        private final long retryAttempts;
        private final long rateLimitedRequests;
        private final Duration totalRateLimitWaitTime;
        private final Duration maxRateLimitWaitTime;
        private final double requestsPerSecond;
        private final Duration uptime;
        private final Instant startTime;
        private final Instant lastRequestTime;
        private final Instant lastSuccessTime;
        private final Instant lastFailureTime;
    }
}