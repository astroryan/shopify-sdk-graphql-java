package com.shopify.sdk.config;

import com.shopify.sdk.ShopifyApi;
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
import com.shopify.sdk.service.product.ProductService;
import com.shopify.sdk.service.order.OrderService;
import com.shopify.sdk.service.rest.RestProductService;
import com.shopify.sdk.service.rest.RestOrderService;
import com.shopify.sdk.service.billing.BillingService;
import com.shopify.sdk.service.bulk.BulkOperationService;
import com.shopify.sdk.session.InMemorySessionStore;
import com.shopify.sdk.session.SessionManager;
import com.shopify.sdk.session.SessionStore;
import com.shopify.sdk.webhook.WebhookProcessor;
import com.shopify.sdk.webhook.WebhookHandler;
import com.shopify.sdk.webhook.DefaultWebhookHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;
import org.mockito.Mockito;

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
    @Primary
    public ObjectMapper testObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
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
    public ShopifyOAuth testShopifyOAuth(ShopifyAuthContext context, HttpClientService httpClientService, ObjectMapper objectMapper) {
        return new ShopifyOAuth(context, httpClientService, objectMapper);
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
    public List<WebhookHandler> testWebhookHandlers() {
        List<WebhookHandler> handlers = new ArrayList<>();
        handlers.add(new DefaultWebhookHandler());
        return handlers;
    }
    
    @Bean
    public WebhookProcessor testWebhookProcessor(ShopifyAuthContext context, 
                                                  ShopifyOAuth shopifyOAuth,
                                                  ObjectMapper objectMapper,
                                                  List<WebhookHandler> webhookHandlers) {
        return new WebhookProcessor(context, shopifyOAuth, objectMapper, webhookHandlers);
    }
    
    
    @Bean
    @Primary
    public ShopifyApi testShopifyApi() {
        // Create a mock ShopifyApi for testing
        ShopifyApi mockApi = Mockito.mock(ShopifyApi.class);
        
        // Mock the getProducts() method
        ProductService mockProductService = Mockito.mock(ProductService.class);
        Mockito.when(mockApi.getProducts()).thenReturn(mockProductService);
        
        // Mock the getRestOrders() method
        RestOrderService mockRestOrderService = Mockito.mock(RestOrderService.class);
        Mockito.when(mockApi.getRestOrders()).thenReturn(mockRestOrderService);
        
        // Mock the getBulkOperations() method
        BulkOperationService mockBulkService = Mockito.mock(BulkOperationService.class);
        Mockito.when(mockApi.getBulkOperations()).thenReturn(mockBulkService);
        
        // Mock the getRestClient() method
        ShopifyRestClient mockRestClient = Mockito.mock(ShopifyRestClient.class);
        Mockito.when(mockApi.getRestClient()).thenReturn(mockRestClient);
        
        // Mock the getOAuth() method
        ShopifyOAuth mockOAuth = Mockito.mock(ShopifyOAuth.class);
        Mockito.when(mockApi.getOAuth()).thenReturn(mockOAuth);
        
        return mockApi;
    }
}