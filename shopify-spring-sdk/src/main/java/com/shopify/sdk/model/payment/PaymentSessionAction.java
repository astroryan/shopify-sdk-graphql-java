package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The action to be taken for a payment session.
 */
public enum PaymentSessionAction {
    @JsonProperty("REDIRECT")
    REDIRECT,
    
    @JsonProperty("NONE")
    NONE,
    
    @JsonProperty("AUTHORIZE")
    AUTHORIZE,
    
    @JsonProperty("CAPTURE")
    CAPTURE
}