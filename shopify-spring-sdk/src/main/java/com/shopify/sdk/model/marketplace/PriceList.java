package com.shopify.sdk.model.marketplace;

import com.shopify.sdk.model.common.Node;
import com.shopify.sdk.model.common.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a price list.
 * A price list allows you to set different prices for products in different contexts.
 * For example, you can create a price list for wholesale customers or for customers in
 * a specific country.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceList implements Node {
    /**
     * A globally-unique identifier for the price list
     */
    private String id;
    
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
     * The adjustment value for the price list
     */
    private PriceListAdjustmentValue adjustmentValue;
    
    /**
     * The prices in the price list
     */
    private PriceListPriceConnection prices;
    
    /**
     * The catalogs associated with the price list
     */
    private CatalogConnection catalogs;
    
    /**
     * The markets associated with the price list
     */
    private MarketConnection markets;
}