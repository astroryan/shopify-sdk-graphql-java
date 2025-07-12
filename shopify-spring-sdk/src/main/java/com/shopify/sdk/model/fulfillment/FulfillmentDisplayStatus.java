package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The display status of a fulfillment.
 */
public enum FulfillmentDisplayStatus {
    @JsonProperty("ATTEMPTED_DELIVERY")
    ATTEMPTED_DELIVERY,
    
    @JsonProperty("CANCELED")
    CANCELED,
    
    @JsonProperty("CONFIRMED")
    CONFIRMED,
    
    @JsonProperty("DELIVERED")
    DELIVERED,
    
    @JsonProperty("FAILURE")
    FAILURE,
    
    @JsonProperty("FULFILLED")
    FULFILLED,
    
    @JsonProperty("IN_TRANSIT")
    IN_TRANSIT,
    
    @JsonProperty("LABEL_PRINTED")
    LABEL_PRINTED,
    
    @JsonProperty("LABEL_PURCHASED")
    LABEL_PURCHASED,
    
    @JsonProperty("LABEL_VOIDED")
    LABEL_VOIDED,
    
    @JsonProperty("MARKED_AS_FULFILLED")
    MARKED_AS_FULFILLED,
    
    @JsonProperty("NOT_DELIVERED")
    NOT_DELIVERED,
    
    @JsonProperty("OUT_FOR_DELIVERY")
    OUT_FOR_DELIVERY,
    
    @JsonProperty("PICKED_UP")
    PICKED_UP,
    
    @JsonProperty("READY_FOR_PICKUP")
    READY_FOR_PICKUP,
    
    @JsonProperty("SUBMITTED")
    SUBMITTED
}