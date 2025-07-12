package com.shopify.sdk.model.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a discount code applied to a cart.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDiscountCode {
    /**
     * The code.
     */
    @JsonProperty("code")
    private String code;
    
    /**
     * Whether the discount code is applicable.
     */
    @JsonProperty("applicable")
    private Boolean applicable;
}