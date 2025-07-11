package com.shopify.sdk.model.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Represents a webhook notification from Shopify
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebhookNotification {
    /**
     * The webhook topic (e.g., "orders/create", "products/update")
     */
    @JsonProperty("topic")
    private String topic;
    
    /**
     * The shop domain that sent the webhook
     */
    @JsonProperty("domain")
    private String domain;
    
    /**
     * The webhook data payload
     */
    @JsonProperty("data")
    private Map<String, Object> data;
    
    /**
     * When the event occurred
     */
    @JsonProperty("created_at")
    private OffsetDateTime createdAt;
    
    /**
     * The API version used for this webhook
     */
    @JsonProperty("api_version")
    private String apiVersion;
    
    /**
     * Get the webhook topic as an enum
     * @return The WebhookSubscriptionTopic enum value
     * @throws IllegalArgumentException if the topic is not recognized
     */
    public WebhookSubscriptionTopic getTopicEnum() {
        return WebhookSubscriptionTopic.fromValue(topic);
    }
    
    /**
     * Get a specific field from the data payload
     * @param key The field key
     * @return The field value or null
     */
    public Object getDataField(String key) {
        return data != null ? data.get(key) : null;
    }
    
    /**
     * Get a specific field from the data payload as a specific type
     * @param key The field key
     * @param type The expected type
     * @param <T> The type parameter
     * @return The field value cast to the specified type or null
     */
    @SuppressWarnings("unchecked")
    public <T> T getDataField(String key, Class<T> type) {
        Object value = getDataField(key);
        if (value != null && type.isAssignableFrom(value.getClass())) {
            return (T) value;
        }
        return null;
    }
}