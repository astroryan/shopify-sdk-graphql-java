package com.shopify.sdk.webhook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.model.webhook.WebhookNotification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

/**
 * Typed webhook handlers for common webhook types
 */
@Slf4j
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