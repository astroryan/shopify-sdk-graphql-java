package com.shopify.sdk.service.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.client.ShopifyRestClient;
import com.shopify.sdk.client.graphql.GraphQLResponse;
import com.shopify.sdk.model.order.Order;
import com.shopify.sdk.model.order.OrderCancelReason;
import com.shopify.sdk.model.order.OrderConnection;
import com.shopify.sdk.model.order.OrderFinancialStatus;
import com.shopify.sdk.model.order.OrderFulfillmentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    
    @Mock
    private ShopifyGraphQLClient graphQLClient;
    
    private ObjectMapper objectMapper;
    private OrderService orderService;
    
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        orderService = new OrderService(graphQLClient, objectMapper);
    }
    
    @Test
    @DisplayName("Should fetch orders successfully")
    void testGetOrders() {
        // Given
        String shop = "test-shop.myshopify.com";
        String accessToken = "test-token";
        
        GraphQLResponse mockResponse = createMockOrdersResponse();
        
        when(graphQLClient.query(eq(shop), eq(accessToken), anyString(), anyMap()))
            .thenReturn(Mono.just(mockResponse));
        
        // When
        Mono<OrderConnection> result = orderService.getOrders(shop, accessToken, 10, null, null);
        
        // Then
        StepVerifier.create(result)
            .assertNext(orders -> {
                assertThat(orders).isNotNull();
                assertThat(orders.getOrderEdges()).hasSize(2);
                assertThat(orders.getOrderEdges().get(0).getNode().getName()).isEqualTo("#1001");
                assertThat(orders.getOrderEdges().get(0).getNode().getTotalPrice()).isNotNull();
                assertThat(orders.getOrderEdges().get(1).getNode().getName()).isEqualTo("#1002");
            })
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should get single order by ID")
    void testGetOrder() {
        // Given
        String shop = "test-shop.myshopify.com";
        String accessToken = "test-token";
        String orderId = "gid://shopify/Order/1001";
        
        GraphQLResponse mockResponse = createMockSingleOrderResponse();
        
        when(graphQLClient.query(eq(shop), eq(accessToken), anyString(), anyMap()))
            .thenReturn(Mono.just(mockResponse));
        
        // When
        Mono<Order> result = orderService.getOrder(shop, accessToken, orderId);
        
        // Then
        StepVerifier.create(result)
            .assertNext(order -> {
                assertThat(order).isNotNull();
                assertThat(order.getId().getValue()).isEqualTo("gid://shopify/Order/1001");
                assertThat(order.getName()).isEqualTo("#1001");
                assertThat(order.getFinancialStatus()).isEqualTo(OrderFinancialStatus.PAID);
                assertThat(order.getFulfillmentStatus()).isEqualTo(OrderFulfillmentStatus.UNFULFILLED);
                assertThat(order.getEmail()).isEqualTo("customer@example.com");
            })
            .verifyComplete();
    }
    
    
    
    @Test
    @DisplayName("Should cancel order successfully")
    void testCancelOrder() {
        // Given
        String shop = "test-shop.myshopify.com";
        String accessToken = "test-token";
        String orderId = "gid://shopify/Order/1001";
        String reason = "Customer requested cancellation";
        
        GraphQLResponse mockResponse = createMockCancelOrderResponse();
        
        when(graphQLClient.query(eq(shop), eq(accessToken), anyString(), anyMap()))
            .thenReturn(Mono.just(mockResponse));
        
        // When
        Mono<Order> result = orderService.cancelOrder(shop, accessToken, orderId, reason, true, true);
        
        // Then
        StepVerifier.create(result)
            .assertNext(order -> {
                assertThat(order).isNotNull();
                assertThat(order.getId().getValue()).isEqualTo("gid://shopify/Order/1001");
                assertThat(order.getCancelledAt()).isNotNull();
                assertThat(order.getCancelReason()).isEqualTo(OrderCancelReason.CUSTOMER);
            })
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should handle GraphQL errors gracefully")
    void testHandleGraphQLErrors() {
        // Given
        String shop = "test-shop.myshopify.com";
        String accessToken = "test-token";
        
        GraphQLResponse errorResponse = new GraphQLResponse();
        GraphQLResponse.GraphQLError error = new GraphQLResponse.GraphQLError();
        error.setMessage("Order not found");
        error.setExtensions(Map.of("code", "ORDER_NOT_FOUND"));
        errorResponse.setErrors(java.util.Collections.singletonList(error));
        
        when(graphQLClient.query(eq(shop), eq(accessToken), anyString(), anyMap()))
            .thenReturn(Mono.just(errorResponse));
        
        // When
        Mono<Order> result = orderService.getOrder(shop, accessToken, "invalid-id");
        
        // Then
        StepVerifier.create(result)
            .expectError(RuntimeException.class)
            .verify();
    }
    
    // Helper methods to create mock responses
    
    private GraphQLResponse createMockOrdersResponse() {
        ObjectNode data = objectMapper.createObjectNode();
        ObjectNode orders = data.putObject("orders");
        
        var edges = orders.putArray("edges");
        
        // Order 1
        var edge1 = edges.addObject();
        var node1 = edge1.putObject("node");
        node1.put("id", "gid://shopify/Order/1001");
        node1.put("name", "#1001");
        node1.put("email", "customer1@example.com");
        
        node1.put("totalPrice", "100.00");
        
        // Order 2
        var edge2 = edges.addObject();
        var node2 = edge2.putObject("node");
        node2.put("id", "gid://shopify/Order/1002");
        node2.put("name", "#1002");
        node2.put("email", "customer2@example.com");
        
        node2.put("totalPrice", "200.00");
        
        // Page info
        var pageInfo = orders.putObject("pageInfo");
        pageInfo.put("hasNextPage", false);
        pageInfo.put("hasPreviousPage", false);
        
        GraphQLResponse response = new GraphQLResponse();
        response.setData(data);
        return response;
    }
    
    private GraphQLResponse createMockSingleOrderResponse() {
        ObjectNode data = objectMapper.createObjectNode();
        ObjectNode order = data.putObject("order");
        order.put("id", "gid://shopify/Order/1001");
        order.put("name", "#1001");
        order.put("email", "customer@example.com");
        order.put("financialStatus", "PAID");
        order.put("fulfillmentStatus", "UNFULFILLED");
        order.put("createdAt", "2024-01-01T00:00:00Z");
        
        order.put("totalPrice", "100.00");
        
        GraphQLResponse response = new GraphQLResponse();
        response.setData(data);
        return response;
    }
    
    
    
    private GraphQLResponse createMockCancelOrderResponse() {
        ObjectNode data = objectMapper.createObjectNode();
        ObjectNode orderCancel = data.putObject("orderCancel");
        
        ObjectNode order = orderCancel.putObject("order");
        order.put("id", "gid://shopify/Order/1001");
        order.put("cancelledAt", "2024-01-15T00:00:00Z");
        order.put("cancelReason", "CUSTOMER");
        
        orderCancel.putArray("userErrors");
        
        GraphQLResponse response = new GraphQLResponse();
        response.setData(data);
        return response;
    }
}