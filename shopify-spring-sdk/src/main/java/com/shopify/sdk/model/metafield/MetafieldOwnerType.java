package com.shopify.sdk.model.metafield;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type of resource that owns a metafield.
 */
public enum MetafieldOwnerType {
    @JsonProperty("ARTICLE")
    ARTICLE,
    
    @JsonProperty("BLOG")
    BLOG,
    
    @JsonProperty("COLLECTION")
    COLLECTION,
    
    @JsonProperty("CUSTOMER")
    CUSTOMER,
    
    @JsonProperty("DISCOUNT")
    DISCOUNT,
    
    @JsonProperty("DRAFTORDER")
    DRAFTORDER,
    
    @JsonProperty("LOCATION")
    LOCATION,
    
    @JsonProperty("ORDER")
    ORDER,
    
    @JsonProperty("PAGE")
    PAGE,
    
    @JsonProperty("PRODUCT")
    PRODUCT,
    
    @JsonProperty("PRODUCTIMAGE")
    PRODUCTIMAGE,
    
    @JsonProperty("PRODUCTVARIANT")
    PRODUCTVARIANT,
    
    @JsonProperty("SHOP")
    SHOP
}