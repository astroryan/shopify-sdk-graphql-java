package com.shopify.sdk.webhook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.model.webhook.WebhookNotification;
import com.shopify.sdk.model.webhook.WebhookSubscriptionTopic;
import com.shopify.sdk.service.WebhookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Default implementation of WebhookHandler
 */
@Slf4j
@Component
public class DefaultWebhookHandler implements WebhookHandler {
    
    private final Map<WebhookSubscriptionTopic, Consumer<WebhookNotification>> handlers;
    private final WebhookService webhookService;
    private final ObjectMapper objectMapper;
    
    public DefaultWebhookHandler(WebhookService webhookService, ObjectMapper objectMapper) {
        this.webhookService = webhookService;
        this.objectMapper = objectMapper;
        this.handlers = new HashMap<>();
    }
    
    @Override
    public void registerHandler(WebhookSubscriptionTopic topic, Consumer<WebhookNotification> handler) {
        handlers.put(topic, handler);
        log.info("Registered webhook handler for topic: {}", topic);
    }
    
    @Override
    public boolean processWebhook(String hmacHeader, String rawBody, String secret) {
        // Verify HMAC
        if (!webhookService.verifyWebhookHmac(hmacHeader, rawBody, secret)) {
            log.error("Webhook HMAC verification failed");
            return false;
        }
        
        try {
            // Parse webhook notification
            WebhookNotification notification = objectMapper.readValue(rawBody, WebhookNotification.class);
            
            // Get topic
            WebhookSubscriptionTopic topic = WebhookSubscriptionTopic.valueOf(notification.getTopic());
            
            // Find and execute handler
            Consumer<WebhookNotification> handler = handlers.get(topic);
            if (handler != null) {
                handler.accept(notification);
                log.info("Successfully processed webhook for topic: {}", topic);
                return true;
            } else {
                log.warn("No handler registered for webhook topic: {}", topic);
                return false;
            }
            
        } catch (Exception e) {
            log.error("Error processing webhook", e);
            return false;
        }
    }
}