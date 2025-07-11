package com.shopify.sdk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Data
@Component
@ConfigurationProperties(prefix = "shopify.sdk")
public class ShopifyProperties {
    
    private Api api = new Api();
    private GraphQL graphql = new GraphQL();
    private RateLimit rateLimit = new RateLimit();
    private Logging logging = new Logging();
    
    @Data
    public static class Api {
        private String version = "2025-07";
        private Timeout timeout = new Timeout();
        private Retry retry = new Retry();
        
        @Data
        public static class Timeout {
            private Duration connect = Duration.ofSeconds(10);
            private Duration read = Duration.ofSeconds(30);
            private Duration write = Duration.ofSeconds(30);
        }
        
        @Data
        public static class Retry {
            private int maxAttempts = 3;
            private long backoffDelay = 1000;
        }
    }
    
    @Data
    public static class GraphQL {
        private String endpoint = "https://{shop}.myshopify.com/admin/api/{version}/graphql.json";
        private int maxQueryDepth = 10;
        private int maxQueryComplexity = 1000;
    }
    
    @Data
    public static class RateLimit {
        private boolean enabled = true;
        private int maxCallsPerSecond = 2;
        private int bucketSize = 40;
    }
    
    @Data
    public static class Logging {
        private String level = "INFO";
        private boolean logRequests = false;
        private boolean logResponses = false;
    }
}