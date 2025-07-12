package com.shopify.sdk.model.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a connection to webhook subscriptions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebhookSubscriptionConnection {
    /**
     * A list of edges containing the nodes
     */
    @JsonProperty("edges")
    private List<WebhookSubscriptionEdge> edges;
    
    /**
     * A list of the nodes contained in WebhookSubscriptionEdge
     */
    @JsonProperty("nodes")
    private List<WebhookSubscription> nodes;
    
    /**
     * Information to aid in pagination
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

/**
 * An edge in a connection to a WebhookSubscription
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class WebhookSubscriptionEdge {
    /**
     * A cursor for use in pagination
     */
    @JsonProperty("cursor")
    private String cursor;
    
    /**
     * The item at the end of WebhookSubscriptionEdge
     */
    @JsonProperty("node")
    private WebhookSubscription node;
}