package com.shopify.sdk.webhook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.model.webhook.WebhookNotification;
import com.shopify.sdk.model.webhook.WebhookSubscriptionTopic;
import com.shopify.sdk.service.WebhookService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

/**
 * Spring controller for receiving webhooks
 * This is an example implementation - developers can customize this for their needs
 */
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

/**
 * Typed webhook handlers for common webhook types
 */
@Component
public class TypedWebhookHandlers {
    
    private final ObjectMapper objectMapper;
    
    public TypedWebhookHandlers(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    /**
     * Base class for typed webhook handlers
     */
    public abstract class TypedWebhookHandler<T> implements Consumer<WebhookNotification> {
        
        private final Class<T> dataClass;
        
        protected TypedWebhookHandler(Class<T> dataClass) {
            this.dataClass = dataClass;
        }
        
        @Override
        public void accept(WebhookNotification notification) {
            try {
                T data = objectMapper.convertValue(notification.getData(), dataClass);
                handleWebhook(data);
            } catch (Exception e) {
                log.error("Error converting webhook data to type: {}", dataClass.getName(), e);
            }
        }
        
        protected abstract void handleWebhook(T data);
    }
    
    // Example typed handlers
    public class OrderCreatedHandler extends TypedWebhookHandler<OrderWebhookData> {
        public OrderCreatedHandler() {
            super(OrderWebhookData.class);
        }
        
        @Override
        protected void handleWebhook(OrderWebhookData data) {
            // Handle order created webhook with typed data
            log.info("Processing order: {}", data.getId());
        }
    }
    
    public class ProductUpdatedHandler extends TypedWebhookHandler<ProductWebhookData> {
        public ProductUpdatedHandler() {
            super(ProductWebhookData.class);
        }
        
        @Override
        protected void handleWebhook(ProductWebhookData data) {
            // Handle product updated webhook with typed data
            log.info("Processing product update: {}", data.getId());
        }
    }
    
    // Webhook data classes
    @Data
    public static class OrderWebhookData {
        private String id;
        private String name;
        private String email;
        private String totalPrice;
        private List<LineItemWebhookData> lineItems;
    }
    
    @Data
    public static class ProductWebhookData {
        private String id;
        private String title;
        private String vendor;
        private String productType;
        private List<VariantWebhookData> variants;
    }
    
    @Data
    public static class LineItemWebhookData {
        private String id;
        private String title;
        private Integer quantity;
        private String price;
    }
    
    @Data
    public static class VariantWebhookData {
        private String id;
        private String title;
        private String price;
        private String sku;
        private Integer inventoryQuantity;
    }
}