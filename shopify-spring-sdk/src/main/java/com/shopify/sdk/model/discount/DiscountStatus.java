package com.shopify.sdk.model.discount;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The status of a discount.
 */
public enum DiscountStatus {
    @JsonProperty("ACTIVE")
    ACTIVE,
    
    @JsonProperty("EXPIRED")
    EXPIRED,
    
    @JsonProperty("SCHEDULED")
    SCHEDULED
}