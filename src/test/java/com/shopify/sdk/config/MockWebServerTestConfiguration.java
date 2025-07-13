package com.shopify.sdk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.client.HttpClientConfig;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.client.ShopifyRestClient;
import com.shopify.sdk.client.graphql.GraphQLClient;
import com.shopify.sdk.client.rest.RestClient;
import com.shopify.sdk.client.rest.RestClientImpl;
import com.shopify.sdk.model.common.ApiVersion;
import com.shopify.sdk.session.SessionManager;
import com.shopify.sdk.session.SessionStore;
import com.shopify.sdk.auth.JwtTokenValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.mockito.Mockito;

/**
 * Test configuration for MockWebServer-based integration tests.
 * Provides bean overrides for testing with dynamic URLs.
 */
@TestConfiguration
public class MockWebServerTestConfiguration {
    
    @Value("${shopify.host-name:localhost:8080}")
    private String hostName;
    
    @Value("${shopify.api-key:test-api-key}")
    private String apiKey;
    
    @Value("${shopify.api-secret-key:test-api-secret}")
    private String apiSecretKey;
    
    @Bean
    @Primary
    public ShopifyAuthContext testAuthContext() {
        return ShopifyAuthContext.builder()
            .apiKey(apiKey)
            .apiSecretKey(apiSecretKey)
            .scopes(java.util.List.of("read_products", "write_products", "read_orders", "write_orders"))
            .hostName(hostName)
            .apiVersion(ApiVersion.JANUARY_24)
            .isEmbeddedApp(false)
            .adminApiAccessToken("test-access-token")
            .build();
    }
    
    @Bean
    @Primary
    public HttpClientConfig testHttpClientConfig() {
        // HttpClientConfig doesn't have setter methods, it uses internal configuration
        return new HttpClientConfig();
    }
    
    @Bean
    @Primary
    public SessionStore testSessionStore() {
        // Provide a mock session store for tests
        return Mockito.mock(SessionStore.class);
    }
    
    @Bean
    @Primary
    public JwtTokenValidator testJwtTokenValidator() {
        // Provide a mock JWT token validator for tests
        return Mockito.mock(JwtTokenValidator.class);
    }
    
    @Bean
    @Primary
    public SessionManager testSessionManager(SessionStore sessionStore, JwtTokenValidator jwtTokenValidator) {
        // Return a mock or a test implementation
        SessionManager mock = Mockito.mock(SessionManager.class);
        return mock;
    }
}