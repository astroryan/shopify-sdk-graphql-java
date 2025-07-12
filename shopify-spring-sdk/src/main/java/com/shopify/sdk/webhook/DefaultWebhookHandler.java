package com.shopify.sdk.webhook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Default webhook handler that logs all webhook events.
 * This handler has the lowest priority and will only be used if no other handlers are available.
 */
@Slf4j
@Component
@ConditionalOnMissingBean(name = "customWebhookHandler")
public class DefaultWebhookHandler implements WebhookHandler {
    
    @Override
    public boolean canHandle(WebhookEvent event) {
        // Handle all events as fallback
        return true;
    }
    
    @Override
    public Mono<Void> handle(WebhookEvent event) {
        return Mono.fromRunnable(() -> {
            log.info("Received webhook: {} from shop: {} with ID: {}", 
                event.getTopic(), 
                event.getShop(), 
                event.getId());
            
            if (log.isDebugEnabled()) {
                log.debug("Webhook headers: {}", event.getHeaders());
                log.debug("Webhook payload: {}", event.getRawBody());
                
                // Log specific event details
                logEventSpecificDetails(event);
            }
        });
    }
    
    @Override
    public int getPriority() {
        // Lowest priority to ensure this is only used as fallback
        return Integer.MAX_VALUE;
    }
    
    @Override
    public Mono<Void> onError(WebhookEvent event, Throwable error) {
        return Mono.fromRunnable(() -> 
            log.error("Error in default webhook handler for event {} from shop {}: {}", 
                event.getTopic(), event.getShop(), error.getMessage(), error));
    }
    
    private void logEventSpecificDetails(WebhookEvent event) {
        if (event.getEventType() == null) {
            return;
        }
        
        try {
            switch (event.getEventType()) {
                case ORDERS_CREATE, ORDERS_UPDATED, ORDERS_PAID, ORDERS_CANCELLED -> 
                    logOrderEvent(event);
                case PRODUCTS_CREATE, PRODUCTS_UPDATE, PRODUCTS_DELETE -> 
                    logProductEvent(event);
                case CUSTOMERS_CREATE, CUSTOMERS_UPDATE, CUSTOMERS_DELETE -> 
                    logCustomerEvent(event);
                case APP_UNINSTALLED -> 
                    logAppUninstallEvent(event);
                default -> 
                    log.debug("Received {} event", event.getEventType());
            }
        } catch (Exception e) {
            log.warn("Failed to log event-specific details for {}: {}", event.getTopic(), e.getMessage());
        }
    }
    
    private void logOrderEvent(WebhookEvent event) {
        Long orderId = event.getPayloadFieldAsLong("id");
        String orderNumber = event.getPayloadFieldAsString("order_number");
        String financialStatus = event.getPayloadFieldAsString("financial_status");
        String fulfillmentStatus = event.getPayloadFieldAsString("fulfillment_status");
        
        log.debug("Order event - ID: {}, Number: {}, Financial Status: {}, Fulfillment Status: {}", 
            orderId, orderNumber, financialStatus, fulfillmentStatus);
    }
    
    private void logProductEvent(WebhookEvent event) {
        Long productId = event.getPayloadFieldAsLong("id");
        String title = event.getPayloadFieldAsString("title");
        String vendor = event.getPayloadFieldAsString("vendor");
        String productType = event.getPayloadFieldAsString("product_type");
        
        log.debug("Product event - ID: {}, Title: {}, Vendor: {}, Type: {}", 
            productId, title, vendor, productType);
    }
    
    private void logCustomerEvent(WebhookEvent event) {
        Long customerId = event.getPayloadFieldAsLong("id");
        String email = event.getPayloadFieldAsString("email");
        String firstName = event.getPayloadFieldAsString("first_name");
        String lastName = event.getPayloadFieldAsString("last_name");
        
        log.debug("Customer event - ID: {}, Email: {}, Name: {} {}", 
            customerId, email, firstName, lastName);
    }
    
    private void logAppUninstallEvent(WebhookEvent event) {
        String domain = event.getPayloadFieldAsString("domain");
        log.info("App uninstalled from shop: {}", domain);
    }
}