package com.shopify.sdk.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.shopify.sdk.client.rest.RestClient;
import com.shopify.sdk.config.ShopifyAuthContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Shopify-specific REST client that provides convenient methods for REST API operations.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ShopifyRestClient {
    
    private final RestClient restClient;
    private final ShopifyAuthContext context;
    
    /**
     * Makes a GET request to a Shopify REST endpoint.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param endpoint the API endpoint (e.g., "/products.json")
     * @param queryParams optional query parameters
     * @return Mono of JsonNode response
     */
    public Mono<JsonNode> get(String shop, String accessToken, String endpoint, Map<String, Object> queryParams) {
        validateInputs(shop, accessToken, endpoint);
        return restClient.get(shop, accessToken, endpoint, queryParams);
    }
    
    /**
     * Makes a GET request to a Shopify REST endpoint without query parameters.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param endpoint the API endpoint
     * @return Mono of JsonNode response
     */
    public Mono<JsonNode> get(String shop, String accessToken, String endpoint) {
        return get(shop, accessToken, endpoint, null);
    }
    
    /**
     * Makes a POST request to a Shopify REST endpoint.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param endpoint the API endpoint
     * @param body the request body
     * @return Mono of JsonNode response
     */
    public Mono<JsonNode> post(String shop, String accessToken, String endpoint, Object body) {
        validateInputs(shop, accessToken, endpoint);
        return restClient.post(shop, accessToken, endpoint, body);
    }
    
    /**
     * Makes a PUT request to a Shopify REST endpoint.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param endpoint the API endpoint
     * @param body the request body
     * @return Mono of JsonNode response
     */
    public Mono<JsonNode> put(String shop, String accessToken, String endpoint, Object body) {
        validateInputs(shop, accessToken, endpoint);
        return restClient.put(shop, accessToken, endpoint, body);
    }
    
    /**
     * Makes a DELETE request to a Shopify REST endpoint.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param endpoint the API endpoint
     * @return Mono of JsonNode response
     */
    public Mono<JsonNode> delete(String shop, String accessToken, String endpoint) {
        validateInputs(shop, accessToken, endpoint);
        return restClient.delete(shop, accessToken, endpoint);
    }
    
    /**
     * Gets all products using the REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param limit the number of products to retrieve (max 250)
     * @param pageInfo pagination info for cursor-based pagination
     * @return Mono of JsonNode containing products
     */
    public Mono<JsonNode> getProducts(String shop, String accessToken, Integer limit, String pageInfo) {
        Map<String, Object> queryParams = Map.of(
            "limit", limit != null ? Math.min(limit, 250) : 50
        );
        
        if (pageInfo != null && !pageInfo.trim().isEmpty()) {
            queryParams = Map.of(
                "limit", limit != null ? Math.min(limit, 250) : 50,
                "page_info", pageInfo
            );
        }
        
        return get(shop, accessToken, "/products.json", queryParams);
    }
    
    /**
     * Gets a single product by ID using the REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param productId the product ID
     * @return Mono of JsonNode containing the product
     */
    public Mono<JsonNode> getProduct(String shop, String accessToken, String productId) {
        return get(shop, accessToken, String.format("/products/%s.json", productId));
    }
    
    /**
     * Gets all orders using the REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param limit the number of orders to retrieve (max 250)
     * @param status the order status filter
     * @param pageInfo pagination info for cursor-based pagination
     * @return Mono of JsonNode containing orders
     */
    public Mono<JsonNode> getOrders(String shop, String accessToken, Integer limit, String status, String pageInfo) {
        Map<String, Object> queryParams = Map.of(
            "limit", limit != null ? Math.min(limit, 250) : 50
        );
        
        if (status != null && !status.trim().isEmpty()) {
            queryParams = Map.of(
                "limit", limit != null ? Math.min(limit, 250) : 50,
                "status", status
            );
        }
        
        if (pageInfo != null && !pageInfo.trim().isEmpty()) {
            if (status != null && !status.trim().isEmpty()) {
                queryParams = Map.of(
                    "limit", limit != null ? Math.min(limit, 250) : 50,
                    "status", status,
                    "page_info", pageInfo
                );
            } else {
                queryParams = Map.of(
                    "limit", limit != null ? Math.min(limit, 250) : 50,
                    "page_info", pageInfo
                );
            }
        }
        
        return get(shop, accessToken, "/orders.json", queryParams);
    }
    
    /**
     * Gets a single order by ID using the REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param orderId the order ID
     * @return Mono of JsonNode containing the order
     */
    public Mono<JsonNode> getOrder(String shop, String accessToken, String orderId) {
        return get(shop, accessToken, String.format("/orders/%s.json", orderId));
    }
    
    /**
     * Creates a product using the REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param productData the product data
     * @return Mono of JsonNode containing the created product
     */
    public Mono<JsonNode> createProduct(String shop, String accessToken, Object productData) {
        return post(shop, accessToken, "/products.json", productData);
    }
    
    /**
     * Updates a product using the REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param productId the product ID
     * @param productData the updated product data
     * @return Mono of JsonNode containing the updated product
     */
    public Mono<JsonNode> updateProduct(String shop, String accessToken, String productId, Object productData) {
        return put(shop, accessToken, String.format("/products/%s.json", productId), productData);
    }
    
    /**
     * Deletes a product using the REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param productId the product ID
     * @return Mono of JsonNode response
     */
    public Mono<JsonNode> deleteProduct(String shop, String accessToken, String productId) {
        return delete(shop, accessToken, String.format("/products/%s.json", productId));
    }
    
    private void validateInputs(String shop, String accessToken, String endpoint) {
        if (shop == null || shop.trim().isEmpty()) {
            throw new IllegalArgumentException("Shop domain cannot be null or empty");
        }
        if (accessToken == null || accessToken.trim().isEmpty()) {
            throw new IllegalArgumentException("Access token cannot be null or empty");
        }
        if (endpoint == null || endpoint.trim().isEmpty()) {
            throw new IllegalArgumentException("Endpoint cannot be null or empty");
        }
    }
}