package com.shopify.sdk.exception;

/**
 * Base exception for all Shopify SDK related errors.
 * Maps to Node.js ShopifyError class.
 */
public class ShopifyApiException extends RuntimeException {
    
    public ShopifyApiException(String message) {
        super(message);
    }
    
    public ShopifyApiException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ShopifyApiException(Throwable cause) {
        super(cause);
    }
}