package com.shopify.sdk.exception;

/**
 * Exception thrown when SDK configuration is invalid.
 */
public class ShopifyConfigException extends ShopifyApiException {
    
    public ShopifyConfigException(String message) {
        super(message);
    }
    
    public ShopifyConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}