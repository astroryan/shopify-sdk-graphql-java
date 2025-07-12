package com.shopify.sdk.config;

import com.shopify.sdk.exception.ShopifyConfigException;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Validates Shopify SDK configuration.
 * Maps to Node.js validateConfig function.
 */
public class ConfigValidator {
    
    private ConfigValidator() {
        // Utility class
    }
    
    /**
     * Validates the provided configuration and throws an exception if invalid.
     *
     * @param context the configuration to validate
     * @throws ShopifyConfigException if the configuration is invalid
     */
    public static void validate(ShopifyAuthContext context) {
        List<String> missingFields = new ArrayList<>();
        
        // Always required fields
        if (!StringUtils.hasText(context.getApiSecretKey())) {
            missingFields.add("apiSecretKey");
        }
        
        if (!StringUtils.hasText(context.getHostName())) {
            missingFields.add("hostName");
        }
        
        // Fields required for non-custom store apps
        if (!context.isCustomStoreApp()) {
            if (!StringUtils.hasText(context.getApiKey())) {
                missingFields.add("apiKey");
            }
        }
        
        // Fields required for custom store apps
        if (context.isCustomStoreApp()) {
            if (!StringUtils.hasText(context.getAdminApiAccessToken())) {
                missingFields.add("adminApiAccessToken");
            }
        }
        
        if (!missingFields.isEmpty()) {
            throw new ShopifyConfigException(
                "Cannot initialize Shopify API Library. Missing values for: " + 
                String.join(", ", missingFields)
            );
        }
        
        // Validate host name format
        validateHostName(context.getHostName());
        
        // Validate API version
        if (context.getApiVersion() == null) {
            throw new ShopifyConfigException("API version cannot be null");
        }
        
        // Warn if adminApiAccessToken equals apiSecretKey for custom store apps
        if (context.isCustomStoreApp() && 
            context.getAdminApiAccessToken() != null &&
            context.getAdminApiAccessToken().equals(context.getApiSecretKey())) {
            // This should be logged as a warning, but we'll just validate for now
        }
    }
    
    private static void validateHostName(String hostName) {
        if (hostName == null || hostName.trim().isEmpty()) {
            throw new ShopifyConfigException("Host name cannot be empty");
        }
        
        // Remove trailing slash for validation
        String cleanHostName = hostName.replaceAll("/$", "");
        
        // Basic validation - should not contain protocol
        if (cleanHostName.startsWith("http://") || cleanHostName.startsWith("https://")) {
            throw new ShopifyConfigException(
                "Host name should not include protocol (http/https). Use hostScheme instead."
            );
        }
        
        // Should not contain path
        if (cleanHostName.contains("/")) {
            throw new ShopifyConfigException(
                "Host name should not include path. Only domain name is allowed."
            );
        }
    }
}