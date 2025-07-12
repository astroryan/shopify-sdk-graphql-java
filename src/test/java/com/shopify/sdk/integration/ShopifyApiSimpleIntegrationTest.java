package com.shopify.sdk.integration;

import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.client.ShopifyRestClient;
import com.shopify.sdk.config.ShopifyAuthContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Simple integration test to verify Spring context loads correctly.
 */
public class ShopifyApiSimpleIntegrationTest extends BaseIntegrationTest {
    
    @Autowired
    private ShopifyAuthContext authContext;
    
    @Autowired
    private ShopifyGraphQLClient graphQLClient;
    
    @Autowired
    private ShopifyRestClient restClient;
    
    @Test
    @DisplayName("Integration test: Spring context loads successfully")
    void testContextLoads() {
        assertThat(authContext).isNotNull();
        assertThat(authContext.getApiKey()).isEqualTo("test-api-key");
        assertThat(authContext.getApiVersion()).isNotNull();
    }
    
    @Test
    @DisplayName("Integration test: GraphQL client is configured")
    void testGraphQLClientConfiguration() {
        assertThat(graphQLClient).isNotNull();
    }
    
    @Test
    @DisplayName("Integration test: REST client is configured")
    void testRestClientConfiguration() {
        assertThat(restClient).isNotNull();
    }
}