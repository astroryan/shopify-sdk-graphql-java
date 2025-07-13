package com.shopify.sdk.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.client.HttpClientConfig;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.client.graphql.GraphQLClient;
import com.shopify.sdk.client.graphql.GraphQLRequest;
import com.shopify.sdk.client.graphql.GraphQLResponse;
import com.shopify.sdk.config.ShopifyAuthContext;
import com.shopify.sdk.model.common.ApiVersion;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Simplified integration test for Shopify SDK with MockWebServer.
 */
@Tag("integration-disabled")
@DisplayName("Simplified Shopify Integration Tests")
public class SimplifiedShopifyIntegrationTest {
    
    private static MockWebServer mockWebServer;
    private ShopifyGraphQLClient graphQLClient;
    private ObjectMapper objectMapper;
    
    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }
    
    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }
    
    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();
        
        // Create a test auth context
        ShopifyAuthContext authContext = ShopifyAuthContext.builder()
            .apiKey("test-api-key")
            .apiSecretKey("test-api-secret")
            .scopes(java.util.List.of("read_products", "write_products"))
            .hostName("test-shop.myshopify.com")
            .hostScheme("https")
            .apiVersion(ApiVersion.JANUARY_24)
            .isEmbeddedApp(false)
            .adminApiAccessToken("test-access-token")
            .build();
        
        // Create a mock GraphQL client
        GraphQLClient mockGraphQLClient = mock(GraphQLClient.class);
        
        // Create the Shopify GraphQL client with mocks
        graphQLClient = new ShopifyGraphQLClient(mockGraphQLClient, authContext);
    }
    
    @Test
    @DisplayName("Should successfully execute GraphQL query with mock")
    void testGraphQLQueryWithMock() throws Exception {
        // This test verifies that the Shopify GraphQL client correctly delegates
        // to the underlying GraphQL client, which in real usage would make HTTP calls
        
        // Given
        GraphQLClient mockGraphQLClient = mock(GraphQLClient.class);
        ShopifyAuthContext authContext = ShopifyAuthContext.builder()
            .apiKey("test-api-key")
            .apiSecretKey("test-api-secret")
            .scopes(java.util.List.of("read_products"))
            .hostName("test-shop.myshopify.com")
            .apiVersion(ApiVersion.JANUARY_24)
            .build();
        
        ShopifyGraphQLClient client = new ShopifyGraphQLClient(mockGraphQLClient, authContext);
        
        // Create mock response
        GraphQLResponse mockResponse = new GraphQLResponse();
        mockResponse.setData(objectMapper.readTree("""
            {
                "shop": {
                    "name": "Test Shop",
                    "email": "test@example.com"
                }
            }
            """));
        
        // Setup mock behavior
        when(mockGraphQLClient.query(any(), anyString(), anyString(), anyString(), any()))
            .thenReturn(Mono.just(mockResponse));
        
        // When
        String query = """
            query {
                shop {
                    name
                    email
                }
            }
            """;
        
        // Then
        StepVerifier.create(client.query("test-shop", "test-token", query, null))
            .assertNext(response -> {
                assertThat(response).isNotNull();
                assertThat(response.getData()).isNotNull();
                assertThat(response.getData().get("shop").get("name").asText())
                    .isEqualTo("Test Shop");
            })
            .verifyComplete();
        
        // Verify the mock was called correctly
        verify(mockGraphQLClient).query(eq(authContext), eq("test-shop"), eq("test-token"), eq(query), isNull());
    }
}