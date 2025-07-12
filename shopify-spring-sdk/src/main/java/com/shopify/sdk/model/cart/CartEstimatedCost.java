package com.shopify.sdk.model.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.MoneyBag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the estimated costs for a cart.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartEstimatedCost {
    /**
     * The subtotal amount.
     */
    @JsonProperty("subtotalAmount")
    private MoneyBag subtotalAmount;
    
    /**
     * The total amount.
     */
    @JsonProperty("totalAmount")
    private MoneyBag totalAmount;
    
    /**
     * The total duty amount.
     */
    @JsonProperty("totalDutyAmount")
    private MoneyBag totalDutyAmount;
    
    /**
     * The total tax amount.
     */
    @JsonProperty("totalTaxAmount")
    private MoneyBag totalTaxAmount;
}