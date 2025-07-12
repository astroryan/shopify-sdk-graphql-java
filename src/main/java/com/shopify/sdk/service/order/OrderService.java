package com.shopify.sdk.service.order;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.client.graphql.GraphQLResponse;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.order.Order;
import com.shopify.sdk.model.order.OrderConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * Service for managing Shopify orders.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final ShopifyGraphQLClient graphQLClient;
    private final ObjectMapper objectMapper;
    
    /**
     * Retrieves a single order by ID.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param orderId the order ID
     * @return Mono of Order
     */
    public Mono<Order> getOrder(String shop, String accessToken, String orderId) {
        String query = """
            query getOrder($id: ID!) {
                order(id: $id) {
                    id
                    cancelReason
                    cancelledAt
                    confirmed
                    createdAt
                    currencyCode
                    currentSubtotalPrice
                    currentTotalTax
                    email
                    financialStatus
                    fulfillmentStatus
                    name
                    orderNumber
                    phone
                    processedAt
                    billingAddress {
                        id
                        address1
                        address2
                        city
                        company
                        country
                        countryCode
                        firstName
                        lastName
                        phone
                        province
                        provinceCode
                        zip
                    }
                    shippingAddress {
                        id
                        address1
                        address2
                        city
                        company
                        country
                        countryCode
                        firstName
                        lastName
                        phone
                        province
                        provinceCode
                        zip
                    }
                    updatedAt
                    test
                    lineItems(first: 250) {
                        edges {
                            node {
                                id
                                quantity
                                title
                                variantTitle
                                name
                                sku
                                vendor
                                weight
                                requiresShipping
                                taxable
                                giftCard
                                fulfillmentStatus
                                fulfillableQuantity
                                refundableQuantity
                                originalUnitPrice
                                discountedUnitPrice
                                originalTotalPrice
                                discountedTotalPrice
                                totalDiscount
                                customAttributes {
                                    key
                                    value
                                }
                            }
                            cursor
                        }
                        pageInfo {
                            hasNextPage
                            hasPreviousPage
                            startCursor
                            endCursor
                        }
                    }
                    canMarkAsPaid
                    canNotifyCustomer
                    totalPrice
                    subtotalPrice
                    totalShippingPrice
                    totalTax
                }
            }
            """;
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", orderId);
        
        return graphQLClient.query(shop, accessToken, query, variables)
            .map(this::extractOrderFromResponse);
    }
    
    /**
     * Retrieves a list of orders with pagination.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param first the number of orders to retrieve
     * @param after the cursor for pagination
     * @param query the search query (optional)
     * @return Mono of OrderConnection
     */
    public Mono<OrderConnection> getOrders(String shop, String accessToken, Integer first, String after, String query) {
        String graphQLQuery = """
            query getOrders($first: Int!, $after: String, $query: String) {
                orders(first: $first, after: $after, query: $query) {
                    edges {
                        node {
                            id
                            cancelReason
                            cancelledAt
                            confirmed
                            createdAt
                            currencyCode
                            currentSubtotalPrice
                            currentTotalTax
                            email
                            financialStatus
                            fulfillmentStatus
                            name
                            orderNumber
                            phone
                            processedAt
                            updatedAt
                            test
                            totalPrice
                            subtotalPrice
                            totalShippingPrice
                            totalTax
                        }
                        cursor
                    }
                    pageInfo {
                        hasNextPage
                        hasPreviousPage
                        startCursor
                        endCursor
                    }
                }
            }
            """;
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first != null ? first : 10);
        if (after != null) {
            variables.put("after", after);
        }
        if (query != null) {
            variables.put("query", query);
        }
        
        return graphQLClient.query(shop, accessToken, graphQLQuery, variables)
            .map(this::extractOrderConnectionFromResponse);
    }
    
    /**
     * Marks an order as paid.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param orderId the order ID
     * @return Mono of Order
     */
    public Mono<Order> markOrderAsPaid(String shop, String accessToken, String orderId) {
        String mutation = """
            mutation orderMarkAsPaid($input: OrderMarkAsPaidInput!) {
                orderMarkAsPaid(input: $input) {
                    order {
                        id
                        financialStatus
                        updatedAt
                    }
                    userErrors {
                        field
                        message
                    }
                }
            }
            """;
        
        Map<String, Object> input = new HashMap<>();
        input.put("id", orderId);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        return graphQLClient.query(shop, accessToken, mutation, variables)
            .map(this::extractOrderFromMutationResponse);
    }
    
    /**
     * Cancels an order.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param orderId the order ID
     * @param reason the cancellation reason
     * @param notifyCustomer whether to notify the customer
     * @param refund whether to refund the order
     * @return Mono of Order
     */
    public Mono<Order> cancelOrder(String shop, String accessToken, String orderId, String reason, Boolean notifyCustomer, Boolean refund) {
        String mutation = """
            mutation orderCancel($orderId: ID!, $reason: OrderCancelReason, $notifyCustomer: Boolean, $refund: Boolean) {
                orderCancel(orderId: $orderId, reason: $reason, notifyCustomer: $notifyCustomer, refund: $refund) {
                    order {
                        id
                        cancelReason
                        cancelledAt
                        financialStatus
                        fulfillmentStatus
                        updatedAt
                    }
                    userErrors {
                        field
                        message
                    }
                }
            }
            """;
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("orderId", orderId);
        if (reason != null) {
            variables.put("reason", reason);
        }
        if (notifyCustomer != null) {
            variables.put("notifyCustomer", notifyCustomer);
        }
        if (refund != null) {
            variables.put("refund", refund);
        }
        
        return graphQLClient.query(shop, accessToken, mutation, variables)
            .map(this::extractOrderFromCancelResponse);
    }
    
    private Order extractOrderFromResponse(GraphQLResponse response) {
        try {
            JsonNode data = response.getData();
            if (data != null && data.has("order")) {
                JsonNode orderNode = data.get("order");
                if (!orderNode.isNull()) {
                    return objectMapper.treeToValue(orderNode, Order.class);
                }
            }
            return null;
        } catch (Exception e) {
            log.error("Error extracting order from response", e);
            throw new ShopifyApiException("Failed to parse order response", e);
        }
    }
    
    private OrderConnection extractOrderConnectionFromResponse(GraphQLResponse response) {
        try {
            JsonNode data = response.getData();
            if (data != null && data.has("orders")) {
                JsonNode ordersNode = data.get("orders");
                return objectMapper.treeToValue(ordersNode, OrderConnection.class);
            }
            return new OrderConnection();
        } catch (Exception e) {
            log.error("Error extracting order connection from response", e);
            throw new ShopifyApiException("Failed to parse orders response", e);
        }
    }
    
    private Order extractOrderFromMutationResponse(GraphQLResponse response) {
        try {
            JsonNode data = response.getData();
            if (data != null && data.has("orderMarkAsPaid")) {
                JsonNode mutationNode = data.get("orderMarkAsPaid");
                
                // Check for user errors first
                if (mutationNode.has("userErrors")) {
                    JsonNode userErrors = mutationNode.get("userErrors");
                    if (userErrors.isArray() && userErrors.size() > 0) {
                        StringBuilder errorMessage = new StringBuilder("Order mutation failed: ");
                        for (JsonNode error : userErrors) {
                            String field = error.has("field") ? error.get("field").asText() : "unknown";
                            String message = error.has("message") ? error.get("message").asText() : "unknown error";
                            errorMessage.append(String.format("[%s: %s] ", field, message));
                        }
                        throw new ShopifyApiException(errorMessage.toString());
                    }
                }
                
                // Extract the order
                if (mutationNode.has("order")) {
                    JsonNode orderNode = mutationNode.get("order");
                    if (!orderNode.isNull()) {
                        return objectMapper.treeToValue(orderNode, Order.class);
                    }
                }
            }
            return null;
        } catch (ShopifyApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error extracting order from mutation response", e);
            throw new ShopifyApiException("Failed to parse order mutation response", e);
        }
    }
    
    private Order extractOrderFromCancelResponse(GraphQLResponse response) {
        try {
            JsonNode data = response.getData();
            if (data != null && data.has("orderCancel")) {
                JsonNode mutationNode = data.get("orderCancel");
                
                // Check for user errors first
                if (mutationNode.has("userErrors")) {
                    JsonNode userErrors = mutationNode.get("userErrors");
                    if (userErrors.isArray() && userErrors.size() > 0) {
                        StringBuilder errorMessage = new StringBuilder("Order cancellation failed: ");
                        for (JsonNode error : userErrors) {
                            String field = error.has("field") ? error.get("field").asText() : "unknown";
                            String message = error.has("message") ? error.get("message").asText() : "unknown error";
                            errorMessage.append(String.format("[%s: %s] ", field, message));
                        }
                        throw new ShopifyApiException(errorMessage.toString());
                    }
                }
                
                // Extract the order
                if (mutationNode.has("order")) {
                    JsonNode orderNode = mutationNode.get("order");
                    if (!orderNode.isNull()) {
                        return objectMapper.treeToValue(orderNode, Order.class);
                    }
                }
            }
            return null;
        } catch (ShopifyApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error extracting order from cancel response", e);
            throw new ShopifyApiException("Failed to parse order cancel response", e);
        }
    }
}