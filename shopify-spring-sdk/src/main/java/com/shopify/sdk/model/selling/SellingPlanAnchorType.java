package com.shopify.sdk.model.selling;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type of anchor for a selling plan.
 */
public enum SellingPlanAnchorType {
    @JsonProperty("WEEKDAY")
    WEEKDAY,
    
    @JsonProperty("MONTHDAY")
    MONTHDAY,
    
    @JsonProperty("YEARDAY")
    YEARDAY
}