package com.shopify.sdk.service;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.common.ID;
import com.shopify.sdk.model.graphql.GraphQLRequest;
import com.shopify.sdk.model.graphql.GraphQLResponse;
import com.shopify.sdk.model.webhook.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * WebhookService 테스트
 * 웹훅 구독 관리 기능을 테스트합니다.
 */
@ExtendWith(MockitoExtension.class)
class WebhookServiceTest {
    
    @Mock
    private ShopifyGraphQLClient graphQLClient;
    
    private WebhookService webhookService;
    private ShopifyAuthContext authContext;
    
    @BeforeEach
    void setUp() {
        webhookService = new WebhookService(graphQLClient);
        authContext = ShopifyAuthContext.builder()
            .shopDomain("test-store.myshopify.com")
            .accessToken("test-token")
            .apiVersion("2025-07")
            .build();
    }
    
    @Test
    @DisplayName("웹훅 구독 생성 - HTTP 엔드포인트")
    void testCreateWebhookSubscription_HttpEndpoint() {
        // Given
        WebhookSubscriptionInput input = WebhookSubscriptionInput.builder()
            .topic(WebhookTopic.ORDERS_CREATE)
            .callbackUrl("https://myapp.com/webhooks/orders/create")
            .format(WebhookFormat.JSON)
            .includeFields(Arrays.asList("id", "email", "total_price", "line_items"))
            .build();
        
        WebhookSubscription expectedWebhook = WebhookSubscription.builder()
            .id(new ID("gid://shopify/WebhookSubscription/123456"))
            .topic(WebhookTopic.ORDERS_CREATE)
            .callbackUrl("https://myapp.com/webhooks/orders/create")
            .format(WebhookFormat.JSON)
            .apiVersion("2025-07")
            .createdAt(ZonedDateTime.now())
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockWebhookCreateResponse(expectedWebhook);
        when(graphQLClient.execute(any(GraphQLRequest.class), any())).thenReturn(mockResponse);
        
        // When
        WebhookSubscription webhook = webhookService.createWebhookSubscription(authContext, input);
        
        // Then
        assertNotNull(webhook);
        assertEquals(WebhookTopic.ORDERS_CREATE, webhook.getTopic());
        assertEquals("https://myapp.com/webhooks/orders/create", webhook.getCallbackUrl());
        assertEquals(WebhookFormat.JSON, webhook.getFormat());
        
        // Verify GraphQL request
        ArgumentCaptor<GraphQLRequest> requestCaptor = ArgumentCaptor.forClass(GraphQLRequest.class);
        verify(graphQLClient).execute(requestCaptor.capture(), any());
        
        GraphQLRequest request = requestCaptor.getValue();
        assertTrue(request.getQuery().contains("webhookSubscriptionCreate"));
        assertEquals(input, request.getVariables().get("input"));
    }
    
    @Test
    @DisplayName("웹훅 구독 생성 - EventBridge 엔드포인트")
    void testCreateWebhookSubscription_EventBridge() {
        // Given
        WebhookSubscriptionInput input = WebhookSubscriptionInput.builder()
            .topic(WebhookTopic.PRODUCTS_UPDATE)
            .arn("arn:aws:events:us-east-1:123456789012:event-bus/shopify-events")
            .format(WebhookFormat.JSON)
            .build();
        
        WebhookSubscription expectedWebhook = WebhookSubscription.builder()
            .id(new ID("gid://shopify/WebhookSubscription/789012"))
            .topic(WebhookTopic.PRODUCTS_UPDATE)
            .endpoint(WebhookEndpoint.builder()
                .arn("arn:aws:events:us-east-1:123456789012:event-bus/shopify-events")
                .build())
            .format(WebhookFormat.JSON)
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockWebhookCreateResponse(expectedWebhook);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        WebhookSubscription webhook = webhookService.createWebhookSubscription(authContext, input);
        
        // Then
        assertNotNull(webhook);
        assertEquals(WebhookTopic.PRODUCTS_UPDATE, webhook.getTopic());
        assertNotNull(webhook.getEndpoint());
        assertEquals("arn:aws:events:us-east-1:123456789012:event-bus/shopify-events", 
                    webhook.getEndpoint().getArn());
    }
    
