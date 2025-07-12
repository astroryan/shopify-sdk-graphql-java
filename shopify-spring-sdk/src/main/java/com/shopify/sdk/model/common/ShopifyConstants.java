package com.shopify.sdk.model.common;

import java.util.Arrays;
import java.util.List;

/**
 * Common constants used throughout the Shopify SDK.
 * Maps to Node.js constants.
 */
public final class ShopifyConstants {
    
    private ShopifyConstants() {
        // Utility class
    }

    public static final String LIBRARY_NAME = "Shopify API Library";
    
    /**
     * Privacy-related webhook topics that require special handling.
     */
    public static final List<String> PRIVACY_TOPICS = Arrays.asList(
        "CUSTOMERS_DATA_REQUEST",
        "CUSTOMERS_REDACT", 
        "SHOP_REDACT"
    );
    
    /**
     * Default user agent for HTTP requests.
     */
    public static final String DEFAULT_USER_AGENT = "Shopify SDK Java";
    
    /**
     * Default timeout for HTTP requests (in milliseconds).
     */
    public static final int DEFAULT_TIMEOUT_MS = 30000;
    
    /**
     * Maximum number of retry attempts for failed requests.
     */
    public static final int MAX_RETRY_ATTEMPTS = 3;
    
    /**
     * Default host scheme for apps.
     */
    public static final String DEFAULT_HOST_SCHEME = "https";
}