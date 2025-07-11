package com.shopify.sdk.webhook;

import com.shopify.sdk.model.webhook.WebhookNotification;
import com.shopify.sdk.model.webhook.WebhookSubscriptionTopic;

import java.util.function.Consumer;

/**
 * Interface for handling Shopify webhooks
 */
public interface WebhookHandler {
    
    /**
     * Register a handler for a specific webhook topic
     * @param topic The webhook topic to handle
     * @param handler The handler function that processes the webhook data
     */
    void registerHandler(WebhookSubscriptionTopic topic, Consumer<WebhookNotification> handler);
    
    /**
     * Process an incoming webhook
     * @param hmacHeader The HMAC header from the webhook request
     * @param rawBody The raw request body
     * @param secret The webhook secret for verification
     * @return true if the webhook was processed successfully, false otherwise
     */
    boolean processWebhook(String hmacHeader, String rawBody, String secret);
}