package com.shopify.sdk.exception;

public class ShopifyRateLimitException extends ShopifyApiException {
    
    public ShopifyRateLimitException(String message) {
        super(message);
    }
    
    public ShopifyRateLimitException(String message, Throwable cause) {
        super(message, cause);
    }
}