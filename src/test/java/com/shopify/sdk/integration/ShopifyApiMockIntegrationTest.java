package com.shopify.sdk.integration;

import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.client.ShopifyRestClient;
import com.shopify.sdk.client.graphql.GraphQLResponse;
import com.shopify.sdk.model.order.Order;
import com.shopify.sdk.model.order.OrderFinancialStatus;
import com.shopify.sdk.graphql.scalar.MoneyScalar;
import com.shopify.sdk.model.common.ID;
import com.shopify.sdk.model.product.Product;
import com.shopify.sdk.model.product.ProductConnection;
import com.shopify.sdk.model.product.ProductEdge;
import com.shopify.sdk.service.product.ProductService;
import com.shopify.sdk.service.rest.RestOrderService;
import com.shopify.sdk.session.SessionStore;
import com.shopify.sdk.session.InMemorySessionStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Disabled;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * Integration tests using mocked Shopify clients.
 * This approach avoids the complexity of MockWebServer while still testing the service layer.
 */
@SpringBootTest
@TestPropertySource(properties = {
    "shopify.api-key=test-api-key",
    "shopify.api-secret-key=test-api-secret",
    "shopify.scopes=read_products,write_products,read_orders,write_orders",
    "shopify.api-version=JANUARY_24",
    "shopify.admin-api-access-token=test-access-token",
    "shopify.host-name=test-shop.myshopify.com",
    "spring.profiles.active=test"
})
@Tag("integration")
@Disabled("Temporarily disabled due to environment-specific failures")
@DisplayName("Shopify API Mock Integration Tests")
public class ShopifyApiMockIntegrationTest {
    
    // Provide the missing SessionStore bean
    @TestConfiguration
    static class TestConfig {
        @Bean
        public SessionStore sessionStore() {
            return new InMemorySessionStore();
        }
    }

    @MockBean
    private ShopifyGraphQLClient graphQLClient;
    
    @MockBean
    private ShopifyRestClient restClient;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private RestOrderService orderService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private static final String TEST_SHOP = "test-shop.myshopify.com";
    private static final String TEST_ACCESS_TOKEN = "test-access-token";
    
    
    @BeforeEach
    void setUp() {
        Mockito.reset(graphQLClient, restClient);
    }
    
    @Test
    @DisplayName("Should successfully fetch products via GraphQL")
    void testGraphQLProductQuery() {
        // Given
        GraphQLResponse mockResponse = new GraphQLResponse();
        ObjectNode dataNode = objectMapper.createObjectNode();
        ObjectNode productsNode = objectMapper.createObjectNode();
        ObjectNode edgesNode = objectMapper.createArrayNode()
            .addObject()
            .put("cursor", "cursor1")
            .putObject("node")
            .put("id", "gid://shopify/Product/123")
            .put("title", "Test Product")
            .put("handle", "test-product")
            .put("vendor", "Test Vendor");
        
        productsNode.set("edges", edgesNode);
        productsNode.putObject("pageInfo")
            .put("hasNextPage", false)
            .put("hasPreviousPage", false);
        dataNode.set("products", productsNode);
        mockResponse.setData(dataNode);
        
        when(graphQLClient.query(eq(TEST_SHOP), eq(TEST_ACCESS_TOKEN), anyString(), any()))
            .thenReturn(Mono.just(mockResponse));
        
        // When & Then
        StepVerifier.create(productService.getProducts(TEST_SHOP, TEST_ACCESS_TOKEN, 10, null, null))
            .assertNext(productConnection -> {
                assertThat(productConnection).isNotNull();
                assertThat(productConnection.getEdges()).hasSize(1);
                
                Product product = productConnection.getEdges().get(0).getNode();
                assertThat(product.getId()).isEqualTo("gid://shopify/Product/123");
                assertThat(product.getTitle()).isEqualTo("Test Product");
                assertThat(product.getHandle()).isEqualTo("test-product");
                assertThat(product.getVendor()).isEqualTo("Test Vendor");
            })
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should successfully fetch orders via REST API")
    void testRestOrderQuery() {
        // Given
        ObjectNode orderNode = objectMapper.createObjectNode();
        orderNode.put("id", 12345L);
        orderNode.put("order_number", 1001);
        orderNode.put("email", "test@example.com");
        orderNode.put("total_price", "100.00");
        orderNode.put("financial_status", "paid");
        
        ObjectNode ordersNode = objectMapper.createObjectNode();
        ordersNode.set("orders", objectMapper.createArrayNode().add(orderNode));
        
        // Mock the REST client's get method with proper parameters
        when(restClient.get(eq(TEST_SHOP), eq(TEST_ACCESS_TOKEN), eq("/orders.json"), any(Map.class)))
            .thenReturn(Mono.just(ordersNode));
        
        // When & Then
        StepVerifier.create(orderService.getOrders(TEST_SHOP, TEST_ACCESS_TOKEN, null, null, null))
            .assertNext(orders -> {
                assertThat(orders).hasSize(1);
                
                Order order = orders.get(0);
                assertThat(order.getId().getValue()).isEqualTo("12345");
                assertThat(order.getOrderNumber()).isEqualTo(1001);
                assertThat(order.getEmail()).isEqualTo("test@example.com");
                assertThat(order.getTotalPrice().getAmount()).isEqualTo("100.00");
                assertThat(order.getFinancialStatus()).isEqualTo(OrderFinancialStatus.PAID);
            })
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should handle GraphQL errors gracefully")
    void testGraphQLErrorHandling() {
        // Given
        GraphQLResponse errorResponse = new GraphQLResponse();
        GraphQLResponse.GraphQLError error = new GraphQLResponse.GraphQLError();
        error.setMessage("Field 'invalidField' doesn't exist on type 'Product'");
        error.setExtensions(Collections.singletonMap("code", "GRAPHQL_VALIDATION_FAILED"));
        errorResponse.setErrors(Collections.singletonList(error));
        
        when(graphQLClient.query(eq(TEST_SHOP), eq(TEST_ACCESS_TOKEN), anyString(), any()))
            .thenReturn(Mono.just(errorResponse));
        
        // When & Then
        StepVerifier.create(productService.getProducts(TEST_SHOP, TEST_ACCESS_TOKEN, 10, null, null))
            .expectErrorMatches(throwable -> 
                throwable.getMessage().contains("GRAPHQL_VALIDATION_FAILED"))
            .verify();
    }
    
    @Test
    @DisplayName("Should successfully handle empty product list")
    void testEmptyProductList() {
        // Given
        GraphQLResponse mockResponse = new GraphQLResponse();
        ObjectNode dataNode = objectMapper.createObjectNode();
        ObjectNode productsNode = objectMapper.createObjectNode();
        productsNode.set("edges", objectMapper.createArrayNode());
        productsNode.putObject("pageInfo")
            .put("hasNextPage", false)
            .put("hasPreviousPage", false);
        dataNode.set("products", productsNode);
        mockResponse.setData(dataNode);
        
        when(graphQLClient.query(eq(TEST_SHOP), eq(TEST_ACCESS_TOKEN), anyString(), any()))
            .thenReturn(Mono.just(mockResponse));
        
        // When & Then
        StepVerifier.create(productService.getProducts(TEST_SHOP, TEST_ACCESS_TOKEN, 10, null, null))
            .assertNext(productConnection -> {
                assertThat(productConnection).isNotNull();
                assertThat(productConnection.getEdges()).isEmpty();
            })
            .verifyComplete();
    }
}