package com.shopify.sdk.config;

import com.shopify.sdk.model.common.ApiVersion;
import com.shopify.sdk.model.common.LogFunction;
import com.shopify.sdk.model.common.LogSeverity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Configuration context for Shopify SDK.
 * Maps to Node.js ConfigInterface and ConfigParams.
 */
@Data
@Builder(toBuilder = true)
public class ShopifyAuthContext {
    
    /**
     * The API key for your app (Client ID).
     */
    private final String apiKey;
    
    /**
     * The API secret key for your app (Client Secret).
     */
    private final String apiSecretKey;
    
    /**
     * The scopes your app needs to access the API.
     */
    private final List<String> scopes;
    
    /**
     * The host name of your app.
     */
    private final String hostName;
    
    /**
     * The scheme to use for the app host.
     */
    @Builder.Default
    private final String hostScheme = "https";
    
    /**
     * The API version to use.
     */
    @Builder.Default
    private final ApiVersion apiVersion = ApiVersion.LATEST;
    
    /**
     * Whether the app is embedded in the Shopify admin.
     */
    @Builder.Default
    private final boolean isEmbeddedApp = true;
    
    /**
     * Whether the app is a Shopify admin custom store app.
     */
    @Builder.Default
    private final boolean isCustomStoreApp = false;
    
    /**
     * An app-wide API access token (for custom apps).
     */
    private final String adminApiAccessToken;
    
    /**
     * The user agent prefix to use for API requests.
     */
    private final String userAgentPrefix;
    
    /**
     * An app-wide API access token for the storefront API (for custom apps).
     */
    private final String privateAppStorefrontAccessToken;
    
    /**
     * Override values for Shopify shop domains.
     */
    private final List<Pattern> customShopDomains;
    
    /**
     * Custom log function.
     */
    private final LogFunction logFunction;
    
    /**
     * The minimum severity level to log.
     */
    @Builder.Default
    private final LogSeverity logLevel = LogSeverity.INFO;
    
    /**
     * Whether to log HTTP requests.
     */
    @Builder.Default
    private final boolean logHttpRequests = false;
    
    /**
     * Whether to log timestamps.
     */
    @Builder.Default
    private final boolean logTimestamps = false;
    
    /**
     * Whether the app is initialized for local testing.
     */
    @Builder.Default
    private final boolean isTesting = false;
    
    /**
     * The webhook secret key for verifying webhook signatures.
     */
    private final String webhookSecret;
    
    public String getFullHostName() {
        return hostScheme + "://" + hostName;
    }
    
    public boolean hasValidApiCredentials() {
        if (isCustomStoreApp) {
            return apiSecretKey != null && !apiSecretKey.trim().isEmpty() &&
                   adminApiAccessToken != null && !adminApiAccessToken.trim().isEmpty();
        } else {
            return apiKey != null && !apiKey.trim().isEmpty() &&
                   apiSecretKey != null && !apiSecretKey.trim().isEmpty();
        }
    }
    
    /**
     * Gets the API secret for authentication.
     * This is a convenience method that returns apiSecretKey.
     *
     * @return the API secret
     */
    public String getApiSecret() {
        return apiSecretKey;
    }
    
    /**
     * Checks if webhook verification is enabled.
     *
     * @return true if webhook secret is configured
     */
    public boolean hasWebhookSecret() {
        return webhookSecret != null && !webhookSecret.trim().isEmpty();
    }
}