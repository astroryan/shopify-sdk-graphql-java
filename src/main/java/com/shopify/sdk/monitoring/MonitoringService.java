package com.shopify.sdk.monitoring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Service for monitoring Shopify API usage and performance.
 */
@Slf4j
@Service
public class MonitoringService {
    
    private final Map<String, ApiMetrics> metricsMap = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private volatile boolean monitoringEnabled = true;
    
    // Default metrics for overall API usage
    private static final String GLOBAL_METRICS_KEY = "global";
    private static final String REST_METRICS_KEY = "rest";
    private static final String GRAPHQL_METRICS_KEY = "graphql";
    
    public MonitoringService() {
        // Initialize default metrics
        metricsMap.put(GLOBAL_METRICS_KEY, new ApiMetrics());
        metricsMap.put(REST_METRICS_KEY, new ApiMetrics());
        metricsMap.put(GRAPHQL_METRICS_KEY, new ApiMetrics());
        
        // Start periodic reporting
        startPeriodicReporting();
    }
    
    /**
     * Records a REST API request start.
     */
    public void recordRestRequest() {
        if (!monitoringEnabled) return;
        
        getGlobalMetrics().recordRequest();
        getRestMetrics().recordRequest();
    }
    
    /**
     * Records a GraphQL API request start.
     */
    public void recordGraphQLRequest() {
        if (!monitoringEnabled) return;
        
        getGlobalMetrics().recordRequest();
        getGraphQLMetrics().recordRequest();
    }
    
    /**
     * Records a successful REST API response.
     */
    public void recordRestSuccess(Duration responseTime) {
        if (!monitoringEnabled) return;
        
        getGlobalMetrics().recordSuccess(responseTime);
        getRestMetrics().recordSuccess(responseTime);
    }
    
    /**
     * Records a successful GraphQL API response.
     */
    public void recordGraphQLSuccess(Duration responseTime) {
        if (!monitoringEnabled) return;
        
        getGlobalMetrics().recordSuccess(responseTime);
        getGraphQLMetrics().recordSuccess(responseTime);
    }
    
    /**
     * Records a failed REST API response.
     */
    public void recordRestFailure(Duration responseTime, Throwable error) {
        if (!monitoringEnabled) return;
        
        getGlobalMetrics().recordFailure(responseTime, error);
        getRestMetrics().recordFailure(responseTime, error);
    }
    
    /**
     * Records a failed GraphQL API response.
     */
    public void recordGraphQLFailure(Duration responseTime, Throwable error) {
        if (!monitoringEnabled) return;
        
        getGlobalMetrics().recordFailure(responseTime, error);
        getGraphQLMetrics().recordFailure(responseTime, error);
    }
    
    /**
     * Records a retry attempt.
     */
    public void recordRetry(String apiType) {
        if (!monitoringEnabled) return;
        
        getGlobalMetrics().recordRetry();
        
        if ("rest".equalsIgnoreCase(apiType)) {
            getRestMetrics().recordRetry();
        } else if ("graphql".equalsIgnoreCase(apiType)) {
            getGraphQLMetrics().recordRetry();
        }
    }
    
    /**
     * Records rate limiting.
     */
    public void recordRateLimit(String apiType, Duration waitTime) {
        if (!monitoringEnabled) return;
        
        getGlobalMetrics().recordRateLimit(waitTime);
        
        if ("rest".equalsIgnoreCase(apiType)) {
            getRestMetrics().recordRateLimit(waitTime);
        } else if ("graphql".equalsIgnoreCase(apiType)) {
            getGraphQLMetrics().recordRateLimit(waitTime);
        }
    }
    
    /**
     * Gets global API metrics.
     */
    public ApiMetrics getGlobalMetrics() {
        return metricsMap.get(GLOBAL_METRICS_KEY);
    }
    
    /**
     * Gets REST API metrics.
     */
    public ApiMetrics getRestMetrics() {
        return metricsMap.get(REST_METRICS_KEY);
    }
    
    /**
     * Gets GraphQL API metrics.
     */
    public ApiMetrics getGraphQLMetrics() {
        return metricsMap.get(GRAPHQL_METRICS_KEY);
    }
    
    /**
     * Gets or creates custom metrics for a specific component.
     */
    public ApiMetrics getOrCreateMetrics(String name) {
        return metricsMap.computeIfAbsent(name, k -> new ApiMetrics());
    }
    
    /**
     * Gets metrics by name.
     */
    public ApiMetrics getMetrics(String name) {
        return metricsMap.get(name);
    }
    
    /**
     * Gets all available metrics.
     */
    public Map<String, ApiMetrics> getAllMetrics() {
        return Map.copyOf(metricsMap);
    }
    
    /**
     * Resets all metrics.
     */
    public void resetAllMetrics() {
        metricsMap.values().forEach(ApiMetrics::reset);
        log.info("All metrics have been reset");
    }
    
