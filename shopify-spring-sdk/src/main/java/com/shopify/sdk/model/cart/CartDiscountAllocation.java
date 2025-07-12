package com.shopify.sdk.model.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.MoneyBag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a discount allocation for a cart line.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDiscountAllocation {
    /**
     * The discount amount.
     */
    @JsonProperty("discountedAmount")
    private MoneyBag discountedAmount;
}