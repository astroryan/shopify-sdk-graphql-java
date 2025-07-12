package com.shopify.sdk.exception;

/**
 * Exception thrown when authentication fails.
 */
public class ShopifyAuthException extends ShopifyApiException {
    
    public ShopifyAuthException(String message) {
        super(message);
    }
    
    public ShopifyAuthException(String message, Throwable cause) {
        super(message, cause);
    }
}