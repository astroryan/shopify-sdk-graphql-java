package com.shopify.sdk.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shopify.sdk.client.graphql.GraphQLResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Utility class for test helpers and mock data generation.
 */
public class TestUtils {
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * Creates a successful GraphQL response with the given data.
     */
    public static GraphQLResponse createSuccessResponse(ObjectNode data) {
        GraphQLResponse response = new GraphQLResponse();
        response.setData(data);
        return response;
    }
    
    /**
     * Creates an error GraphQL response with the given error message.
     */
    public static GraphQLResponse createErrorResponse(String message, String code) {
        GraphQLResponse response = new GraphQLResponse();
        GraphQLResponse.GraphQLError error = new GraphQLResponse.GraphQLError();
        error.setMessage(message);
        error.setExtensions(Map.of("code", code));
        response.setErrors(java.util.Collections.singletonList(error));
        return response;
    }
    
    /**
     * Loads a JSON file from test resources.
     */
    public static JsonNode loadJsonFromResource(String resourcePath) throws IOException {
        try (InputStream is = TestUtils.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }
            return objectMapper.readTree(is);
        }
    }
    
    /**
     * Loads a text file from test resources.
     */
    public static String loadTextFromResource(String resourcePath) throws IOException {
        try (InputStream is = TestUtils.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
    
    /**
     * Creates a mock Product GraphQL response.
     */
    public static ObjectNode createMockProduct(String id, String title, String price) {
        ObjectNode product = objectMapper.createObjectNode();
        product.put("id", id);
        product.put("title", title);
        product.put("handle", title.toLowerCase().replace(" ", "-"));
        product.put("productType", "Test Type");
        product.put("vendor", "Test Vendor");
        product.put("status", "ACTIVE");
        
        ObjectNode variants = product.putObject("variants");
        var edges = variants.putArray("edges");
        var edge = edges.addObject();
        var variant = edge.putObject("node");
        variant.put("id", "gid://shopify/ProductVariant/1");
        variant.put("title", "Default");
        variant.put("price", price);
        variant.put("sku", "TEST-001");
        
        return product;
    }
    
    /**
     * Creates a mock Order GraphQL response.
     */
    public static ObjectNode createMockOrder(String id, String name, String totalPrice) {
        ObjectNode order = objectMapper.createObjectNode();
        order.put("id", id);
        order.put("name", name);
        order.put("email", "customer@example.com");
        order.put("financialStatus", "PAID");
        order.put("fulfillmentStatus", "UNFULFILLED");
        order.put("createdAt", "2024-01-01T00:00:00Z");
        
        ObjectNode priceSet = order.putObject("totalPriceSet");
        ObjectNode presentmentMoney = priceSet.putObject("presentmentMoney");
        presentmentMoney.put("amount", totalPrice);
        presentmentMoney.put("currencyCode", "USD");
        
        return order;
    }
    
    /**
     * Creates a mock Customer GraphQL response.
     */
    public static ObjectNode createMockCustomer(String id, String email, String firstName, String lastName) {
        ObjectNode customer = objectMapper.createObjectNode();
        customer.put("id", id);
        customer.put("email", email);
        customer.put("firstName", firstName);
        customer.put("lastName", lastName);
        customer.put("displayName", firstName + " " + lastName);
        customer.put("state", "ENABLED");
        customer.put("acceptsMarketing", true);
        customer.put("createdAt", "2024-01-01T00:00:00Z");
        
        return customer;
    }
    
    /**
     * Creates a mock Webhook Subscription GraphQL response.
     */
    public static ObjectNode createMockWebhookSubscription(String id, String topic, String callbackUrl) {
        ObjectNode subscription = objectMapper.createObjectNode();
        subscription.put("id", id);
        subscription.put("topic", topic);
        subscription.put("callbackUrl", callbackUrl);
        subscription.put("format", "JSON");
        subscription.put("createdAt", "2024-01-01T00:00:00Z");
        subscription.put("updatedAt", "2024-01-01T00:00:00Z");
        
        return subscription;
    }
    
    /**
     * Creates a mock Metafield GraphQL response.
     */
    public static ObjectNode createMockMetafield(String id, String namespace, String key, String value, String type) {
        ObjectNode metafield = objectMapper.createObjectNode();
        metafield.put("id", id);
        metafield.put("namespace", namespace);
        metafield.put("key", key);
        metafield.put("value", value);
        metafield.put("type", type);
        metafield.put("createdAt", "2024-01-01T00:00:00Z");
        metafield.put("updatedAt", "2024-01-01T00:00:00Z");
        
        return metafield;
    }
    
    /**
     * Creates a mock Location GraphQL response.
     */
    public static ObjectNode createMockLocation(String id, String name, boolean active) {
        ObjectNode location = objectMapper.createObjectNode();
        location.put("id", id);
        location.put("name", name);
        location.put("active", active);
        location.put("fulfillsOnlineOrders", true);
        
        ObjectNode address = location.putObject("address");
        address.put("address1", "123 Test St");
        address.put("city", "Test City");
        address.put("province", "Test Province");
        address.put("country", "Test Country");
        address.put("zip", "12345");
        
        return location;
    }
    
    /**
     * Creates page info for GraphQL connections.
     */
    public static ObjectNode createPageInfo(boolean hasNextPage, boolean hasPreviousPage, String endCursor) {
        ObjectNode pageInfo = objectMapper.createObjectNode();
        pageInfo.put("hasNextPage", hasNextPage);
        pageInfo.put("hasPreviousPage", hasPreviousPage);
        if (endCursor != null) {
            pageInfo.put("endCursor", endCursor);
        }
        return pageInfo;
    }
    
    /**
     * Creates a connection response with edges and page info.
     */
    public static ObjectNode createConnection(String fieldName, ObjectNode[] nodes, boolean hasNextPage) {
        ObjectNode connection = objectMapper.createObjectNode();
        var edges = connection.putArray("edges");
        
        for (ObjectNode node : nodes) {
            var edge = edges.addObject();
            edge.set("node", node);
            edge.put("cursor", "cursor_" + node.get("id").asText());
        }
        
        connection.set("pageInfo", createPageInfo(hasNextPage, false, 
            hasNextPage ? "cursor_next" : null));
        
        return connection;
    }
    
    /**
     * Creates user errors for GraphQL mutations.
     */
    public static ObjectNode createUserError(String field, String message, String code) {
        ObjectNode error = objectMapper.createObjectNode();
        var fields = error.putArray("field");
        fields.add(field);
        error.put("message", message);
        error.put("code", code);
        return error;
    }
    
    /**
     * Gets the ObjectMapper instance for custom operations.
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}