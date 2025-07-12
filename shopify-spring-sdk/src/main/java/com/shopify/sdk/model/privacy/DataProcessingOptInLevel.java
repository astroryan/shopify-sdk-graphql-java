package com.shopify.sdk.model.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The level of data processing opt-in.
 */
public enum DataProcessingOptInLevel {
    @JsonProperty("ACCEPTED")
    ACCEPTED,
    
    @JsonProperty("DECLINED")
    DECLINED,
    
    @JsonProperty("PENDING")
    PENDING
}