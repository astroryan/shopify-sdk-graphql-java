package com.shopify.sdk.config;

import com.shopify.sdk.model.common.LogFunction;
import com.shopify.sdk.model.common.LogSeverity;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Factory for creating and validating Shopify SDK configuration.
 */
@Component
public class ShopifyConfigFactory {
    
    /**
     * Creates a ShopifyAuthContext from ShopifyProperties.
     *
     * @param properties the Spring Boot configuration properties
     * @return validated ShopifyAuthContext
     */
    public ShopifyAuthContext createContext(ShopifyProperties properties) {
        return createContext(properties, null);
    }
    
    /**
     * Creates a ShopifyAuthContext from ShopifyProperties with custom log function.
     *
     * @param properties the Spring Boot configuration properties
     * @param logFunction custom log function (optional)
     * @return validated ShopifyAuthContext
     */
    public ShopifyAuthContext createContext(ShopifyProperties properties, LogFunction logFunction) {
        ShopifyAuthContext.ShopifyAuthContextBuilder builder = ShopifyAuthContext.builder()
            .apiKey(properties.getApiKey())
            .apiSecretKey(properties.getApiSecretKey())
            .scopes(properties.getScopes())
            .hostName(cleanHostName(properties.getHostName()))
            .hostScheme(properties.getHostScheme())
            .apiVersion(properties.getApiVersion())
            .isEmbeddedApp(properties.isEmbeddedApp())
            .isCustomStoreApp(properties.isCustomStoreApp())
            .adminApiAccessToken(properties.getAdminApiAccessToken())
            .userAgentPrefix(properties.getUserAgentPrefix())
            .privateAppStorefrontAccessToken(properties.getPrivateAppStorefrontAccessToken())
            .logLevel(properties.getLogging().getLevel())
            .logHttpRequests(properties.getLogging().isHttpRequests())
            .logTimestamps(properties.getLogging().isTimestamps())
            .isTesting(properties.isTesting());
        
        // Convert custom shop domains to Pattern objects
        if (properties.getCustomShopDomains() != null) {
            builder.customShopDomains(
                properties.getCustomShopDomains().stream()
                    .map(Pattern::compile)
                    .collect(Collectors.toList())
            );
        }
        
        // Set custom log function if provided
        if (logFunction != null) {
            builder.logFunction(logFunction);
        } else {
            builder.logFunction(this::defaultLogFunction);
        }
        
        ShopifyAuthContext context = builder.build();
        
        // Validate the configuration
        ConfigValidator.validate(context);
        
        return context;
    }
    
    /**
     * Creates a basic ShopifyAuthContext for testing.
     *
     * @param apiKey the API key
     * @param apiSecretKey the API secret key
     * @param hostName the host name
     * @return ShopifyAuthContext for testing
     */
    public ShopifyAuthContext createTestContext(String apiKey, String apiSecretKey, String hostName) {
        ShopifyAuthContext context = ShopifyAuthContext.builder()
            .apiKey(apiKey)
            .apiSecretKey(apiSecretKey)
            .hostName(hostName)
            .isTesting(true)
            .logFunction(this::defaultLogFunction)
            .build();
        
        ConfigValidator.validate(context);
        return context;
    }
    
    private String cleanHostName(String hostName) {
        if (hostName == null) {
            return null;
        }
        return hostName.replaceAll("/$", "");
    }
    
    private void defaultLogFunction(LogSeverity severity, String message) {
        switch (severity) {
            case DEBUG:
                System.out.println("[DEBUG] " + message);
                break;
            case INFO:
                System.out.println("[INFO] " + message);
                break;
            case WARNING:
                System.err.println("[WARNING] " + message);
                break;
            case ERROR:
                System.err.println("[ERROR] " + message);
                break;
        }
    }
}