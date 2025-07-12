package com.shopify.sdk.model.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The level of marketing consent.
 */
public enum MarketingConsentLevel {
    @JsonProperty("SINGLE_OPT_IN")
    SINGLE_OPT_IN,
    
    @JsonProperty("CONFIRMED_OPT_IN")
    CONFIRMED_OPT_IN,
    
    @JsonProperty("UNSUBSCRIBED")
    UNSUBSCRIBED
}