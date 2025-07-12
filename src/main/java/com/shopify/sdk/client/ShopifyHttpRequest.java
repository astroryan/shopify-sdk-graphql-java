package com.shopify.sdk.client;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * Represents an HTTP request to the Shopify API.
 */
@Data
@Builder(toBuilder = true)
public class ShopifyHttpRequest {
    
    private final HttpMethod method;
    private final String url;
    private final Map<String, String> headers;
    private final String body;
    private final Map<String, Object> queryParams;
    
    public static ShopifyHttpRequestBuilder get(String url) {
        return ShopifyHttpRequest.builder()
            .method(HttpMethod.GET)
            .url(url);
    }
    
    public static ShopifyHttpRequestBuilder post(String url) {
        return ShopifyHttpRequest.builder()
            .method(HttpMethod.POST)
            .url(url);
    }
    
    public static ShopifyHttpRequestBuilder put(String url) {
        return ShopifyHttpRequest.builder()
            .method(HttpMethod.PUT)
            .url(url);
    }
    
    public static ShopifyHttpRequestBuilder delete(String url) {
        return ShopifyHttpRequest.builder()
            .method(HttpMethod.DELETE)
            .url(url);
    }
}