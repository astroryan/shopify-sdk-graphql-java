package com.shopify.sdk.model.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.DateTime;
import com.shopify.sdk.model.common.ID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a webhook subscription
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebhookSubscription {
    /**
     * The ID of the webhook subscription
     */
    @JsonProperty("id")
    private ID id;
    
    /**
     * The API version for the webhook
     */
    @JsonProperty("apiVersion")
    private ApiVersion apiVersion;
    
    /**
     * The callback URL for HTTP endpoints
     */
    @JsonProperty("callbackUrl")
    private String callbackUrl;
    
    /**
     * When the webhook subscription was created
     */
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    /**
     * When the webhook subscription was last updated
     */
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    /**
     * The webhook endpoint configuration
     */
    @JsonProperty("endpoint")
    private WebhookEndpoint endpoint;
    
    /**
     * The format in which the webhook subscription should send data
     */
    @JsonProperty("format")
    private WebhookSubscriptionFormat format;
    
    /**
     * The list of fields to be included in the webhook subscription
     */
    @JsonProperty("includeFields")
    private List<String> includeFields;
    
    /**
     * The list of namespaces for any metafields that should be included
     */
    @JsonProperty("metafieldNamespaces")
    private List<String> metafieldNamespaces;
    
    /**
     * The type of event that triggers the webhook
     */
    @JsonProperty("topic")
    private WebhookSubscriptionTopic topic;
}