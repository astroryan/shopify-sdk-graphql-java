package com.shopify.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.shopify.sdk.config.ShopifyAuthContext;
import com.shopify.sdk.config.ShopifyConfig;
import com.shopify.sdk.exception.ShopifyApiException;
import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShopifyGraphQLClientTest {
    
    @Mock
    private ShopifyConfig shopifyConfig;
    
    @Mock
    private OkHttpClient okHttpClient;
    
    @Mock
    private Call call;
    
    private ShopifyGraphQLClient graphQLClient;
    
    @BeforeEach
    void setUp() {
        when(shopifyConfig.getApiVersion()).thenReturn("2024-01");
        when(shopifyConfig.getRequestTimeout()).thenReturn(30000);
        when(shopifyConfig.getMaxRetries()).thenReturn(3);
        when(shopifyConfig.getRetryDelay()).thenReturn(1000);
        
        // Use reflection to inject mocked OkHttpClient
        graphQLClient = new ShopifyGraphQLClient(shopifyConfig);
        try {
            var field = ShopifyGraphQLClient.class.getDeclaredField("httpClient");
            field.setAccessible(true);
            field.set(graphQLClient, okHttpClient);
        } catch (Exception e) {
            fail("Failed to inject mock OkHttpClient");
        }
    }
    
    @Test
    void testExecuteQuerySuccess() throws IOException {
        // Arrange
        String shopDomain = "test-shop.myshopify.com";
        String accessToken = "test-token";
        ShopifyAuthContext authContext = new ShopifyAuthContext(shopDomain, accessToken);
        ShopifyGraphQLClient.setAuthContext(authContext);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query("{ shop { name } }")
                .variables(new HashMap<>())
                .build();
        
        String responseJson = """
                {
                    "data": {
                        "shop": {
                            "name": "Test Shop"
                        }
                    }
                }
                """;
        
        Response response = new Response.Builder()
                .request(new Request.Builder().url("https://test.com").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create(responseJson, MediaType.parse("application/json")))
                .build();
        
        when(okHttpClient.newCall(any(Request.class))).thenReturn(call);
        when(call.execute()).thenReturn(response);
        
        // Act
        GraphQLResponse<TestShopResponse> result = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<TestShopResponse>>() {}
        );
        
        // Assert
        assertNotNull(result);
        assertNotNull(result.getData());
        assertEquals("Test Shop", result.getData().shop.name);
        assertFalse(result.hasErrors());
    }
    
    @Test
    void testExecuteQueryWithErrors() throws IOException {
        // Arrange
        String shopDomain = "test-shop.myshopify.com";
        String accessToken = "test-token";
        ShopifyAuthContext authContext = new ShopifyAuthContext(shopDomain, accessToken);
        ShopifyGraphQLClient.setAuthContext(authContext);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query("{ shop { invalidField } }")
                .variables(new HashMap<>())
                .build();
        
        String responseJson = """
                {
                    "errors": [
                        {
                            "message": "Field 'invalidField' doesn't exist on type 'Shop'",
                            "extensions": {
                                "code": "FIELD_NOT_FOUND"
                            }
                        }
                    ]
                }
                """;
        
        Response response = new Response.Builder()
                .request(new Request.Builder().url("https://test.com").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create(responseJson, MediaType.parse("application/json")))
                .build();
        
        when(okHttpClient.newCall(any(Request.class))).thenReturn(call);
        when(call.execute()).thenReturn(response);
        
        // Act
        GraphQLResponse<TestShopResponse> result = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<TestShopResponse>>() {}
        );
        
        // Assert
        assertNotNull(result);
        assertTrue(result.hasErrors());
        assertEquals(1, result.getErrors().size());
        assertEquals("Field 'invalidField' doesn't exist on type 'Shop'", result.getErrors().get(0).getMessage());
    }
    
    @Test
    void testExecuteWithRateLimiting() throws IOException {
        // Arrange
        String shopDomain = "test-shop.myshopify.com";
        String accessToken = "test-token";
        ShopifyAuthContext authContext = new ShopifyAuthContext(shopDomain, accessToken);
        ShopifyGraphQLClient.setAuthContext(authContext);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query("{ shop { name } }")
                .variables(new HashMap<>())
                .build();
        
        Response rateLimitResponse = new Response.Builder()
                .request(new Request.Builder().url("https://test.com").build())
                .protocol(Protocol.HTTP_1_1)
                .code(429)
                .message("Too Many Requests")
                .body(ResponseBody.create("Rate limited", MediaType.parse("text/plain")))
                .build();
        
        String successResponseJson = """
                {
                    "data": {
                        "shop": {
                            "name": "Test Shop"
                        }
                    }
                }
                """;
        
        Response successResponse = new Response.Builder()
                .request(new Request.Builder().url("https://test.com").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create(successResponseJson, MediaType.parse("application/json")))
                .build();
        
        when(okHttpClient.newCall(any(Request.class))).thenReturn(call);
        when(call.execute())
                .thenReturn(rateLimitResponse)
                .thenReturn(successResponse);
        
        // Act
        GraphQLResponse<TestShopResponse> result = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<TestShopResponse>>() {}
        );
        
        // Assert
        assertNotNull(result);
        assertEquals("Test Shop", result.getData().shop.name);
        verify(call, times(2)).execute(); // Should retry once after rate limit
    }
    
    @Test
    void testExecuteWithoutAuthContext() {
        // Arrange
        ShopifyGraphQLClient.clearAuthContext();
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query("{ shop { name } }")
                .variables(new HashMap<>())
                .build();
        
        // Act & Assert
        assertThrows(ShopifyApiException.class, () -> {
            graphQLClient.execute(request, new TypeReference<GraphQLResponse<TestShopResponse>>() {});
        });
    }
    
    @Test
    void testExecuteWithVariables() throws IOException {
        // Arrange
        String shopDomain = "test-shop.myshopify.com";
        String accessToken = "test-token";
        ShopifyAuthContext authContext = new ShopifyAuthContext(shopDomain, accessToken);
        ShopifyGraphQLClient.setAuthContext(authContext);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", "gid://shopify/Product/123");
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query("query getProduct($id: ID!) { product(id: $id) { title } }")
                .variables(variables)
                .build();
        
        String responseJson = """
                {
                    "data": {
                        "product": {
                            "title": "Test Product"
                        }
                    }
                }
                """;
        
        Response response = new Response.Builder()
                .request(new Request.Builder().url("https://test.com").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create(responseJson, MediaType.parse("application/json")))
                .build();
        
        when(okHttpClient.newCall(any(Request.class))).thenReturn(call);
        when(call.execute()).thenReturn(response);
        
        // Act
        GraphQLResponse<TestProductResponse> result = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<TestProductResponse>>() {}
        );
        
        // Assert
        assertNotNull(result);
        assertEquals("Test Product", result.getData().product.title);
    }
    
    // Test response classes
    static class TestShopResponse {
        public TestShop shop;
    }
    
    static class TestShop {
        public String name;
    }
    
    static class TestProductResponse {
        public TestProduct product;
    }
    
    static class TestProduct {
        public String title;
    }
}