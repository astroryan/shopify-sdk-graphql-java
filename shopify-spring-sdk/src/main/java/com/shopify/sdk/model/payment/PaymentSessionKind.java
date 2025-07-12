package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The kind of payment session.
 */
public enum PaymentSessionKind {
    @JsonProperty("SALE")
    SALE,
    
    @JsonProperty("AUTHORIZATION")
    AUTHORIZATION,
    
    @JsonProperty("CAPTURE")
    CAPTURE,
    
    @JsonProperty("REFUND")
    REFUND,
    
    @JsonProperty("VOID")
    VOID
}