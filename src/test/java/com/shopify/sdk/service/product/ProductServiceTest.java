package com.shopify.sdk.service.product;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.client.graphql.GraphQLResponse;
import com.shopify.sdk.model.product.Product;
import com.shopify.sdk.model.product.ProductConnection;
import com.shopify.sdk.service.product.ProductInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    
    @Mock
    private ShopifyGraphQLClient graphQLClient;
    
    private ObjectMapper objectMapper;
    private ProductService productService;
    
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        productService = new ProductService(graphQLClient, objectMapper);
    }
    
    @Test
    @DisplayName("Should fetch products successfully")
    void testGetProducts() {
        // Given
        String shop = "test-shop.myshopify.com";
        String accessToken = "test-token";
        
        GraphQLResponse mockResponse = createMockProductsResponse();
        
        when(graphQLClient.query(eq(shop), eq(accessToken), anyString(), anyMap()))
            .thenReturn(Mono.just(mockResponse));
        
        // When
        Mono<ProductConnection> result = productService.getProducts(shop, accessToken, 10, null, null);
        
        // Then
        StepVerifier.create(result)
            .assertNext(products -> {
                assertThat(products).isNotNull();
                assertThat(products.getEdges()).hasSize(2);
                assertThat(products.getEdges().get(0).getNode().getTitle()).isEqualTo("Test Product 1");
                assertThat(products.getEdges().get(1).getNode().getTitle()).isEqualTo("Test Product 2");
            })
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should get single product by ID")
    void testGetProduct() {
        // Given
        String shop = "test-shop.myshopify.com";
        String accessToken = "test-token";
        String productId = "gid://shopify/Product/123";
        
        GraphQLResponse mockResponse = createMockSingleProductResponse();
        
        when(graphQLClient.query(eq(shop), eq(accessToken), anyString(), anyMap()))
            .thenReturn(Mono.just(mockResponse));
        
        // When
        Mono<Product> result = productService.getProduct(shop, accessToken, productId);
        
        // Then
        StepVerifier.create(result)
            .assertNext(product -> {
                assertThat(product).isNotNull();
                assertThat(product.getId().getValue()).isEqualTo("gid://shopify/Product/123");
                assertThat(product.getTitle()).isEqualTo("Test Product");
                assertThat(product.getHandle()).isEqualTo("test-product");
            })
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should create product successfully")
    void testCreateProduct() {
        // Given
        String shop = "test-shop.myshopify.com";
        String accessToken = "test-token";
        ProductInput input = ProductInput.builder()
            .title("New Product")
            .productType("Test Type")
            .vendor("Test Vendor")
            .build();
        
        GraphQLResponse mockResponse = createMockCreateProductResponse();
        
        when(graphQLClient.query(eq(shop), eq(accessToken), anyString(), anyMap()))
            .thenReturn(Mono.just(mockResponse));
        
        // When
        Mono<Product> result = productService.createProduct(shop, accessToken, input);
        
        // Then
        StepVerifier.create(result)
            .assertNext(product -> {
                assertThat(product).isNotNull();
                assertThat(product.getTitle()).isEqualTo("New Product");
                assertThat(product.getProductType()).isEqualTo("Test Type");
                assertThat(product.getVendor()).isEqualTo("Test Vendor");
            })
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should update product successfully")
    void testUpdateProduct() {
        // Given
        String shop = "test-shop.myshopify.com";
        String accessToken = "test-token";
        String productId = "gid://shopify/Product/123";
        ProductInput input = ProductInput.builder()
            .title("Updated Product")
            .build();
        
        GraphQLResponse mockResponse = createMockUpdateProductResponse();
        
        when(graphQLClient.query(eq(shop), eq(accessToken), anyString(), anyMap()))
            .thenReturn(Mono.just(mockResponse));
        
        // When
        Mono<Product> result = productService.updateProduct(shop, accessToken, productId, input);
        
        // Then
        StepVerifier.create(result)
            .assertNext(product -> {
                assertThat(product).isNotNull();
                assertThat(product.getId().getValue()).isEqualTo("gid://shopify/Product/123");
                assertThat(product.getTitle()).isEqualTo("Updated Product");
            })
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should delete product successfully")
    void testDeleteProduct() {
        // Given
        String shop = "test-shop.myshopify.com";
        String accessToken = "test-token";
        String productId = "gid://shopify/Product/123";
        
        GraphQLResponse mockResponse = createMockDeleteProductResponse();
        
        when(graphQLClient.query(eq(shop), eq(accessToken), anyString(), anyMap()))
            .thenReturn(Mono.just(mockResponse));
        
        // When
        Mono<Boolean> result = productService.deleteProduct(shop, accessToken, productId);
        
        // Then
        StepVerifier.create(result)
            .assertNext(success -> {
                assertThat(success).isTrue();
            })
            .verifyComplete();
    }
    
    
    @Test
    @DisplayName("Should handle GraphQL errors gracefully")
    void testHandleGraphQLErrors() {
        // Given
        String shop = "test-shop.myshopify.com";
        String accessToken = "test-token";
        
        GraphQLResponse errorResponse = new GraphQLResponse();
        GraphQLResponse.GraphQLError error = new GraphQLResponse.GraphQLError();
        error.setMessage("Product not found");
        error.setExtensions(Map.of("code", "PRODUCT_NOT_FOUND"));
        errorResponse.setErrors(java.util.Collections.singletonList(error));
        
        when(graphQLClient.query(eq(shop), eq(accessToken), anyString(), anyMap()))
            .thenReturn(Mono.just(errorResponse));
        
        // When
        Mono<Product> result = productService.getProduct(shop, accessToken, "invalid-id");
        
        // Then
        StepVerifier.create(result)
            .expectError(RuntimeException.class)
            .verify();
    }
    
    // Helper methods to create mock responses
    
    private GraphQLResponse createMockProductsResponse() {
        ObjectNode data = objectMapper.createObjectNode();
        ObjectNode products = data.putObject("products");
        
        // Create edges array
        var edges = products.putArray("edges");
        
        // Product 1
        var edge1 = edges.addObject();
        var node1 = edge1.putObject("node");
        node1.put("id", "gid://shopify/Product/1");
        node1.put("title", "Test Product 1");
        node1.put("handle", "test-product-1");
        
        // Product 2
        var edge2 = edges.addObject();
        var node2 = edge2.putObject("node");
        node2.put("id", "gid://shopify/Product/2");
        node2.put("title", "Test Product 2");
        node2.put("handle", "test-product-2");
        
        // Page info
        var pageInfo = products.putObject("pageInfo");
        pageInfo.put("hasNextPage", false);
        pageInfo.put("hasPreviousPage", false);
        
        GraphQLResponse response = new GraphQLResponse();
        response.setData(data);
        return response;
    }
    
    private GraphQLResponse createMockSingleProductResponse() {
        ObjectNode data = objectMapper.createObjectNode();
        ObjectNode product = data.putObject("product");
        product.put("id", "gid://shopify/Product/123");
        product.put("title", "Test Product");
        product.put("handle", "test-product");
        product.put("productType", "Test Type");
        product.put("vendor", "Test Vendor");
        
        GraphQLResponse response = new GraphQLResponse();
        response.setData(data);
        return response;
    }
    
    private GraphQLResponse createMockCreateProductResponse() {
        ObjectNode data = objectMapper.createObjectNode();
        ObjectNode productCreate = data.putObject("productCreate");
        
        ObjectNode product = productCreate.putObject("product");
        product.put("id", "gid://shopify/Product/456");
        product.put("title", "New Product");
        product.put("productType", "Test Type");
        product.put("vendor", "Test Vendor");
        
        productCreate.putArray("userErrors");
        
        GraphQLResponse response = new GraphQLResponse();
        response.setData(data);
        return response;
    }
    
    private GraphQLResponse createMockUpdateProductResponse() {
        ObjectNode data = objectMapper.createObjectNode();
        ObjectNode productUpdate = data.putObject("productUpdate");
        
        ObjectNode product = productUpdate.putObject("product");
        product.put("id", "gid://shopify/Product/123");
        product.put("title", "Updated Product");
        
        productUpdate.putArray("userErrors");
        
        GraphQLResponse response = new GraphQLResponse();
        response.setData(data);
        return response;
    }
    
    private GraphQLResponse createMockDeleteProductResponse() {
        ObjectNode data = objectMapper.createObjectNode();
        ObjectNode productDelete = data.putObject("productDelete");
        productDelete.put("deletedProductId", "gid://shopify/Product/123");
        productDelete.putArray("userErrors");
        
        GraphQLResponse response = new GraphQLResponse();
        response.setData(data);
        return response;
    }
}