package com.shopify.sdk.model.selling;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The interval for a selling plan.
 */
public enum SellingPlanInterval {
    @JsonProperty("DAY")
    DAY,
    
    @JsonProperty("WEEK")
    WEEK,
    
    @JsonProperty("MONTH")
    MONTH,
    
    @JsonProperty("YEAR")
    YEAR
}