    @Test
    @DisplayName("웹훅 구독 목록 조회")
    void testListWebhookSubscriptions() {
        // Given
        List<WebhookSubscription> expectedWebhooks = Arrays.asList(
            createTestWebhook("1", WebhookTopic.ORDERS_CREATE, "https://myapp.com/webhooks/orders/create"),
            createTestWebhook("2", WebhookTopic.PRODUCTS_UPDATE, "https://myapp.com/webhooks/products/update"),
            createTestWebhook("3", WebhookTopic.CUSTOMERS_CREATE, "https://myapp.com/webhooks/customers/create")
        );
        
        GraphQLResponse<Object> mockResponse = createMockWebhooksListResponse(expectedWebhooks);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        List<WebhookSubscription> webhooks = webhookService.listWebhookSubscriptions(
            authContext, 
            10, 
            null
        );
        
        // Then
        assertEquals(3, webhooks.size());
        assertEquals(WebhookTopic.ORDERS_CREATE, webhooks.get(0).getTopic());
        assertEquals(WebhookTopic.PRODUCTS_UPDATE, webhooks.get(1).getTopic());
        assertEquals(WebhookTopic.CUSTOMERS_CREATE, webhooks.get(2).getTopic());
    }
    
    @Test
    @DisplayName("웹훅 구독 업데이트")
    void testUpdateWebhookSubscription() {
        // Given
        String webhookId = "gid://shopify/WebhookSubscription/123456";
        WebhookSubscriptionInput updateInput = WebhookSubscriptionInput.builder()
            .callbackUrl("https://myapp.com/webhooks/orders/create/v2")
            .includeFields(Arrays.asList("id", "email", "total_price", "line_items", "customer"))
            .build();
        
        WebhookSubscription updatedWebhook = WebhookSubscription.builder()
            .id(new ID(webhookId))
            .topic(WebhookTopic.ORDERS_CREATE)
            .callbackUrl("https://myapp.com/webhooks/orders/create/v2")
            .format(WebhookFormat.JSON)
            .updatedAt(ZonedDateTime.now())
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockWebhookUpdateResponse(updatedWebhook);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        WebhookSubscription webhook = webhookService.updateWebhookSubscription(
            authContext, 
            webhookId, 
            updateInput
        );
        
        // Then
        assertNotNull(webhook);
        assertEquals("https://myapp.com/webhooks/orders/create/v2", webhook.getCallbackUrl());
    }
    
    @Test
    @DisplayName("웹훅 구독 삭제")
    void testDeleteWebhookSubscription() {
        // Given
        String webhookId = "gid://shopify/WebhookSubscription/123456";
        
        GraphQLResponse<Object> mockResponse = createMockWebhookDeleteResponse(webhookId);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        String deletedId = webhookService.deleteWebhookSubscription(authContext, webhookId);
        
        // Then
        assertEquals(webhookId, deletedId);
    }
    
    @Test
    @DisplayName("웹훅 구독 실패 - 잘못된 엔드포인트")
    void testCreateWebhookSubscription_InvalidEndpoint() {
        // Given
        WebhookSubscriptionInput input = WebhookSubscriptionInput.builder()
            .topic(WebhookTopic.ORDERS_CREATE)
            .callbackUrl("http://insecure.com/webhook") // HTTP는 허용되지 않음
            .format(WebhookFormat.JSON)
            .build();
        
        GraphQLResponse<Object> errorResponse = createMockWebhookCreateErrorResponse(
            "callbackUrl",
            "Webhook callback URL must use HTTPS"
        );
        when(graphQLClient.execute(any(), any())).thenReturn(errorResponse);
        
        // When & Then
        ShopifyApiException exception = assertThrows(ShopifyApiException.class, () -> {
            webhookService.createWebhookSubscription(authContext, input);
        });
        
        assertTrue(exception.getMessage().contains("Webhook callback URL must use HTTPS"));
    }
    
    @Test
    @DisplayName("웹훅 알림 실패 조회")
    void testGetWebhookNotificationFailures() {
        // Given
        String webhookId = "gid://shopify/WebhookSubscription/123456";
        List<WebhookNotificationFailure> failures = Arrays.asList(
            WebhookNotificationFailure.builder()
                .occurredAt(ZonedDateTime.now().minusHours(1))
                .httpStatusCode(500)
                .errorMessage("Internal Server Error")
                .build(),
            WebhookNotificationFailure.builder()
                .occurredAt(ZonedDateTime.now().minusMinutes(30))
                .httpStatusCode(503)
                .errorMessage("Service Unavailable")
                .build()
        );
        
        GraphQLResponse<Object> mockResponse = createMockNotificationFailuresResponse(failures);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        List<WebhookNotificationFailure> result = webhookService.getNotificationFailures(
            authContext,
            webhookId,
            10
        );
        
        // Then
        assertEquals(2, result.size());
        assertEquals(500, result.get(0).getHttpStatusCode());
        assertEquals(503, result.get(1).getHttpStatusCode());
    }
    
