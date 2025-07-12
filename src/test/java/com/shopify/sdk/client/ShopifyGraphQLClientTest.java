package com.shopify.sdk.client;

import com.shopify.sdk.client.graphql.GraphQLClient;
import com.shopify.sdk.client.graphql.GraphQLResponse;
import com.shopify.sdk.config.ShopifyAuthContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShopifyGraphQLClientTest {
    
    @Mock
    private GraphQLClient graphQLClient;
    
    @Mock
    private ShopifyAuthContext context;
    
    @Mock
    private GraphQLResponse response;
    
    private ShopifyGraphQLClient shopifyGraphQLClient;
    
    @BeforeEach
    void setUp() {
        shopifyGraphQLClient = new ShopifyGraphQLClient(graphQLClient, context);
    }
    
    @Test
    void shouldExecuteSimpleQuery() {
        // Given
        String shop = "test-shop.myshopify.com";
        String accessToken = "test-token";
        String query = "{ shop { name } }";
        
        when(graphQLClient.query(eq(context), eq(shop), eq(accessToken), eq(query)))
            .thenReturn(Mono.just(response));
        
        // When
        Mono<GraphQLResponse> result = shopifyGraphQLClient.query(shop, accessToken, query);
        
        // Then
        StepVerifier.create(result)
            .expectNext(response)
            .verifyComplete();
    }
    
    @Test
    void shouldExecuteStorefrontQuery() {
        // Given
        String shop = "test-shop.myshopify.com";
        String storefrontToken = "storefront-token";
        String query = "{ products(first: 10) { edges { node { title } } } }";
        
        when(graphQLClient.executeStorefrontQuery(eq(context), eq(shop), eq(storefrontToken), any()))
            .thenReturn(Mono.just(response));
        
        // When
        Mono<GraphQLResponse> result = shopifyGraphQLClient.storefrontQuery(shop, storefrontToken, query);
        
        // Then
        StepVerifier.create(result)
            .expectNext(response)
            .verifyComplete();
    }
}