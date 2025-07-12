package com.shopify.sdk.model.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.MoneyBag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a delivery option for a cart.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDeliveryOption {
    /**
     * The handle of the delivery option.
     */
    @JsonProperty("handle")
    private String handle;
    
    /**
     * The title of the delivery option.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The estimated cost of the delivery option.
     */
    @JsonProperty("estimatedCost")
    private MoneyBag estimatedCost;
}