    @Test
    @DisplayName("웹훅 토픽별 필터링")
    void testListWebhooksByTopic() {
        // Given
        WebhookTopic targetTopic = WebhookTopic.ORDERS_CREATE;
        List<WebhookSubscription> allWebhooks = Arrays.asList(
            createTestWebhook("1", WebhookTopic.ORDERS_CREATE, "https://myapp.com/1"),
            createTestWebhook("2", WebhookTopic.PRODUCTS_UPDATE, "https://myapp.com/2"),
            createTestWebhook("3", WebhookTopic.ORDERS_CREATE, "https://myapp.com/3")
        );
        
        GraphQLResponse<Object> mockResponse = createMockWebhooksListResponse(allWebhooks);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        List<WebhookSubscription> filteredWebhooks = webhookService.listWebhooksByTopic(
            authContext,
            targetTopic
        );
        
        // Then
        assertEquals(2, filteredWebhooks.size());
        assertTrue(filteredWebhooks.stream().allMatch(w -> w.getTopic() == targetTopic));
    }
    
    @Test
    @DisplayName("필수 웹훅 구독 확인 및 생성")
    void testEnsureRequiredWebhooks() {
        // Given
        Map<WebhookTopic, String> requiredWebhooks = Map.of(
            WebhookTopic.ORDERS_CREATE, "https://myapp.com/webhooks/orders/create",
            WebhookTopic.PRODUCTS_UPDATE, "https://myapp.com/webhooks/products/update",
            WebhookTopic.CUSTOMERS_CREATE, "https://myapp.com/webhooks/customers/create"
        );
        
        // 기존 웹훅 (ORDERS_CREATE만 있음)
        List<WebhookSubscription> existingWebhooks = Arrays.asList(
            createTestWebhook("1", WebhookTopic.ORDERS_CREATE, "https://myapp.com/webhooks/orders/create")
        );
        
        GraphQLResponse<Object> listResponse = createMockWebhooksListResponse(existingWebhooks);
        when(graphQLClient.execute(any(), any()))
            .thenReturn(listResponse)
            .thenReturn(createMockWebhookCreateResponse(
                createTestWebhook("2", WebhookTopic.PRODUCTS_UPDATE, "https://myapp.com/webhooks/products/update")
            ))
            .thenReturn(createMockWebhookCreateResponse(
                createTestWebhook("3", WebhookTopic.CUSTOMERS_CREATE, "https://myapp.com/webhooks/customers/create")
            ));
        
        // When
        Map<WebhookTopic, WebhookSubscription> result = webhookService.ensureRequiredWebhooks(
            authContext,
            requiredWebhooks
        );
        
        // Then
        assertEquals(3, result.size());
        assertTrue(result.containsKey(WebhookTopic.ORDERS_CREATE));
        assertTrue(result.containsKey(WebhookTopic.PRODUCTS_UPDATE));
        assertTrue(result.containsKey(WebhookTopic.CUSTOMERS_CREATE));
        
        // Verify that 2 new webhooks were created
        verify(graphQLClient, times(3)).execute(any(), any()); // 1 list + 2 creates
    }
    
    // Helper methods
    private WebhookSubscription createTestWebhook(String id, WebhookTopic topic, String callbackUrl) {
        return WebhookSubscription.builder()
            .id(new ID("gid://shopify/WebhookSubscription/" + id))
            .topic(topic)
            .callbackUrl(callbackUrl)
            .format(WebhookFormat.JSON)
            .apiVersion("2025-07")
            .createdAt(ZonedDateTime.now())
            .build();
    }
    
    private GraphQLResponse<Object> createMockWebhookCreateResponse(WebhookSubscription webhook) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "webhookSubscriptionCreate", Map.of(
                "webhookSubscription", webhook,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockWebhooksListResponse(List<WebhookSubscription> webhooks) {
        List<Map<String, Object>> edges = new ArrayList<>();
        for (WebhookSubscription webhook : webhooks) {
            edges.add(Map.of("node", webhook));
        }
        
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "webhookSubscriptions", Map.of(
                "edges", edges,
                "pageInfo", Map.of("hasNextPage", false)
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockWebhookUpdateResponse(WebhookSubscription webhook) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "webhookSubscriptionUpdate", Map.of(
                "webhookSubscription", webhook,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockWebhookDeleteResponse(String webhookId) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "webhookSubscriptionDelete", Map.of(
                "deletedWebhookSubscriptionId", webhookId,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockWebhookCreateErrorResponse(String field, String message) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "webhookSubscriptionCreate", Map.of(
                "webhookSubscription", null,
                "userErrors", Arrays.asList(
                    Map.of(
                        "field", Arrays.asList(field),
                        "message", message,
                        "code", "INVALID"
                    )
                )
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockNotificationFailuresResponse(List<WebhookNotificationFailure> failures) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "webhookSubscription", Map.of(
                "notificationFailures", failures
            )
        ));
        return response;
    }
}