package com.shopify.sdk.model.webhook;

import com.shopify.sdk.model.common.ID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Represents a webhook subscription in Shopify.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebhookSubscription {
    private ID id;
    private String topic;
    private String callbackUrl;
    private String format;
    private Instant createdAt;
    private Instant updatedAt;
    private String apiVersion;
    private String metafieldNamespaces;
    private String privateMetafieldNamespaces;
    private Boolean includeFields;
}