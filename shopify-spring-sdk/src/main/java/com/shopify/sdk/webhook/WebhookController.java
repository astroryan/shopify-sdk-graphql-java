package com.shopify.sdk.webhook;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for handling Shopify webhook endpoints.
 * This controller is only enabled if the property shopify.webhook.enabled is true.
 */
@Slf4j
@RestController
@RequestMapping("/webhooks/shopify")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "shopify.webhook.enabled", havingValue = "true", matchIfMissing = false)
public class WebhookController {
    
    private final WebhookProcessor webhookProcessor;
    
    /**
     * Handles webhook requests from Shopify.
     *
     * @param requestBody the raw request body
     * @param request the HTTP request for header extraction
     * @return ResponseEntity with processing result
     */
    @PostMapping(
        value = {"", "/"},
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.ALL_VALUE}
    )
    public Mono<ResponseEntity<String>> handleWebhook(
            @RequestBody String requestBody,
            HttpServletRequest request) {
        
        Map<String, String> headers = extractHeaders(request);
        String topic = headers.get("X-Shopify-Topic");
        String shop = headers.get("X-Shopify-Shop-Domain");
        
        log.info("Received webhook: {} from shop: {}", topic, shop);
        
        return webhookProcessor.processWebhook(requestBody, headers)
            .map(event -> {
                log.debug("Successfully processed webhook: {}", event.getId());
                return ResponseEntity.ok("Webhook processed successfully");
            })
            .onErrorResume(error -> {
                log.error("Failed to process webhook: {} from shop: {}", topic, shop, error);
                return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process webhook: " + error.getMessage()));
            });
    }
    
    /**
     * Handles webhook requests with verification disabled (for testing).
     *
     * @param requestBody the raw request body
     * @param request the HTTP request for header extraction
     * @return ResponseEntity with processing result
     */
    @PostMapping(
        value = "/test",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.ALL_VALUE}
    )
    public Mono<ResponseEntity<String>> handleTestWebhook(
            @RequestBody String requestBody,
            HttpServletRequest request) {
        
        Map<String, String> headers = extractHeaders(request);
        String topic = headers.get("X-Shopify-Topic");
        String shop = headers.get("X-Shopify-Shop-Domain");
        
        log.info("Received test webhook: {} from shop: {}", topic, shop);
        
        return webhookProcessor.processWebhookWithoutVerification(requestBody, headers)
            .map(event -> {
                log.debug("Successfully processed test webhook: {}", event.getId());
                return ResponseEntity.ok("Test webhook processed successfully");
            })
            .onErrorResume(error -> {
                log.error("Failed to process test webhook: {} from shop: {}", topic, shop, error);
                return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process test webhook: " + error.getMessage()));
            });
    }
    
    /**
     * Handles webhook verification requests from Shopify.
     * This endpoint is used by Shopify to verify that the webhook endpoint is reachable.
     *
     * @param request the HTTP request
     * @return ResponseEntity confirming the endpoint is reachable
     */
    @GetMapping({"", "/"})
    public ResponseEntity<String> verifyWebhookEndpoint(HttpServletRequest request) {
        String challenge = request.getParameter("hub.challenge");
        
        if (challenge != null && !challenge.trim().isEmpty()) {
            log.debug("Responding to webhook verification challenge");
            return ResponseEntity.ok(challenge);
        }
        
        return ResponseEntity.ok("Shopify webhook endpoint is active");
    }
    
    /**
     * Gets webhook processing statistics.
     *
     * @return webhook statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<WebhookProcessor.WebhookStats> getWebhookStats() {
        return ResponseEntity.ok(webhookProcessor.getStats());
    }
    
    /**
     * Resets webhook processing statistics.
     *
     * @return confirmation message
     */
    @PostMapping("/stats/reset")
    public ResponseEntity<String> resetWebhookStats() {
        webhookProcessor.resetStats();
        return ResponseEntity.ok("Webhook statistics reset successfully");
    }
    
    /**
     * Health check endpoint for the webhook service.
     *
     * @return health status
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "Shopify Webhook Handler");
        health.put("timestamp", System.currentTimeMillis());
        
        WebhookProcessor.WebhookStats stats = webhookProcessor.getStats();
        health.put("totalReceived", stats.getTotalReceived());
        health.put("totalProcessed", stats.getTotalProcessed());
        health.put("totalFailed", stats.getTotalFailed());
        health.put("successRate", String.format("%.2f%%", stats.getSuccessRate()));
        
        return ResponseEntity.ok(health);
    }
    
    private Map<String, String> extractHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                headers.put(headerName, headerValue);
            }
        }
        
        // Add some additional useful request information
        headers.put("Remote-Addr", request.getRemoteAddr());
        headers.put("Request-URI", request.getRequestURI());
        headers.put("Content-Length", String.valueOf(request.getContentLength()));
        
        return headers;
    }
}