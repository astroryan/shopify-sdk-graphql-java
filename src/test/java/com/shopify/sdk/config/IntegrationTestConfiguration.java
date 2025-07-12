package com.shopify.sdk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shopify.sdk.client.HttpClientConfig;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.client.ShopifyRestClient;
import com.shopify.sdk.client.graphql.GraphQLClient;
import com.shopify.sdk.client.rest.RestClient;
import com.shopify.sdk.client.rest.RestClientImpl;
import com.shopify.sdk.model.common.ApiVersion;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Test configuration for integration tests that provides minimal bean configuration
 * without requiring a full Spring Boot application.
 */
@TestConfiguration
public class IntegrationTestConfiguration {
    
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
    
    @Bean
    public ShopifyAuthContext shopifyAuthContext() {
        // Use environment variables if available, otherwise use test defaults
        String storeDomain = System.getenv("SHOPIFY_TEST_STORE_DOMAIN");
        String accessToken = System.getenv("SHOPIFY_TEST_ACCESS_TOKEN");
        
        if (storeDomain == null || storeDomain.isEmpty()) {
            storeDomain = "test-shop.myshopify.com";
        }
        
        return ShopifyAuthContext.builder()
            .apiKey("test-api-key")
            .apiSecretKey("test-api-secret")
            .scopes(java.util.List.of("read_products", "write_products", "read_orders", "write_orders"))
            .hostName(storeDomain)
            .apiVersion(ApiVersion.JANUARY_24)
            .isEmbeddedApp(false)
            .build();
    }
    
    @Bean
    public HttpClientConfig httpClientConfig() {
        return new HttpClientConfig();
    }
    
    @Bean
    public GraphQLClient graphQLClient(HttpClientConfig httpClientConfig, ObjectMapper objectMapper) {
        return new GraphQLClient(httpClientConfig, objectMapper);
    }
    
    @Bean
    public ShopifyGraphQLClient shopifyGraphQLClient(GraphQLClient graphQLClient, ShopifyAuthContext context) {
        return new ShopifyGraphQLClient(graphQLClient, context);
    }
    
    @Bean
    public RestClient restClient(HttpClientConfig httpClientConfig, ObjectMapper objectMapper) {
        return new RestClientImpl(httpClientConfig, objectMapper);
    }
    
    @Bean
    public ShopifyRestClient shopifyRestClient(RestClient restClient, ShopifyAuthContext context) {
        return new ShopifyRestClient(restClient, context);
    }
}