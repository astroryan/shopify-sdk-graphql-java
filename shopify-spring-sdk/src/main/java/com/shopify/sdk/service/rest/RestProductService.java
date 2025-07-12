package com.shopify.sdk.service.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.client.ShopifyRestClient;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.product.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST-based service for managing Shopify products.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RestProductService {
    
    private final ShopifyRestClient restClient;
    private final ObjectMapper objectMapper;
    
    /**
     * Retrieves a single product by ID using REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param productId the product ID
     * @return Mono of Product
     */
    public Mono<Product> getProduct(String shop, String accessToken, String productId) {
        return restClient.getProduct(shop, accessToken, productId)
            .map(this::extractSingleProduct);
    }
    
    /**
     * Retrieves a list of products using REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param limit the number of products to retrieve (max 250)
     * @param pageInfo pagination info for cursor-based pagination
     * @return Mono containing list of products
     */
    public Mono<List<Product>> getProducts(String shop, String accessToken, Integer limit, String pageInfo) {
        return restClient.getProducts(shop, accessToken, limit, pageInfo)
            .map(this::extractProducts);
    }
    
    /**
     * Creates a new product using REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param productData the product data
     * @return Mono of created Product
     */
    public Mono<Product> createProduct(String shop, String accessToken, Map<String, Object> productData) {
        Map<String, Object> payload = Map.of("product", productData);
        
        return restClient.createProduct(shop, accessToken, payload)
            .map(this::extractSingleProduct);
    }
    
    /**
     * Updates an existing product using REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param productId the product ID
     * @param productData the updated product data
     * @return Mono of updated Product
     */
    public Mono<Product> updateProduct(String shop, String accessToken, String productId, Map<String, Object> productData) {
        Map<String, Object> payload = Map.of("product", productData);
        
        return restClient.updateProduct(shop, accessToken, productId, payload)
            .map(this::extractSingleProduct);
    }
    
    /**
     * Deletes a product using REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param productId the product ID
     * @return Mono<Void> indicating completion
     */
    public Mono<Void> deleteProduct(String shop, String accessToken, String productId) {
        return restClient.deleteProduct(shop, accessToken, productId)
            .then();
    }
    
    /**
     * Gets products with specific vendor using REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param vendor the vendor name
     * @param limit the number of products to retrieve
     * @return Mono containing list of products
     */
    public Mono<List<Product>> getProductsByVendor(String shop, String accessToken, String vendor, Integer limit) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("vendor", vendor);
        queryParams.put("limit", limit != null ? Math.min(limit, 250) : 50);
        
        return restClient.get(shop, accessToken, "/products.json", queryParams)
            .map(this::extractProducts);
    }
    
    /**
     * Gets products with specific product type using REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param productType the product type
     * @param limit the number of products to retrieve
     * @return Mono containing list of products
     */
    public Mono<List<Product>> getProductsByType(String shop, String accessToken, String productType, Integer limit) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("product_type", productType);
        queryParams.put("limit", limit != null ? Math.min(limit, 250) : 50);
        
        return restClient.get(shop, accessToken, "/products.json", queryParams)
            .map(this::extractProducts);
    }
    
    /**
     * Gets the product count using REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @return Mono containing the product count
     */
    public Mono<Integer> getProductCount(String shop, String accessToken) {
        return restClient.get(shop, accessToken, "/products/count.json")
            .map(response -> {
                if (response.has("count")) {
                    return response.get("count").asInt();
                }
                return 0;
            });
    }
    
    private Product extractSingleProduct(JsonNode response) {
        try {
            if (response.has("product")) {
                JsonNode productNode = response.get("product");
                return objectMapper.treeToValue(productNode, Product.class);
            }
            throw new ShopifyApiException("Product not found in response");
        } catch (Exception e) {
            log.error("Error extracting product from response", e);
            throw new ShopifyApiException("Failed to parse product response", e);
        }
    }
    
    private List<Product> extractProducts(JsonNode response) {
        try {
            if (response.has("products")) {
                JsonNode productsNode = response.get("products");
                if (productsNode.isArray()) {
                    return objectMapper.convertValue(productsNode, 
                        objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));
                }
            }
            return List.of();
        } catch (Exception e) {
            log.error("Error extracting products from response", e);
            throw new ShopifyApiException("Failed to parse products response", e);
        }
    }
}