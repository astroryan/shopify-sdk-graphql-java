package com.shopify.sdk.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * Represents an HTTP response from the Shopify API.
 */
@Data
@AllArgsConstructor
public class ShopifyHttpResponse {
    
    private final HttpStatus status;
    private final Map<String, String> headers;
    private final String body;
    
    public int getStatusCode() {
        return status.value();
    }
    
    public boolean isSuccessful() {
        return status.is2xxSuccessful();
    }
    
    public boolean isClientError() {
        return status.is4xxClientError();
    }
    
    public boolean isServerError() {
        return status.is5xxServerError();
    }
    
    public String getHeader(String name) {
        return headers.get(name);
    }
    
    public boolean hasHeader(String name) {
        return headers.containsKey(name);
    }
}