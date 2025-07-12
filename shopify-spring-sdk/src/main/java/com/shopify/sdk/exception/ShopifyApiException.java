package com.shopify.sdk.exception;

import java.util.List;

import com.shopify.sdk.model.graphql.GraphQLError;

public class ShopifyApiException extends RuntimeException {
    
    public ShopifyApiException(String message) {
        super(message);
    }
    
    public ShopifyApiException(String message, Throwable cause) {
        super(message, cause);
    }
}