package com.shopify.sdk.model.marketplace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The adjustment value for a price list.
 * Can be either a percentage or a fixed amount adjustment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceListAdjustmentValue {
    /**
     * The percentage adjustment (if adjustment type is PERCENTAGE_DECREASE or PERCENTAGE_INCREASE)
     */
    private BigDecimal percentage;
    
    /**
     * The fixed amount adjustment (if adjustment type is FIXED_AMOUNT)
     */
    private BigDecimal fixedAmount;
}