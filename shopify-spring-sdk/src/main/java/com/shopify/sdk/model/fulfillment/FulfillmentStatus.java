package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The status of a fulfillment.
 */
public enum FulfillmentStatus {
    @JsonProperty("PENDING")
    PENDING,
    
    @JsonProperty("OPEN")
    OPEN,
    
    @JsonProperty("SUCCESS")
    SUCCESS,
    
    @JsonProperty("CANCELLED")
    CANCELLED,
    
    @JsonProperty("ERROR")
    ERROR,
    
    @JsonProperty("FAILURE")
    FAILURE
}