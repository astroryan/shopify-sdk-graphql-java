package com.shopify.sdk.client.rest;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Interface for making REST API calls to Shopify.
 */
public interface RestClient {
    
    /**
     * Makes a GET request to the specified endpoint.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param endpoint the API endpoint
     * @param queryParams optional query parameters
     * @return Mono of JsonNode response
     */
    Mono<JsonNode> get(String shop, String accessToken, String endpoint, Map<String, Object> queryParams);
    
    /**
     * Makes a POST request to the specified endpoint.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param endpoint the API endpoint
     * @param body the request body
     * @return Mono of JsonNode response
     */
    Mono<JsonNode> post(String shop, String accessToken, String endpoint, Object body);
    
    /**
     * Makes a PUT request to the specified endpoint.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param endpoint the API endpoint
     * @param body the request body
     * @return Mono of JsonNode response
     */
    Mono<JsonNode> put(String shop, String accessToken, String endpoint, Object body);
    
    /**
     * Makes a DELETE request to the specified endpoint.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param endpoint the API endpoint
     * @return Mono of JsonNode response
     */
    Mono<JsonNode> delete(String shop, String accessToken, String endpoint);
}