package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The reason for a fulfillment hold.
 */
public enum FulfillmentHoldReason {
    @JsonProperty("AWAITING_PAYMENT")
    AWAITING_PAYMENT,
    
    @JsonProperty("HIGH_RISK_OF_FRAUD")
    HIGH_RISK_OF_FRAUD,
    
    @JsonProperty("INCORRECT_ADDRESS")
    INCORRECT_ADDRESS,
    
    @JsonProperty("INVENTORY_OUT_OF_STOCK")
    INVENTORY_OUT_OF_STOCK,
    
    @JsonProperty("OTHER")
    OTHER
}