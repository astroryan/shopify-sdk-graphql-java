package com.shopify.sdk.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.config.ShopifyAuthContext;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.client.ShopifyRestClient;
import com.shopify.sdk.model.common.ApiVersion;
import com.shopify.sdk.model.product.Product;
import com.shopify.sdk.model.product.ProductConnection;
import com.shopify.sdk.model.order.Order;
import com.shopify.sdk.service.product.ProductService;
import com.shopify.sdk.service.rest.RestOrderService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;

/**
 * Integration tests for Shopify SDK using MockWebServer.
 * Tests real HTTP interactions with mocked responses.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Import(com.shopify.sdk.config.MockWebServerTestConfiguration.class)
@TestPropertySource(properties = {
    "shopify.api-key=test-api-key",
    "shopify.api-secret-key=test-api-secret",
    "shopify.scopes=read_products,write_products,read_orders,write_orders",
    "shopify.api-version=JANUARY_24",
    "shopify.admin-api-access-token=test-access-token",
    "spring.profiles.active=test",
    "shopify.use-ssl=false"
})
@Tag("integration")
@DisplayName("Shopify API Integration Tests")
@Timeout(value = 30, unit = TimeUnit.SECONDS)  // Class-level timeout
public class ShopifyApiIntegrationTest {

    private static MockWebServer mockWebServer;
    private static String TEST_SHOP;
    private static final String TEST_ACCESS_TOKEN = "test-access-token";
    
    @Autowired
    private ShopifyGraphQLClient graphQLClient;
    
    @Autowired
    private ShopifyRestClient restClient;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private RestOrderService orderService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        TEST_SHOP = mockWebServer.getHostName() + ":" + mockWebServer.getPort();
    }
    
    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }
    
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        String mockServerUrl = "http://" + mockWebServer.getHostName() + ":" + mockWebServer.getPort();
        registry.add("shopify.host-name", () -> 
            mockWebServer.getHostName() + ":" + mockWebServer.getPort());
        registry.add("shopify.base-url", () -> mockServerUrl);
        registry.add("shopify.use-ssl", () -> "false");
    }
    
    @BeforeEach
    void init() throws InterruptedException {
        // Ensure MockWebServer is ready and clear any previous state
        assertThat(mockWebServer).isNotNull();
        
        // Clear any queued responses between tests
        while (mockWebServer.getRequestCount() > 0) {
            try {
                mockWebServer.takeRequest(0, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    @AfterEach
    void cleanup() {
        // Consume any remaining requests to prevent hanging
        try {
            while (mockWebServer.getRequestCount() > 0) {
                mockWebServer.takeRequest(0, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    @Test
    @DisplayName("Should successfully fetch products via GraphQL")
    void testGraphQLProductQuery() throws Exception {
        // Given
        String mockResponse = """
            {
                "data": {
                    "products": {
                        "edges": [
                            {
                                "node": {
                                    "id": "gid://shopify/Product/123",
                                    "title": "Test Product",
                                    "handle": "test-product",
                                    "descriptionHtml": "<p>Test description</p>",
                                    "vendor": "Test Vendor",
                                    "productType": "Test Type",
                                    "tags": ["tag1", "tag2"],
                                    "status": "ACTIVE",
                                    "createdAt": "2024-01-01T00:00:00Z",
                                    "updatedAt": "2024-01-01T00:00:00Z"
                                }
                            }
                        ],
                        "pageInfo": {
                            "hasNextPage": false,
                            "hasPreviousPage": false
                        }
                    }
                }
            }
            """;
        
        mockWebServer.enqueue(new MockResponse()
            .setBody(mockResponse)
            .addHeader("Content-Type", "application/json")
            .setResponseCode(200));
        
        // When
        StepVerifier.create(productService.getProducts(TEST_SHOP, TEST_ACCESS_TOKEN, 10, null, null))
            .assertNext(productConnection -> {
                assertThat(productConnection).isNotNull();
                assertThat(productConnection.getEdges()).hasSize(1);
                
                Product product = productConnection.getEdges().get(0).getNode();
                assertThat(product.getId()).isEqualTo("gid://shopify/Product/123");
                assertThat(product.getTitle()).isEqualTo("Test Product");
                assertThat(product.getHandle()).isEqualTo("test-product");
                assertThat(product.getVendor()).isEqualTo("Test Vendor");
                assertThat(product.getTags()).containsExactly("tag1", "tag2");
            })
            .expectComplete()
            .verify(Duration.ofSeconds(5));
        
        // Then verify the request
        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("/admin/api/2024-01/graphql.json");
        assertThat(request.getHeader("X-Shopify-Access-Token")).isEqualTo(TEST_ACCESS_TOKEN);
    }
    
    @Test
    @DisplayName("Should successfully fetch orders via REST API")
    void testRestOrderQuery() throws Exception {
        // Given
        String mockResponse = """
            {
                "orders": [
                    {
                        "id": 12345,
                        "order_number": 1001,
                        "email": "test@example.com",
                        "total_price": "100.00",
                        "currency": "USD",
                        "financial_status": "paid",
                        "fulfillment_status": "fulfilled",
                        "created_at": "2024-01-01T00:00:00Z",
                        "updated_at": "2024-01-01T00:00:00Z",
                        "line_items": [
                            {
                                "id": 1,
                                "product_id": 123,
                                "title": "Test Product",
                                "quantity": 2,
                                "price": "50.00"
                            }
                        ]
                    }
                ]
            }
            """;
        
        mockWebServer.enqueue(new MockResponse()
            .setBody(mockResponse)
            .addHeader("Content-Type", "application/json")
            .setResponseCode(200));
        
        // When
        StepVerifier.create(orderService.getOrders(TEST_SHOP, TEST_ACCESS_TOKEN, null, null, null))
            .assertNext(orders -> {
                assertThat(orders).hasSize(1);
                
                Order order = orders.get(0);
                assertThat(order.getId()).isEqualTo(12345L);
                assertThat(order.getOrderNumber()).isEqualTo(1001);
                assertThat(order.getEmail()).isEqualTo("test@example.com");
                assertThat(order.getTotalPrice()).isEqualTo(new BigDecimal("100.00"));
                assertThat(order.getFinancialStatus()).isEqualTo("paid");
                // Note: line_items in REST API returns a different structure than GraphQL
            })
            .expectComplete()
            .verify(Duration.ofSeconds(5));
        
        // Then verify the request
        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(request.getPath()).isEqualTo("/admin/api/2024-01/orders.json");
        assertThat(request.getHeader("X-Shopify-Access-Token")).isEqualTo(TEST_ACCESS_TOKEN);
    }
    
    @Test
    @DisplayName("Should handle rate limiting correctly")
    void testRateLimiting() throws Exception {
        // Given - First response with rate limit headers
        mockWebServer.enqueue(new MockResponse()
            .setBody("{\"errors\":\"Throttled\"}")
            .addHeader("Content-Type", "application/json")
            .addHeader("Retry-After", "1")  // Reduced retry time
            .setResponseCode(429));
        
        // Second response succeeds
        String successResponse = """
            {
                "data": {
                    "products": {
                        "edges": [],
                        "pageInfo": {
                            "hasNextPage": false,
                            "hasPreviousPage": false
                        }
                    }
                }
            }
            """;
        
        mockWebServer.enqueue(new MockResponse()
            .setBody(successResponse)
            .addHeader("Content-Type", "application/json")
            .setResponseCode(200));
        
        // When - with timeout
        StepVerifier.create(productService.getProducts(TEST_SHOP, TEST_ACCESS_TOKEN, 10, null, null))
            .assertNext(productConnection -> {
                assertThat(productConnection).isNotNull();
                assertThat(productConnection.getEdges()).isEmpty();
            })
            .expectComplete()
            .verify(Duration.ofSeconds(5));
        
        // Then verify retry happened
        assertThat(mockWebServer.getRequestCount()).isGreaterThanOrEqualTo(2);
    }
    
    @Test
    @DisplayName("Should handle API errors gracefully")
    void testApiErrorHandling() throws Exception {
        // Given
        String errorResponse = """
            {
                "errors": [
                    {
                        "message": "Field 'invalidField' doesn't exist on type 'Product'",
                        "extensions": {
                            "code": "GRAPHQL_VALIDATION_FAILED"
                        }
                    }
                ]
            }
            """;
        
        mockWebServer.enqueue(new MockResponse()
            .setBody(errorResponse)
            .addHeader("Content-Type", "application/json")
            .setResponseCode(400));
        
        // When & Then
        StepVerifier.create(productService.getProducts(TEST_SHOP, TEST_ACCESS_TOKEN, 10, null, null))
            .expectErrorMatches(throwable -> 
                throwable.getMessage().contains("GRAPHQL_VALIDATION_FAILED"))
            .verify(Duration.ofSeconds(5));
    }
    
    @Test
    @DisplayName("Should handle network timeouts")
    void testNetworkTimeout() {
        // Given - Delayed response to trigger timeout
        mockWebServer.enqueue(new MockResponse()
            .setBody("{}")
            .setBodyDelay(6, TimeUnit.SECONDS));  // Delay longer than client timeout
        
        // When & Then
        StepVerifier.create(productService.getProducts(TEST_SHOP, TEST_ACCESS_TOKEN, 10, null, null))
            .expectErrorMatches(throwable -> {
                // Check various timeout-related errors
                String message = throwable.getMessage();
                Throwable cause = throwable.getCause();
                return throwable instanceof java.util.concurrent.TimeoutException ||
                       (cause != null && cause instanceof java.util.concurrent.TimeoutException) ||
                       (message != null && (message.contains("timeout") || message.contains("Timeout"))) ||
                       throwable instanceof io.netty.handler.timeout.ReadTimeoutException ||
                       (cause != null && cause instanceof io.netty.handler.timeout.ReadTimeoutException);
            })
            .verify(Duration.ofSeconds(10)); // Give more time for the test to detect timeout
    }
}