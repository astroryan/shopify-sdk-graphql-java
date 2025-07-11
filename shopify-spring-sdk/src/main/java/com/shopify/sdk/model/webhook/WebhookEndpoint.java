package com.shopify.sdk.model.webhook;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

/**
 * Base interface for webhook endpoints
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "__typename"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = WebhookHttpEndpoint.class, name = "WebhookHttpEndpoint"),
    @JsonSubTypes.Type(value = WebhookEventBridgeEndpoint.class, name = "WebhookEventBridgeEndpoint"),
    @JsonSubTypes.Type(value = WebhookPubSubEndpoint.class, name = "WebhookPubSubEndpoint")
})
public interface WebhookEndpoint {
    // Marker interface for different webhook endpoint types
}

/**
 * HTTP webhook endpoint
 */
@Data
class WebhookHttpEndpoint implements WebhookEndpoint {
    private String callbackUrl;
}

/**
 * AWS EventBridge webhook endpoint
 */
@Data
class WebhookEventBridgeEndpoint implements WebhookEndpoint {
    private String arn;
}

/**
 * Google Pub/Sub webhook endpoint
 */
@Data
class WebhookPubSubEndpoint implements WebhookEndpoint {
    private String pubSubProject;
    private String pubSubTopic;
}