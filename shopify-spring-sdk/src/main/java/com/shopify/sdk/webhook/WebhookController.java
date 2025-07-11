package com.shopify.sdk.webhook;

import com.shopify.sdk.model.webhook.WebhookSubscriptionTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Spring controller for receiving webhooks
 * This is an example implementation - developers can customize this for their needs
 */
@Slf4j
@Component
class WebhookController {
    
    private final WebhookHandler webhookHandler;
    
    public WebhookController(WebhookHandler webhookHandler) {
        this.webhookHandler = webhookHandler;
        
        // Register handlers for different topics
        registerHandlers();
    }
    
    private void registerHandlers() {
        // Example: Order created handler
        webhookHandler.registerHandler(WebhookSubscriptionTopic.ORDERS_CREATE, notification -> {
            log.info("Order created: {}", notification.getData());
            // Process order creation
        });
        
        // Example: Product updated handler
        webhookHandler.registerHandler(WebhookSubscriptionTopic.PRODUCTS_UPDATE, notification -> {
            log.info("Product updated: {}", notification.getData());
            // Process product update
        });
        
        // Example: Customer created handler
        webhookHandler.registerHandler(WebhookSubscriptionTopic.CUSTOMERS_CREATE, notification -> {
            log.info("Customer created: {}", notification.getData());
            // Process customer creation
        });
        
        // Example: Inventory level updated handler
        webhookHandler.registerHandler(WebhookSubscriptionTopic.INVENTORY_LEVELS_UPDATE, notification -> {
            log.info("Inventory level updated: {}", notification.getData());
            // Process inventory update
        });
        
        // Example: Fulfillment created handler
        webhookHandler.registerHandler(WebhookSubscriptionTopic.FULFILLMENTS_CREATE, notification -> {
            log.info("Fulfillment created: {}", notification.getData());
            // Process fulfillment creation
        });
    }
}