package com.shopify.sdk.config;

import com.shopify.sdk.auth.JwtTokenValidator;
import com.shopify.sdk.auth.ShopifyOAuth;
import com.shopify.sdk.client.HttpClientConfig;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.client.ShopifyRestClient;
import com.shopify.sdk.client.graphql.GraphQLClient;
import com.shopify.sdk.client.HttpClientService;
import com.shopify.sdk.client.rest.RestClient;
import com.shopify.sdk.client.rest.RestClientImpl;
import com.shopify.sdk.model.common.ApiVersion;
import com.shopify.sdk.session.InMemorySessionStore;
import com.shopify.sdk.session.SessionManager;
import com.shopify.sdk.session.SessionStore;
import com.shopify.sdk.webhook.WebhookProcessor;
import com.shopify.sdk.webhook.WebhookHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Test configuration for Shopify SDK tests.
 * This configuration overrides certain beans for testing purposes.
 */
@TestConfiguration
public class ShopifyTestConfiguration {
    
    @Bean
    @Primary
    public ShopifyAuthContext testAuthContext() {
        return ShopifyAuthContext.builder()
            .apiKey("test-api-key")
            .apiSecretKey("test-api-secret")
            .scopes(List.of("read_products", "write_products", "read_orders", "write_orders"))
            .hostName("test-app.example.com")
            .apiVersion(ApiVersion.JANUARY_24)
            .isEmbeddedApp(true)
            .build();
    }
    
    @Bean
    @Primary
    public SessionStore testSessionStore() {
        // Use in-memory storage for tests
        return new InMemorySessionStore();
    }
    
    @Bean
    public ObjectMapper testObjectMapper() {
        return new ObjectMapper();
    }
    
    @Bean
    public HttpClientConfig testHttpClientConfig() {
        return new HttpClientConfig();
    }
    
    @Bean
    public GraphQLClient testGraphQLClient(HttpClientConfig httpClientConfig, ObjectMapper objectMapper) {
        return new GraphQLClient(httpClientConfig, objectMapper);
    }
    
    @Bean
    public HttpClientService testHttpClientService(HttpClientConfig httpClientConfig) {
        return new HttpClientService(httpClientConfig);
    }
    
    @Bean
    public ShopifyGraphQLClient testShopifyGraphQLClient(GraphQLClient graphQLClient, 
                                                          ShopifyAuthContext context) {
        return new ShopifyGraphQLClient(graphQLClient, context);
    }
    
    @Bean
    public RestClient testRestClient(HttpClientConfig httpClientConfig, ObjectMapper objectMapper) {
        return new RestClientImpl(httpClientConfig, objectMapper);
    }
    
    @Bean
    public ShopifyRestClient testShopifyRestClient(RestClient restClient, 
                                                    ShopifyAuthContext context) {
        return new ShopifyRestClient(restClient, context);
    }
    
    @Bean
    public ShopifyOAuth testShopifyOAuth(ShopifyAuthContext context) {
        return new ShopifyOAuth(context);
    }
    
    @Bean
    public JwtTokenValidator testJwtTokenValidator(ShopifyAuthContext context) {
        return new JwtTokenValidator(context);
    }
    
    @Bean
    public SessionManager testSessionManager(SessionStore sessionStore, JwtTokenValidator jwtTokenValidator) {
        return new SessionManager(sessionStore, jwtTokenValidator);
    }
    
    @Bean
    public WebhookProcessor testWebhookProcessor(ShopifyAuthContext context, 
                                                  ShopifyOAuth shopifyOAuth,
                                                  ObjectMapper objectMapper) {
        List<WebhookHandler> handlers = new ArrayList<>();
        return new WebhookProcessor(context, shopifyOAuth, objectMapper, handlers);
    }
}