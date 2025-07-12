package com.shopify.sdk.service.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.client.ShopifyRestClient;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.order.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST-based service for managing Shopify orders.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RestOrderService {
    
    private final ShopifyRestClient restClient;
    private final ObjectMapper objectMapper;
    
    /**
     * Retrieves a single order by ID using REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param orderId the order ID
     * @return Mono of Order
     */
    public Mono<Order> getOrder(String shop, String accessToken, String orderId) {
        return restClient.getOrder(shop, accessToken, orderId)
            .map(this::extractSingleOrder);
    }
    
    /**
     * Retrieves a list of orders using REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param limit the number of orders to retrieve (max 250)
     * @param status the order status filter
     * @param pageInfo pagination info for cursor-based pagination
     * @return Mono containing list of orders
     */
    public Mono<List<Order>> getOrders(String shop, String accessToken, Integer limit, String status, String pageInfo) {
        return restClient.getOrders(shop, accessToken, limit, status, pageInfo)
            .map(this::extractOrders);
    }
    
    /**
     * Creates a new order using REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param orderData the order data
     * @return Mono of created Order
     */
    public Mono<Order> createOrder(String shop, String accessToken, Map<String, Object> orderData) {
        Map<String, Object> payload = Map.of("order", orderData);
        
        return restClient.post(shop, accessToken, "/orders.json", payload)
            .map(this::extractSingleOrder);
    }
    
    /**
     * Updates an existing order using REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param orderId the order ID
     * @param orderData the updated order data
     * @return Mono of updated Order
     */
    public Mono<Order> updateOrder(String shop, String accessToken, String orderId, Map<String, Object> orderData) {
        Map<String, Object> payload = Map.of("order", orderData);
        
        return restClient.put(shop, accessToken, String.format("/orders/%s.json", orderId), payload)
            .map(this::extractSingleOrder);
    }
    
    /**
     * Cancels an order using REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param orderId the order ID
     * @param cancelData the cancel parameters (reason, email, refund, etc.)
     * @return Mono of cancelled Order
     */
    public Mono<Order> cancelOrder(String shop, String accessToken, String orderId, Map<String, Object> cancelData) {
        return restClient.post(shop, accessToken, String.format("/orders/%s/cancel.json", orderId), cancelData)
            .map(this::extractSingleOrder);
    }
    
    /**
     * Closes an order using REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param orderId the order ID
     * @return Mono of closed Order
     */
    public Mono<Order> closeOrder(String shop, String accessToken, String orderId) {
        return restClient.post(shop, accessToken, String.format("/orders/%s/close.json", orderId), Map.of())
            .map(this::extractSingleOrder);
    }
    
    /**
     * Opens an order using REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param orderId the order ID
     * @return Mono of opened Order
     */
    public Mono<Order> openOrder(String shop, String accessToken, String orderId) {
        return restClient.post(shop, accessToken, String.format("/orders/%s/open.json", orderId), Map.of())
            .map(this::extractSingleOrder);
    }
    
    /**
     * Gets orders by financial status using REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param financialStatus the financial status (paid, pending, refunded, etc.)
     * @param limit the number of orders to retrieve
     * @return Mono containing list of orders
     */
    public Mono<List<Order>> getOrdersByFinancialStatus(String shop, String accessToken, String financialStatus, Integer limit) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("financial_status", financialStatus);
        queryParams.put("limit", limit != null ? Math.min(limit, 250) : 50);
        
        return restClient.get(shop, accessToken, "/orders.json", queryParams)
            .map(this::extractOrders);
    }
    
    /**
     * Gets orders by fulfillment status using REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param fulfillmentStatus the fulfillment status (shipped, unshipped, partial, etc.)
     * @param limit the number of orders to retrieve
     * @return Mono containing list of orders
     */
    public Mono<List<Order>> getOrdersByFulfillmentStatus(String shop, String accessToken, String fulfillmentStatus, Integer limit) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("fulfillment_status", fulfillmentStatus);
        queryParams.put("limit", limit != null ? Math.min(limit, 250) : 50);
        
        return restClient.get(shop, accessToken, "/orders.json", queryParams)
            .map(this::extractOrders);
    }
    
    /**
     * Gets the order count using REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param status the status filter (any, open, closed, cancelled)
     * @return Mono containing the order count
     */
    public Mono<Integer> getOrderCount(String shop, String accessToken, String status) {
        Map<String, Object> queryParams = new HashMap<>();
        if (status != null && !status.trim().isEmpty()) {
            queryParams.put("status", status);
        }
        
        return restClient.get(shop, accessToken, "/orders/count.json", queryParams)
            .map(response -> {
                if (response.has("count")) {
                    return response.get("count").asInt();
                }
                return 0;
            });
    }
    
    /**
     * Gets fulfillments for an order using REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param orderId the order ID
     * @return Mono containing list of fulfillments
     */
    public Mono<JsonNode> getFulfillments(String shop, String accessToken, String orderId) {
        return restClient.get(shop, accessToken, String.format("/orders/%s/fulfillments.json", orderId));
    }
    
    /**
     * Creates a fulfillment for an order using REST API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param orderId the order ID
     * @param fulfillmentData the fulfillment data
     * @return Mono containing the created fulfillment
     */
    public Mono<JsonNode> createFulfillment(String shop, String accessToken, String orderId, Map<String, Object> fulfillmentData) {
        Map<String, Object> payload = Map.of("fulfillment", fulfillmentData);
        
        return restClient.post(shop, accessToken, String.format("/orders/%s/fulfillments.json", orderId), payload);
    }
    
    private Order extractSingleOrder(JsonNode response) {
        try {
            if (response.has("order")) {
                JsonNode orderNode = response.get("order");
                return objectMapper.treeToValue(orderNode, Order.class);
            }
            throw new ShopifyApiException("Order not found in response");
        } catch (Exception e) {
            log.error("Error extracting order from response", e);
            throw new ShopifyApiException("Failed to parse order response", e);
        }
    }
    
    private List<Order> extractOrders(JsonNode response) {
        try {
            if (response.has("orders")) {
                JsonNode ordersNode = response.get("orders");
                if (ordersNode.isArray()) {
                    return objectMapper.convertValue(ordersNode, 
                        objectMapper.getTypeFactory().constructCollectionType(List.class, Order.class));
                }
            }
            return List.of();
        } catch (Exception e) {
            log.error("Error extracting orders from response", e);
            throw new ShopifyApiException("Failed to parse orders response", e);
        }
    }
}