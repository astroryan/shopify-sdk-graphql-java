package com.shopify.sdk.model.shop;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type of shop policy.
 */
public enum ShopPolicyType {
    @JsonProperty("REFUND_POLICY")
    REFUND_POLICY,
    
    @JsonProperty("SHIPPING_POLICY")
    SHIPPING_POLICY,
    
    @JsonProperty("PRIVACY_POLICY")
    PRIVACY_POLICY,
    
    @JsonProperty("TERMS_OF_SERVICE")
    TERMS_OF_SERVICE,
    
    @JsonProperty("TERMS_OF_SALE")
    TERMS_OF_SALE,
    
    @JsonProperty("LEGAL_NOTICE")
    LEGAL_NOTICE,
    
    @JsonProperty("SUBSCRIPTION_POLICY")
    SUBSCRIPTION_POLICY
}