package com.shopify.sdk.model.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for creating an EventBridge webhook subscription
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventBridgeWebhookSubscriptionCreateInput {
    /**
     * The ARN of the EventBridge endpoint
     */
    @JsonProperty("arn")
    private String arn;
    
    /**
     * The format in which the webhook subscription should send data
     */
    @JsonProperty("format")
    private WebhookSubscriptionFormat format;
    
    /**
     * The list of fields to be included in the webhook
     */
    @JsonProperty("includeFields")
    private List<String> includeFields;
    
    /**
     * The list of namespaces for any metafields that should be included
     */
    @JsonProperty("metafieldNamespaces")
    private List<String> metafieldNamespaces;
}