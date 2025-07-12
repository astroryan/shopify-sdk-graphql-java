package com.shopify.sdk.model.shop;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The branding level for a shop.
 */
public enum ShopBrandingLevel {
    @JsonProperty("SHOPIFY_PLUS")
    SHOPIFY_PLUS,
    
    @JsonProperty("SHOPIFY_GOLD")
    SHOPIFY_GOLD,
    
    @JsonProperty("ROGERS")
    ROGERS,
    
    @JsonProperty("ROGERS_BASIC")
    ROGERS_BASIC
}