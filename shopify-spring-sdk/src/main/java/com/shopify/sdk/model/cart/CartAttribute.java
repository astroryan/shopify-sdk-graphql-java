package com.shopify.sdk.model.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an attribute of a cart.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartAttribute {
    /**
     * The key of the attribute.
     */
    @JsonProperty("key")
    private String key;
    
    /**
     * The value of the attribute.
     */
    @JsonProperty("value")
    private String value;
}