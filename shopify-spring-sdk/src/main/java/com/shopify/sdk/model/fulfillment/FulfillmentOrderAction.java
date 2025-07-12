package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The action that can be taken on a fulfillment order.
 */
public enum FulfillmentOrderAction {
    @JsonProperty("CREATE_FULFILLMENT")
    CREATE_FULFILLMENT,
    
    @JsonProperty("REQUEST_FULFILLMENT")
    REQUEST_FULFILLMENT,
    
    @JsonProperty("CANCEL_FULFILLMENT_ORDER")
    CANCEL_FULFILLMENT_ORDER,
    
    @JsonProperty("MOVE")
    MOVE,
    
    @JsonProperty("REQUEST_CANCELLATION")
    REQUEST_CANCELLATION,
    
    @JsonProperty("MARK_AS_OPEN")
    MARK_AS_OPEN,
    
    @JsonProperty("RELEASE_HOLD")
    RELEASE_HOLD,
    
    @JsonProperty("HOLD")
    HOLD,
    
    @JsonProperty("EXTERNAL")
    EXTERNAL
}