    /**
     * Resets specific metrics.
     */
    public void resetMetrics(String name) {
        ApiMetrics metrics = metricsMap.get(name);
        if (metrics != null) {
            metrics.reset();
            log.info("Metrics '{}' have been reset", name);
        }
    }
    
    /**
     * Gets a comprehensive monitoring report.
     */
    public MonitoringReport getReport() {
        return MonitoringReport.builder()
            .timestamp(Instant.now())
            .globalSummary(getGlobalMetrics().getSummary())
            .restSummary(getRestMetrics().getSummary())
            .graphQLSummary(getGraphQLMetrics().getSummary())
            .customMetrics(metricsMap.entrySet().stream()
                .filter(entry -> !isSystemMetrics(entry.getKey()))
                .collect(java.util.stream.Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> entry.getValue().getSummary()
                )))
            .monitoringEnabled(monitoringEnabled)
            .build();
    }
    
    /**
     * Enables monitoring.
     */
    public void enableMonitoring() {
        monitoringEnabled = true;
        log.info("Monitoring enabled");
    }
    
    /**
     * Disables monitoring.
     */
    public void disableMonitoring() {
        monitoringEnabled = false;
        log.info("Monitoring disabled");
    }
    
    /**
     * Checks if monitoring is enabled.
     */
    public boolean isMonitoringEnabled() {
        return monitoringEnabled;
    }
    
    /**
     * Logs a summary of current metrics.
     */
    public void logSummary() {
        if (!monitoringEnabled) {
            log.info("Monitoring is disabled");
            return;
        }
        
        ApiMetrics.MetricsSummary global = getGlobalMetrics().getSummary();
        ApiMetrics.MetricsSummary rest = getRestMetrics().getSummary();
        ApiMetrics.MetricsSummary graphql = getGraphQLMetrics().getSummary();
        
        log.info("=== Shopify API Metrics Summary ===");
        log.info("Global: {} total requests, {:.1f}% success rate, {:.1f}ms avg response time, {:.2f} req/sec",
            global.getTotalRequests(), global.getSuccessRate(), 
            global.getAverageResponseTimeMs(), global.getRequestsPerSecond());
        
        log.info("REST: {} requests, {:.1f}% success, {:.1f}ms avg response",
            rest.getTotalRequests(), rest.getSuccessRate(), rest.getAverageResponseTimeMs());
        
        log.info("GraphQL: {} requests, {:.1f}% success, {:.1f}ms avg response",
            graphql.getTotalRequests(), graphql.getSuccessRate(), graphql.getAverageResponseTimeMs());
        
        if (global.getRetryAttempts() > 0) {
            log.info("Retries: {} attempts, Rate limits: {} requests",
                global.getRetryAttempts(), global.getRateLimitedRequests());
        }
        
        log.info("Uptime: {}", formatDuration(global.getUptime()));
    }
    
    /**
     * Starts periodic reporting of metrics.
     */
    private void startPeriodicReporting() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                if (monitoringEnabled) {
                    logSummary();
                }
            } catch (Exception e) {
                log.error("Error during periodic metrics reporting", e);
            }
        }, 5, 60, TimeUnit.MINUTES); // Report every hour, start after 5 minutes
    }
    
    /**
     * Checks if a metrics key is a system metrics.
     */
    private boolean isSystemMetrics(String key) {
        return GLOBAL_METRICS_KEY.equals(key) || 
               REST_METRICS_KEY.equals(key) || 
               GRAPHQL_METRICS_KEY.equals(key);
    }
    
    /**
     * Formats a duration for display.
     */
    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        
        if (hours > 0) {
            return String.format("%dh %dm %ds", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%dm %ds", minutes, seconds);
        } else {
            return String.format("%ds", seconds);
        }
    }
    
    /**
     * Shuts down the monitoring service.
     */
    public void shutdown() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
        log.info("Monitoring service shut down");
    }
    
    /**
     * Comprehensive monitoring report.
     */
    @lombok.Data
    @lombok.Builder
    public static class MonitoringReport {
        private final Instant timestamp;
        private final ApiMetrics.MetricsSummary globalSummary;
        private final ApiMetrics.MetricsSummary restSummary;
        private final ApiMetrics.MetricsSummary graphQLSummary;
        private final Map<String, ApiMetrics.MetricsSummary> customMetrics;
        private final boolean monitoringEnabled;
        
        public boolean hasErrors() {
            return globalSummary.getFailedRequests() > 0;
        }
        
        public boolean hasHighFailureRate() {
            return globalSummary.getFailureRate() > 10.0; // More than 10% failure rate
        }
        
        public boolean hasSlowResponses() {
            return globalSummary.getAverageResponseTimeMs() > 2000; // More than 2 seconds average
        }
        
        public boolean hasRateLimiting() {
            return globalSummary.getRateLimitedRequests() > 0;
        }
    }
}