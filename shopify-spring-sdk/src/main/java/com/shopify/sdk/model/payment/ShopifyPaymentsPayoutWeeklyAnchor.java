package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The weekly anchor day for payouts.
 */
public enum ShopifyPaymentsPayoutWeeklyAnchor {
    @JsonProperty("MONDAY")
    MONDAY,
    
    @JsonProperty("TUESDAY")
    TUESDAY,
    
    @JsonProperty("WEDNESDAY")
    WEDNESDAY,
    
    @JsonProperty("THURSDAY")
    THURSDAY,
    
    @JsonProperty("FRIDAY")
    FRIDAY,
    
    @JsonProperty("SATURDAY")
    SATURDAY,
    
    @JsonProperty("SUNDAY")
    SUNDAY
}