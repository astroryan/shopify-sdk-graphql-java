package com.shopify.sdk.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Shopify API Mock Server for SDK Testing
 * 
 * SDK 사용자들이 자신의 코드를 테스트할 때도 사용할 수 있도록 설계됨
 * 실제 Shopify API의 동작을 정확히 모방함
 */
public class ShopifyMockServer {
    
    private final MockWebServer server;
    private final ObjectMapper objectMapper;
    private final String apiVersion;
    
    public ShopifyMockServer(String apiVersion) throws IOException {
        this.server = new MockWebServer();
        this.objectMapper = new ObjectMapper();
        this.apiVersion = apiVersion;
        this.server.start();
    }
    
    public String getUrl() {
        return server.url("/").toString();
    }
    
    public void shutdown() throws IOException {
        server.shutdown();
    }
    
    public RecordedRequest takeRequest() throws InterruptedException {
        return server.takeRequest();
    }
    
    // ===== Product 관련 Mock 응답 =====
    
    public void stubGetProduct(String productId, Map<String, Object> productData) {
        String response = createGraphQLResponse(Map.of("product", productData));
        
        server.enqueue(new MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setHeader("X-Shopify-Shop-Api-Call-Limit", "40/40")
            .setBody(response));
    }
    
    public void stubCreateProduct(Map<String, Object> productData) {
        String response = createGraphQLResponse(Map.of(
            "productCreate", Map.of(
                "product", productData,
                "userErrors", new Object[0]
            )
        ));
        
        server.enqueue(new MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody(response));
    }
    
    // ===== Order 관련 Mock 응답 =====
    
    public void stubGetOrders(Map<String, Object> ordersData) {
        String response = createGraphQLResponse(Map.of("orders", ordersData));
        
        server.enqueue(new MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody(response));
    }
    
    public void stubCreateFulfillment(Map<String, Object> fulfillmentData) {
        String response = createGraphQLResponse(Map.of(
            "fulfillmentCreate", Map.of(
                "fulfillment", fulfillmentData,
                "userErrors", new Object[0]
            )
        ));
        
        server.enqueue(new MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody(response));
    }
    
    // ===== 에러 시나리오 =====
    
    public void stubRateLimit() {
        server.enqueue(new MockResponse()
            .setResponseCode(429)
            .setHeader("Retry-After", "2.0")
            .setHeader("X-Shopify-Shop-Api-Call-Limit", "40/40")
            .setBody("{\"errors\":\"Throttled\"}"));
    }
    
    public void stubUnauthorized() {
        server.enqueue(new MockResponse()
            .setResponseCode(401)
            .setHeader("Content-Type", "application/json")
            .setBody("{\"errors\":\"Unauthorized\"}"));
    }
    
    public void stubGraphQLError(String message, String code) {
        String response = createGraphQLResponse(
            null,
            new Object[]{
                Map.of(
                    "message", message,
                    "extensions", Map.of("code", code)
                )
            }
        );
        
        server.enqueue(new MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody(response));
    }
    
    public void stubNetworkTimeout(int delaySeconds) {
        server.enqueue(new MockResponse()
            .setBodyDelay(delaySeconds, TimeUnit.SECONDS)
            .setResponseCode(200));
    }
    
    // ===== Webhook 검증 =====
    
    public void stubWebhookValidation(String body, String hmac) {
        server.enqueue(new MockResponse()
            .setResponseCode(200)
            .setHeader("X-Shopify-Hmac-Sha256", hmac)
            .setHeader("X-Shopify-Topic", "orders/create")
            .setHeader("X-Shopify-Shop-Domain", "test-shop.myshopify.com")
            .setBody(body));
    }
    
    // ===== 대량 작업 (Bulk Operations) =====
    
    public void stubBulkOperationCreate(String operationId) {
        String response = createGraphQLResponse(Map.of(
            "bulkOperationRunQuery", Map.of(
                "bulkOperation", Map.of(
                    "id", operationId,
                    "status", "CREATED",
                    "url", null
                ),
                "userErrors", new Object[0]
            )
        ));
        
        server.enqueue(new MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody(response));
    }
    
    public void stubBulkOperationStatus(String operationId, String status, String resultUrl) {
        Map<String, Object> bulkOperation = Map.of(
            "id", operationId,
            "status", status,
            "completedAt", status.equals("COMPLETED") ? "2024-01-01T00:00:00Z" : null,
            "url", resultUrl != null ? resultUrl : ""
        );
        
        String response = createGraphQLResponse(Map.of(
            "node", bulkOperation
        ));
        
        server.enqueue(new MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody(response));
    }
    
    // ===== Helper Methods =====
    
    private String createGraphQLResponse(Map<String, Object> data) {
        return createGraphQLResponse(data, null);
    }
    
    private String createGraphQLResponse(Map<String, Object> data, Object[] errors) {
        try {
            Map<String, Object> response = new java.util.HashMap<>();
            
            if (data != null) {
                response.put("data", data);
            }
            
            if (errors != null && errors.length > 0) {
                response.put("errors", errors);
            }
            
            // Shopify API 응답에 포함되는 extensions 추가
            response.put("extensions", Map.of(
                "cost", Map.of(
                    "requestedQueryCost", 10,
                    "actualQueryCost", 8,
                    "throttleStatus", Map.of(
                        "maximumAvailable", 1000,
                        "currentlyAvailable", 992,
                        "restoreRate", 50
                    )
                )
            ));
            
            return objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create mock response", e);
        }
    }
    
    // ===== 실제 사용 예시를 위한 빌더 패턴 =====
    
    public static class ResponseBuilder {
        private final ShopifyMockServer server;
        
        public ResponseBuilder(ShopifyMockServer server) {
            this.server = server;
        }
        
        public ResponseBuilder withSuccessfulProductQuery(String productId, String title, String status) {
            server.stubGetProduct(productId, Map.of(
                "id", "gid://shopify/Product/" + productId,
                "title", title,
                "status", status,
                "handle", title.toLowerCase().replace(" ", "-"),
                "vendor", "Test Vendor",
                "createdAt", "2024-01-01T00:00:00Z"
            ));
            return this;
        }
        
        public ResponseBuilder withSuccessfulOrdersQuery(int count) {
            Map<String, Object>[] edges = new Map[count];
            for (int i = 0; i < count; i++) {
                edges[i] = Map.of(
                    "node", Map.of(
                        "id", "gid://shopify/Order/" + (1000 + i),
                        "name", "#" + (1000 + i),
                        "totalPrice", Map.of(
                            "amount", "99.99",
                            "currencyCode", "USD"
                        ),
                        "financialStatus", "PAID",
                        "createdAt", "2024-01-01T00:00:00Z"
                    ),
                    "cursor", "cursor_" + i
                );
            }
            
            server.stubGetOrders(Map.of(
                "edges", edges,
                "pageInfo", Map.of(
                    "hasNextPage", count >= 10,
                    "endCursor", count > 0 ? "cursor_" + (count - 1) : null
                )
            ));
            return this;
        }
        
        public ResponseBuilder withRateLimit() {
            server.stubRateLimit();
            return this;
        }
        
        public ResponseBuilder withNetworkDelay(int seconds) {
            server.stubNetworkTimeout(seconds);
            return this;
        }
    }
    
    public ResponseBuilder expect() {
        return new ResponseBuilder(this);
    }
}