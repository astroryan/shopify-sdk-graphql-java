package com.shopify.sdk.exception;

/**
 * Exception thrown when webhook processing fails.
 */
public class ShopifyWebhookException extends ShopifyApiException {
    
    public ShopifyWebhookException(String message) {
        super(message);
    }
    
    public ShopifyWebhookException(String message, Throwable cause) {
        super(message, cause);
    }
}