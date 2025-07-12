package com.shopify.sdk.exception;

import lombok.Getter;

import java.util.Map;

/**
 * Exception thrown when HTTP requests to Shopify API fail.
 */
@Getter
public class ShopifyHttpException extends ShopifyApiException {
    
    private final int statusCode;
    private final String responseBody;
    private final Map<String, String> headers;
    
    public ShopifyHttpException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.responseBody = null;
        this.headers = null;
    }
    
    public ShopifyHttpException(int statusCode, String message, String responseBody) {
        super(message);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.headers = null;
    }
    
    public ShopifyHttpException(int statusCode, String message, String responseBody, Map<String, String> headers) {
        super(message);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.headers = headers;
    }
    
    public ShopifyHttpException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
        this.responseBody = null;
        this.headers = null;
    }
    
    public boolean isClientError() {
        return statusCode >= 400 && statusCode < 500;
    }
    
    public boolean isServerError() {
        return statusCode >= 500;
    }
    
    public boolean isRateLimited() {
        return statusCode == 429;
    }
    
    public boolean isUnauthorized() {
        return statusCode == 401;
    }
    
    public boolean isForbidden() {
        return statusCode == 403;
    }
    
    public boolean isNotFound() {
        return statusCode == 404;
    }
}