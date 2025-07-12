package com.shopify.sdk.webhook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.auth.ShopifyOAuth;
import com.shopify.sdk.config.ShopifyAuthContext;
import com.shopify.sdk.exception.ShopifyApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebhookProcessorTest {
    
    @Mock
    private ShopifyAuthContext authContext;
    
    @Mock
    private ShopifyOAuth shopifyOAuth;
    
    private ObjectMapper objectMapper;
    private List<WebhookHandler> handlers;
    private WebhookProcessor webhookProcessor;
    
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        handlers = new ArrayList<>();
        webhookProcessor = new WebhookProcessor(authContext, shopifyOAuth, objectMapper, handlers);
    }
    
    @Test
    @DisplayName("Should process webhook successfully with valid HMAC")
    void testProcessWebhookSuccess() throws InterruptedException {
        // Given
        String webhookPayload = """
            {
                "id": 12345,
                "email": "test@example.com",
                "first_name": "Test",
                "last_name": "Customer"
            }
            """;
        
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Shopify-Topic", "customers/create");
        headers.put("X-Shopify-Shop-Domain", "test-shop.myshopify.com");
        headers.put("X-Shopify-Hmac-Sha256", "valid-hmac");
        headers.put("X-Shopify-Webhook-Id", "webhook-123");
        headers.put("X-Shopify-API-Version", "2024-01");
        
        when(authContext.hasWebhookSecret()).thenReturn(false);
        
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<WebhookEvent> capturedEvent = new AtomicReference<>();
        
        TestWebhookHandler handler = new TestWebhookHandler("customers/create", event -> {
            capturedEvent.set(event);
            latch.countDown();
            return Mono.empty();
        });
        
        handlers.add(handler);
        
        // When
        Mono<WebhookEvent> result = webhookProcessor.processWebhook(webhookPayload, headers);
        
        // Then
        StepVerifier.create(result)
            .assertNext(event -> {
                assertThat(event).isNotNull();
                assertThat(event.getTopic()).isEqualTo("customers/create");
                assertThat(event.getShop()).isEqualTo("test-shop.myshopify.com");
            })
            .verifyComplete();
        
        assertTrue(latch.await(5, TimeUnit.SECONDS));
        
        WebhookEvent event = capturedEvent.get();
        assertThat(event).isNotNull();
        assertThat(event.getTopic()).isEqualTo("customers/create");
        assertThat(event.getShop()).isEqualTo("test-shop.myshopify.com");
        assertThat(event.getId()).isEqualTo("webhook-123");
        assertThat(event.getApiVersion()).isEqualTo("2024-01");
        assertThat(event.getPayload()).isNotNull();
        assertThat(event.getPayload().get("email").asText()).isEqualTo("test@example.com");
    }
    
    @Test
    @DisplayName("Should reject webhook with invalid HMAC")
    void testRejectInvalidHmac() {
        // Given
        String webhookPayload = """
            {
                "id": 12345,
                "email": "test@example.com"
            }
            """;
        
        Map<String, String> headers = Map.of(
            "X-Shopify-Topic", "customers/create",
            "X-Shopify-Shop-Domain", "test-shop.myshopify.com",
            "X-Shopify-Hmac-Sha256", "invalid-hmac"
        );
        
        when(authContext.hasWebhookSecret()).thenReturn(true);
        when(shopifyOAuth.validateWebhook(webhookPayload, "invalid-hmac")).thenReturn(false);
        
        // When
        Mono<WebhookEvent> result = webhookProcessor.processWebhook(webhookPayload, headers);
        
        // Then
        StepVerifier.create(result)
            .expectError(ShopifyApiException.class)
            .verify();
    }
    
    @Test
    @DisplayName("Should handle missing required headers")
    void testMissingHeaders() {
        // Given
        String webhookPayload = """
            {
                "id": 12345
            }
            """;
        
        Map<String, String> headers = Map.of(
            "X-Shopify-Topic", "customers/create"
            // Missing X-Shopify-Shop-Domain and X-Shopify-Hmac-Sha256
        );
        
        when(authContext.hasWebhookSecret()).thenReturn(false);
        
        // When
        Mono<WebhookEvent> result = webhookProcessor.processWebhook(webhookPayload, headers);
        
        // Then
        StepVerifier.create(result)
            .assertNext(event -> {
                assertThat(event).isNotNull();
                assertThat(event.getTopic()).isEqualTo("customers/create");
                assertThat(event.getShop()).isNull(); // Missing X-Shopify-Shop-Domain
                assertThat(event.getHmacSignature()).isNull(); // Missing X-Shopify-Hmac-Sha256
            })
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should handle wildcard handlers")
    void testWildcardHandler() throws InterruptedException {
        // Given
        String webhookPayload = """
            {
                "id": 12345,
                "title": "Test Product"
            }
            """;
        
        Map<String, String> headers = Map.of(
            "X-Shopify-Topic", "products/create",
            "X-Shopify-Shop-Domain", "test-shop.myshopify.com",
            "X-Shopify-Hmac-Sha256", "valid-hmac"
        );
        
        when(authContext.hasWebhookSecret()).thenReturn(false);
        
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<String> capturedTopic = new AtomicReference<>();
        
        TestWebhookHandler wildcardHandler = new TestWebhookHandler("*", event -> {
            capturedTopic.set(event.getTopic());
            latch.countDown();
            return Mono.empty();
        });
        
        handlers.add(wildcardHandler);
        
        // When
        Mono<WebhookEvent> result = webhookProcessor.processWebhook(webhookPayload, headers);
        
        // Then
        StepVerifier.create(result)
            .assertNext(event -> assertThat(event.getTopic()).isEqualTo("products/create"))
            .verifyComplete();
        
        assertTrue(latch.await(5, TimeUnit.SECONDS));
        assertThat(capturedTopic.get()).isEqualTo("products/create");
    }
    
    @Test
    @DisplayName("Should handle multiple handlers for same topic")
    void testMultipleHandlers() throws InterruptedException {
        // Given
        String webhookPayload = """
            {
                "id": 12345,
                "name": "#1001"
            }
            """;
        
        Map<String, String> headers = Map.of(
            "X-Shopify-Topic", "orders/create",
            "X-Shopify-Shop-Domain", "test-shop.myshopify.com",
            "X-Shopify-Hmac-Sha256", "valid-hmac"
        );
        
        when(authContext.hasWebhookSecret()).thenReturn(false);
        
        CountDownLatch latch = new CountDownLatch(2);
        AtomicReference<Integer> handlerCount = new AtomicReference<>(0);
        
        TestWebhookHandler handler1 = new TestWebhookHandler("orders/create", event -> {
            handlerCount.updateAndGet(v -> v + 1);
            latch.countDown();
            return Mono.empty();
        });
        
        TestWebhookHandler handler2 = new TestWebhookHandler("orders/create", event -> {
            handlerCount.updateAndGet(v -> v + 1);
            latch.countDown();
            return Mono.empty();
        });
        
        handlers.add(handler1);
        handlers.add(handler2);
        
        // When
        Mono<WebhookEvent> result = webhookProcessor.processWebhook(webhookPayload, headers);
        
        // Then
        StepVerifier.create(result)
            .assertNext(event -> assertThat(event).isNotNull())
            .verifyComplete();
        
        assertTrue(latch.await(5, TimeUnit.SECONDS));
        assertThat(handlerCount.get()).isEqualTo(2);
    }
    
    @Test
    @DisplayName("Should handle handler errors gracefully")
    void testHandlerError() {
        // Given
        String webhookPayload = """
            {
                "id": 12345
            }
            """;
        
        Map<String, String> headers = Map.of(
            "X-Shopify-Topic", "orders/paid",
            "X-Shopify-Shop-Domain", "test-shop.myshopify.com",
            "X-Shopify-Hmac-Sha256", "valid-hmac"
        );
        
        when(authContext.hasWebhookSecret()).thenReturn(false);
        
        TestWebhookHandler errorHandler = new TestWebhookHandler("orders/paid", event -> {
            return Mono.error(new RuntimeException("Handler error"));
        });
        
        handlers.add(errorHandler);
        
        // When
        Mono<WebhookEvent> result = webhookProcessor.processWebhook(webhookPayload, headers);
        
        // Then - The processor should still return the event, but log the handler error
        StepVerifier.create(result)
            .assertNext(event -> assertThat(event).isNotNull())
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should parse webhook event correctly")
    void testWebhookEventParsing() throws InterruptedException {
        // Given
        String webhookPayload = """
            {
                "id": 12345,
                "line_items": [
                    {
                        "id": 1,
                        "title": "Product 1",
                        "quantity": 2
                    },
                    {
                        "id": 2,
                        "title": "Product 2",
                        "quantity": 1
                    }
                ],
                "customer": {
                    "id": 67890,
                    "email": "customer@example.com"
                }
            }
            """;
        
        Map<String, String> headers = Map.of(
            "X-Shopify-Topic", "orders/fulfilled",
            "X-Shopify-Shop-Domain", "test-shop.myshopify.com",
            "X-Shopify-Hmac-Sha256", "valid-hmac",
            "X-Shopify-Triggered-At", "2024-01-15T10:00:00Z"
        );
        
        when(authContext.hasWebhookSecret()).thenReturn(false);
        
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<WebhookEvent> capturedEvent = new AtomicReference<>();
        
        TestWebhookHandler handler = new TestWebhookHandler("orders/fulfilled", event -> {
            capturedEvent.set(event);
            latch.countDown();
            return Mono.empty();
        });
        
        handlers.add(handler);
        
        // When
        Mono<WebhookEvent> result = webhookProcessor.processWebhook(webhookPayload, headers);
        
        // Then
        StepVerifier.create(result)
            .assertNext(event -> assertThat(event).isNotNull())
            .verifyComplete();
        
        assertTrue(latch.await(5, TimeUnit.SECONDS));
        
        WebhookEvent event = capturedEvent.get();
        assertThat(event.getPayload()).isNotNull();
        assertThat(event.getPayload().has("line_items")).isTrue();
        assertThat(event.getPayload().get("customer").get("email").asText()).isEqualTo("customer@example.com");
        assertThat(event.getHeaders().get("X-Shopify-Triggered-At")).isEqualTo("2024-01-15T10:00:00Z");
    }
    
    // Test webhook handler implementation
    private static class TestWebhookHandler implements WebhookHandler {
        private final String topic;
        private final HandlerFunction handlerFunction;
        
        public TestWebhookHandler(String topic, HandlerFunction handlerFunction) {
            this.topic = topic;
            this.handlerFunction = handlerFunction;
        }
        
        @Override
        public boolean canHandle(WebhookEvent event) {
            return "*".equals(topic) || topic.equals(event.getTopic());
        }
        
        @Override
        public Mono<Void> handle(WebhookEvent event) {
            return handlerFunction.handle(event);
        }
        
        @Override
        public WebhookEventType[] getSupportedEventTypes() {
            return null;
        }
    }
    
    @FunctionalInterface
    private interface HandlerFunction {
        Mono<Void> handle(WebhookEvent event);
    }
}