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
 * Input for updating a price list.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceListUpdateInput {
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
     * The catalog IDs to add to the price list
     */
    private List<String> addCatalogIds;
    
    /**
     * The catalog IDs to remove from the price list
     */
    private List<String> removeCatalogIds;
    
    /**
     * The market IDs to add to the price list
     */
    private List<String> addMarketIds;
    
    /**
     * The market IDs to remove from the price list
     */
    private List<String> removeMarketIds;
}