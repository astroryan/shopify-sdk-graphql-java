package com.shopify.sdk.model.marketplace.input;

import com.shopify.sdk.model.common.CurrencyCode;
import com.shopify.sdk.model.marketplace.PriceListAdjustmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Input for creating a price list.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceListCreateInput {
    /**
     * The name of the price list
     */
    private String name;
    
    /**
     * The currency of the price list
     */
    private CurrencyCode currency;
    
    /**
     * Whether the price list is active
     */
    private Boolean active;
    
    /**
     * The adjustment type for the price list
     */
    private PriceListAdjustmentType adjustmentType;
    
    /**
     * The adjustment percentage (for percentage-based adjustments)
     */
    private BigDecimal adjustmentPercentage;
    
    /**
     * The adjustment amount (for fixed amount adjustments)
     */
    private BigDecimal adjustmentAmount;
    
    /**
     * The catalog IDs to associate with the price list
     */
    private List<String> catalogIds;
    
    /**
     * The market IDs to associate with the price list
     */
    private List<String> marketIds;
}