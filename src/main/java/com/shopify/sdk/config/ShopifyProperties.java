package com.shopify.sdk.config;

import com.shopify.sdk.model.common.ApiVersion;
import com.shopify.sdk.model.common.LogSeverity;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Spring Boot configuration properties for Shopify SDK.
 * Maps configuration from application.properties/yaml.
 */
@Data
@ConfigurationProperties(prefix = "shopify")
public class ShopifyProperties {
    
    /**
     * The API key for your app (Client ID).
     */
    private String apiKey;
    
    /**
     * The API secret key for your app (Client Secret).
     */
    private String apiSecretKey;
    
    /**
     * The scopes your app needs to access the API.
     */
    private List<String> scopes;
    
    /**
     * The host name of your app.
     */
    private String hostName;
    
    /**
     * The scheme to use for the app host.
     */
    private String hostScheme = "https";
    
    /**
     * The API version to use.
     */
    private ApiVersion apiVersion = ApiVersion.LATEST;
    
    /**
     * Whether the app is embedded in the Shopify admin.
     */
    private boolean isEmbeddedApp = true;
    
    /**
     * Whether the app is a Shopify admin custom store app.
     */
    private boolean isCustomStoreApp = false;
    
    /**
     * An app-wide API access token (for custom apps).
     */
    private String adminApiAccessToken;
    
    /**
     * The user agent prefix to use for API requests.
     */
    private String userAgentPrefix;
    
    /**
     * An app-wide API access token for the storefront API (for custom apps).
     */
    private String privateAppStorefrontAccessToken;
    
    /**
     * Override values for Shopify shop domains.
     */
    private List<String> customShopDomains;
    
    /**
     * Logging configuration.
     */
    private final Logging logging = new Logging();
    
    /**
     * Whether the app is initialized for local testing.
     */
    private boolean isTesting = false;
    
    @Data
    public static class Logging {
        /**
         * The minimum severity level to log.
         */
        private LogSeverity level = LogSeverity.INFO;
        
        /**
         * Whether to log HTTP requests.
         */
        private boolean httpRequests = false;
        
        /**
         * Whether to log timestamps.
         */
        private boolean timestamps = false;
    }
}