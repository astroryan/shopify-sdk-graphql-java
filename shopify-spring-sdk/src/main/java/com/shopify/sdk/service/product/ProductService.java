package com.shopify.sdk.service.product;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.client.graphql.GraphQLResponse;
import com.shopify.sdk.config.ShopifyAuthContext;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.product.Product;
import com.shopify.sdk.model.product.ProductConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * Service for managing Shopify products.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ShopifyGraphQLClient graphQLClient;
    private final ObjectMapper objectMapper;
    
    /**
     * Retrieves a single product by ID.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param productId the product ID
     * @return Mono of Product
     */
    public Mono<Product> getProduct(String shop, String accessToken, String productId) {
        String query = """
            query getProduct($id: ID!) {
                product(id: $id) {
                    id
                    title
                    handle
                    description
                    descriptionHtml
                    createdAt
                    updatedAt
                    publishedAt
                    productType
                    vendor
                    tags
                    status
                    templateSuffix
                    tracksInventory
                    isGiftCard
                    totalInventory
                    totalVariants
                    hasOnlyDefaultVariant
                    requiresSellingPlan
                    seo {
                        title
                        description
                    }
                    onlineStoreUrl
                    onlineStorePreviewUrl
                }
            }
            """;
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", productId);
        
        return graphQLClient.query(shop, accessToken, query, variables)
            .map(this::extractProductFromResponse);
    }
    
    /**
     * Retrieves a list of products with pagination.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param first the number of products to retrieve
     * @param after the cursor for pagination
     * @param query the search query (optional)
     * @return Mono of ProductConnection
     */
    public Mono<ProductConnection> getProducts(String shop, String accessToken, Integer first, String after, String query) {
        StringBuilder graphQLQuery = new StringBuilder();
        graphQLQuery.append("""
            query getProducts($first: Int!, $after: String, $query: String) {
                products(first: $first, after: $after, query: $query) {
                    edges {
                        node {
                            id
                            title
                            handle
                            description
                            createdAt
                            updatedAt
                            publishedAt
                            productType
                            vendor
                            tags
                            status
                            totalInventory
                            totalVariants
                            onlineStoreUrl
                        }
                        cursor
                    }
                    pageInfo {
                        hasNextPage
                        hasPreviousPage
                        startCursor
                        endCursor
                    }
                }
            }
            """);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first != null ? first : 10);
        if (after != null) {
            variables.put("after", after);
        }
        if (query != null) {
            variables.put("query", query);
        }
        
        return graphQLClient.query(shop, accessToken, graphQLQuery.toString(), variables)
            .map(this::extractProductConnectionFromResponse);
    }
    
    /**
     * Creates a new product.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param input the product input data
     * @return Mono of Product
     */
    public Mono<Product> createProduct(String shop, String accessToken, ProductInput input) {
        String mutation = """
            mutation productCreate($input: ProductInput!) {
                productCreate(input: $input) {
                    product {
                        id
                        title
                        handle
                        description
                        descriptionHtml
                        createdAt
                        updatedAt
                        publishedAt
                        productType
                        vendor
                        tags
                        status
                        templateSuffix
                        tracksInventory
                        isGiftCard
                        totalInventory
                        totalVariants
                        hasOnlyDefaultVariant
                        requiresSellingPlan
                        seo {
                            title
                            description
                        }
                        onlineStoreUrl
                        onlineStorePreviewUrl
                    }
                    userErrors {
                        field
                        message
                    }
                }
            }
            """;
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        return graphQLClient.query(shop, accessToken, mutation, variables)
            .map(this::extractProductFromMutationResponse);
    }
    
    /**
     * Updates an existing product.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param productId the product ID
     * @param input the product input data
     * @return Mono of Product
     */
    public Mono<Product> updateProduct(String shop, String accessToken, String productId, ProductInput input) {
        String mutation = """
            mutation productUpdate($input: ProductInput!) {
                productUpdate(input: $input) {
                    product {
                        id
                        title
                        handle
                        description
                        descriptionHtml
                        createdAt
                        updatedAt
                        publishedAt
                        productType
                        vendor
                        tags
                        status
                        templateSuffix
                        tracksInventory
                        isGiftCard
                        totalInventory
                        totalVariants
                        hasOnlyDefaultVariant
                        requiresSellingPlan
                        seo {
                            title
                            description
                        }
                        onlineStoreUrl
                        onlineStorePreviewUrl
                    }
                    userErrors {
                        field
                        message
                    }
                }
            }
            """;
        
        // Add the ID to the input
        Map<String, Object> inputMap = objectMapper.convertValue(input, Map.class);
        inputMap.put("id", productId);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", inputMap);
        
        return graphQLClient.query(shop, accessToken, mutation, variables)
            .map(this::extractProductFromMutationResponse);
    }
    
    /**
     * Deletes a product.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param productId the product ID
     * @return Mono of Boolean indicating success
     */
    public Mono<Boolean> deleteProduct(String shop, String accessToken, String productId) {
        String mutation = """
            mutation productDelete($input: ProductDeleteInput!) {
                productDelete(input: $input) {
                    deletedProductId
                    userErrors {
                        field
                        message
                    }
                }
            }
            """;
        
        Map<String, Object> input = new HashMap<>();
        input.put("id", productId);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        return graphQLClient.query(shop, accessToken, mutation, variables)
            .map(response -> {
                JsonNode data = response.getData();
                if (data != null && data.has("productDelete")) {
                    JsonNode productDelete = data.get("productDelete");
                    return productDelete.has("deletedProductId") && 
                           !productDelete.get("deletedProductId").isNull();
                }
                return false;
            });
    }
    
    private Product extractProductFromResponse(GraphQLResponse response) {
        try {
            JsonNode data = response.getData();
            if (data != null && data.has("product")) {
                JsonNode productNode = data.get("product");
                if (!productNode.isNull()) {
                    return objectMapper.treeToValue(productNode, Product.class);
                }
            }
            return null;
        } catch (Exception e) {
            log.error("Error extracting product from response", e);
            throw new ShopifyApiException("Failed to parse product response", e);
        }
    }
    
    private ProductConnection extractProductConnectionFromResponse(GraphQLResponse response) {
        try {
            JsonNode data = response.getData();
            if (data != null && data.has("products")) {
                JsonNode productsNode = data.get("products");
                return objectMapper.treeToValue(productsNode, ProductConnection.class);
            }
            return new ProductConnection();
        } catch (Exception e) {
            log.error("Error extracting product connection from response", e);
            throw new ShopifyApiException("Failed to parse products response", e);
        }
    }
    
    private Product extractProductFromMutationResponse(GraphQLResponse response) {
        try {
            JsonNode data = response.getData();
            if (data != null) {
                // Check for productCreate or productUpdate
                JsonNode mutationNode = data.has("productCreate") ? 
                    data.get("productCreate") : data.get("productUpdate");
                
                if (mutationNode != null) {
                    // Check for user errors first
                    if (mutationNode.has("userErrors")) {
                        JsonNode userErrors = mutationNode.get("userErrors");
                        if (userErrors.isArray() && userErrors.size() > 0) {
                            StringBuilder errorMessage = new StringBuilder("Product mutation failed: ");
                            for (JsonNode error : userErrors) {
                                String field = error.has("field") ? error.get("field").asText() : "unknown";
                                String message = error.has("message") ? error.get("message").asText() : "unknown error";
                                errorMessage.append(String.format("[%s: %s] ", field, message));
                            }
                            throw new ShopifyApiException(errorMessage.toString());
                        }
                    }
                    
                    // Extract the product
                    if (mutationNode.has("product")) {
                        JsonNode productNode = mutationNode.get("product");
                        if (!productNode.isNull()) {
                            return objectMapper.treeToValue(productNode, Product.class);
                        }
                    }
                }
            }
            return null;
        } catch (ShopifyApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error extracting product from mutation response", e);
            throw new ShopifyApiException("Failed to parse product mutation response", e);
        }
    }
}