package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The status of a fulfillment order.
 */
public enum FulfillmentOrderStatus {
    @JsonProperty("CANCELLED")
    CANCELLED,
    
    @JsonProperty("CLOSED")
    CLOSED,
    
    @JsonProperty("IN_PROGRESS")
    IN_PROGRESS,
    
    @JsonProperty("ON_HOLD")
    ON_HOLD,
    
    @JsonProperty("OPEN")
    OPEN,
    
    @JsonProperty("SCHEDULED")
    SCHEDULED
}