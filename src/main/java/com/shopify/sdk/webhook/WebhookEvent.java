package com.shopify.sdk.webhook;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

/**
 * Represents a webhook event received from Shopify.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebhookEvent {
    
    /**
     * The unique identifier for this webhook event.
     */
    private String id;
    
    /**
     * The webhook topic/event type.
     */
    private String topic;
    
    /**
     * The webhook event type enum.
     */
    private WebhookEventType eventType;
    
    /**
     * The shop domain that sent the webhook.
     */
    private String shop;
    
    /**
     * The raw request body as received from Shopify.
     */
    private String rawBody;
    
    /**
     * The parsed JSON payload.
     */
    private JsonNode payload;
    
    /**
     * The webhook headers.
     */
    private Map<String, String> headers;
    
    /**
     * The HMAC signature from the X-Shopify-Hmac-Sha256 header.
     */
    private String hmacSignature;
    
    /**
     * The API version used for this webhook.
     */
    private String apiVersion;
    
    /**
     * When the webhook was received.
     */
    private Instant receivedAt;
    
    /**
     * When the event occurred on Shopify (if available in payload).
     */
    private Instant occurredAt;
    
    /**
     * Whether the webhook signature was verified.
     */
    private Boolean verified;
    
    /**
     * Any processing errors that occurred.
     */
    private String error;
    
    /**
     * Additional metadata.
     */
    private Map<String, Object> metadata;
    
    /**
     * Whether this webhook has been processed.
     */
    private Boolean processed;
    
    /**
     * When the webhook was processed.
     */
    private Instant processedAt;
    
    /**
     * Gets a specific header value.
     *
     * @param headerName the header name
     * @return the header value or null if not found
     */
    public String getHeader(String headerName) {
        return headers != null ? headers.get(headerName) : null;
    }
    
    /**
     * Gets the Shopify topic header.
     *
     * @return the X-Shopify-Topic header value
     */
    public String getShopifyTopic() {
        return getHeader("X-Shopify-Topic");
    }
    
    /**
     * Gets the Shopify shop domain header.
     *
     * @return the X-Shopify-Shop-Domain header value
     */
    public String getShopifyShopDomain() {
        return getHeader("X-Shopify-Shop-Domain");
    }
    
    /**
     * Gets the Shopify API version header.
     *
     * @return the X-Shopify-API-Version header value
     */
    public String getShopifyApiVersion() {
        return getHeader("X-Shopify-API-Version");
    }
    
    /**
     * Gets the webhook ID header.
     *
     * @return the X-Shopify-Webhook-Id header value
     */
    public String getShopifyWebhookId() {
        return getHeader("X-Shopify-Webhook-Id");
    }
    
    /**
     * Gets the request ID header for tracking.
     *
     * @return the X-Request-Id header value
     */
    public String getRequestId() {
        return getHeader("X-Request-Id");
    }
    
    /**
     * Extracts a field from the JSON payload.
     *
     * @param fieldPath the JSON path (e.g., "order.id")
     * @return the field value as JsonNode or null if not found
     */
    public JsonNode getPayloadField(String fieldPath) {
        if (payload == null || fieldPath == null) {
            return null;
        }
        
        String[] pathParts = fieldPath.split("\\.");
        JsonNode current = payload;
        
        for (String part : pathParts) {
            if (current == null || !current.has(part)) {
                return null;
            }
            current = current.get(part);
        }
        
        return current;
    }
    
    /**
     * Extracts a field from the JSON payload as a string.
     *
     * @param fieldPath the JSON path
     * @return the field value as string or null if not found
     */
    public String getPayloadFieldAsString(String fieldPath) {
        JsonNode field = getPayloadField(fieldPath);
        return field != null && !field.isNull() ? field.asText() : null;
    }
    
    /**
     * Extracts a field from the JSON payload as a long.
     *
     * @param fieldPath the JSON path
     * @return the field value as long or null if not found
     */
    public Long getPayloadFieldAsLong(String fieldPath) {
        JsonNode field = getPayloadField(fieldPath);
        return field != null && !field.isNull() && field.isNumber() ? field.asLong() : null;
    }
    
    /**
     * Gets the entity ID from the payload (works for most Shopify resources).
     *
     * @return the entity ID or null if not found
     */
    public Long getEntityId() {
        // Try common patterns for entity ID
        JsonNode idField = getPayloadField("id");
        if (idField == null) {
            // Try nested patterns like order.id, product.id, etc.
            String[] commonEntities = {"order", "product", "customer", "collection", "cart", "checkout"};
            for (String entity : commonEntities) {
                idField = getPayloadField(entity + ".id");
                if (idField != null) {
                    break;
                }
            }
        }
        
        return idField != null && !idField.isNull() && idField.isNumber() ? idField.asLong() : null;
    }
    
    /**
     * Marks this webhook as processed.
     */
    public void markAsProcessed() {
        this.processed = true;
        this.processedAt = Instant.now();
    }
    
    /**
     * Marks this webhook as failed with an error.
     *
     * @param error the error message
     */
    public void markAsFailed(String error) {
        this.processed = false;
        this.error = error;
        this.processedAt = Instant.now();
    }
    
    /**
     * Sets metadata value.
     *
     * @param key the metadata key
     * @param value the metadata value
     */
    public void setMetadata(String key, Object value) {
        if (metadata == null) {
            metadata = new java.util.HashMap<>();
        }
        metadata.put(key, value);
    }
    
    /**
     * Gets metadata value.
     *
     * @param key the metadata key
     * @return the metadata value or null if not found
     */
    public Object getMetadata(String key) {
        return metadata != null ? metadata.get(key) : null;
    }
}