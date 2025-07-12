package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The interval for Shopify Payments payouts.
 */
public enum ShopifyPaymentsPayoutInterval {
    @JsonProperty("DAILY")
    DAILY,
    
    @JsonProperty("WEEKLY")
    WEEKLY,
    
    @JsonProperty("MONTHLY")
    MONTHLY,
    
    @JsonProperty("MANUAL")
    MANUAL
}