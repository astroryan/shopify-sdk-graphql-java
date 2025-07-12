package com.shopify.sdk.webhook;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.auth.ShopifyOAuth;
import com.shopify.sdk.config.ShopifyAuthContext;
import com.shopify.sdk.exception.ShopifyApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Service for processing Shopify webhooks.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WebhookProcessor {
    
    private final ShopifyAuthContext context;
    private final ShopifyOAuth shopifyOAuth;
    private final ObjectMapper objectMapper;
    private final List<WebhookHandler> webhookHandlers;
    
    // Statistics
    private final AtomicLong totalReceived = new AtomicLong(0);
    private final AtomicLong totalProcessed = new AtomicLong(0);
    private final AtomicLong totalFailed = new AtomicLong(0);
    private final Map<String, AtomicLong> eventTypeStats = new ConcurrentHashMap<>();
    
    /**
     * Processes a webhook request.
     *
     * @param rawBody the raw request body
     * @param headers the request headers
     * @return Mono containing the processed webhook event
     */
    public Mono<WebhookEvent> processWebhook(String rawBody, Map<String, String> headers) {
        totalReceived.incrementAndGet();
        
        return Mono.fromSupplier(() -> parseWebhookEvent(rawBody, headers))
            .flatMap(this::verifyWebhook)
            .flatMap(this::routeToHandlers)
            .doOnSuccess(event -> {
                totalProcessed.incrementAndGet();
                if (event.getEventType() != null) {
                    eventTypeStats.computeIfAbsent(event.getEventType().getTopic(), k -> new AtomicLong(0))
                        .incrementAndGet();
                }
                log.debug("Successfully processed webhook: {} for shop: {}", 
                    event.getTopic(), event.getShop());
            })
            .doOnError(error -> {
                totalFailed.incrementAndGet();
                log.error("Failed to process webhook", error);
            });
    }
    
    /**
     * Processes a webhook with verification disabled (for testing).
     *
     * @param rawBody the raw request body
     * @param headers the request headers
     * @return Mono containing the processed webhook event
     */
    public Mono<WebhookEvent> processWebhookWithoutVerification(String rawBody, Map<String, String> headers) {
        totalReceived.incrementAndGet();
        
        return Mono.fromSupplier(() -> parseWebhookEvent(rawBody, headers))
            .flatMap(this::routeToHandlers)
            .doOnSuccess(event -> {
                totalProcessed.incrementAndGet();
                log.debug("Successfully processed unverified webhook: {} for shop: {}", 
                    event.getTopic(), event.getShop());
            })
            .doOnError(error -> {
                totalFailed.incrementAndGet();
                log.error("Failed to process unverified webhook", error);
            });
    }
    
    /**
     * Verifies a webhook signature.
     *
     * @param rawBody the raw request body
     * @param hmacSignature the HMAC signature from header
     * @return true if verification succeeds
     */
    public boolean verifyWebhookSignature(String rawBody, String hmacSignature) {
        if (!context.hasWebhookSecret()) {
            log.warn("Webhook secret not configured, skipping verification");
            return true; // Allow if not configured
        }
        
        return shopifyOAuth.validateWebhook(rawBody, hmacSignature);
    }
    
    /**
     * Gets webhook processing statistics.
     *
     * @return webhook statistics
     */
    public WebhookStats getStats() {
        return WebhookStats.builder()
            .totalReceived(totalReceived.get())
            .totalProcessed(totalProcessed.get())
            .totalFailed(totalFailed.get())
            .eventTypeStats(Map.copyOf(eventTypeStats))
            .build();
    }
    
    /**
     * Resets webhook processing statistics.
     */
    public void resetStats() {
        totalReceived.set(0);
        totalProcessed.set(0);
        totalFailed.set(0);
        eventTypeStats.clear();
        log.info("Webhook statistics reset");
    }
    
    private WebhookEvent parseWebhookEvent(String rawBody, Map<String, String> headers) {
        try {
            String topic = headers.get("X-Shopify-Topic");
            String shop = headers.get("X-Shopify-Shop-Domain");
            String hmacSignature = headers.get("X-Shopify-Hmac-Sha256");
            String apiVersion = headers.get("X-Shopify-API-Version");
            String webhookId = headers.get("X-Shopify-Webhook-Id");
            
            JsonNode payload = null;
            if (rawBody != null && !rawBody.trim().isEmpty()) {
                payload = objectMapper.readTree(rawBody);
            }
            
            WebhookEvent event = WebhookEvent.builder()
                .id(webhookId != null ? webhookId : UUID.randomUUID().toString())
                .topic(topic)
                .eventType(WebhookEventType.fromTopic(topic))
                .shop(shop)
                .rawBody(rawBody)
                .payload(payload)
                .headers(Map.copyOf(headers))
                .hmacSignature(hmacSignature)
                .apiVersion(apiVersion)
                .receivedAt(Instant.now())
                .verified(false) // Will be set during verification
                .processed(false)
                .build();
            
            log.debug("Parsed webhook event: {} from shop: {}", topic, shop);
            return event;
            
        } catch (Exception e) {
            log.error("Failed to parse webhook event", e);
            throw new ShopifyApiException("Failed to parse webhook event", e);
        }
    }
    
    private Mono<WebhookEvent> verifyWebhook(WebhookEvent event) {
        return Mono.fromSupplier(() -> {
            if (!context.hasWebhookSecret()) {
                log.debug("Webhook secret not configured, skipping verification for event: {}", event.getId());
                event.setVerified(true);
                return event;
            }
            
            boolean isValid = verifyWebhookSignature(event.getRawBody(), event.getHmacSignature());
            event.setVerified(isValid);
            
            if (!isValid) {
                throw new ShopifyApiException("Webhook signature verification failed for event: " + event.getId());
            }
            
            log.debug("Webhook signature verified for event: {}", event.getId());
            return event;
        });
    }
    
    private Mono<WebhookEvent> routeToHandlers(WebhookEvent event) {
        List<WebhookHandler> applicableHandlers = webhookHandlers.stream()
            .filter(handler -> handler.canHandle(event))
            .sorted(Comparator.comparingInt(WebhookHandler::getPriority))
            .toList();
        
        if (applicableHandlers.isEmpty()) {
            log.debug("No handlers found for webhook event: {}", event.getTopic());
            event.markAsProcessed();
            return Mono.just(event);
        }
        
        log.debug("Found {} handlers for webhook event: {}", applicableHandlers.size(), event.getTopic());
        
        return Flux.fromIterable(applicableHandlers)
            .flatMap(handler -> processWithHandler(handler, event))
            .then(Mono.fromRunnable(() -> event.markAsProcessed()))
            .thenReturn(event)
            .onErrorMap(error -> {
                event.markAsFailed(error.getMessage());
                return new ShopifyApiException("Failed to process webhook with handlers", error);
            });
    }
    
    private Mono<Void> processWithHandler(WebhookHandler handler, WebhookEvent event) {
        return handler.handle(event)
            .doOnSuccess(ignored -> log.debug("Handler {} successfully processed webhook: {}", 
                handler.getClass().getSimpleName(), event.getId()))
            .onErrorResume(error -> {
                log.error("Handler {} failed to process webhook: {}", 
                    handler.getClass().getSimpleName(), event.getId(), error);
                return handler.onError(event, error);
            });
    }
    
    /**
     * Webhook processing statistics.
     */
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class WebhookStats {
        private long totalReceived;
        private long totalProcessed;
        private long totalFailed;
        private Map<String, AtomicLong> eventTypeStats;
        
        public double getSuccessRate() {
            return totalReceived > 0 ? (double) totalProcessed / totalReceived * 100 : 0.0;
        }
        
        public double getFailureRate() {
            return totalReceived > 0 ? (double) totalFailed / totalReceived * 100 : 0.0;
        }
    }
}