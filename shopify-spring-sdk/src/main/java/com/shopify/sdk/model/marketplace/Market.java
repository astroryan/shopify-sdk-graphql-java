package com.shopify.sdk.model.marketplace;

import com.shopify.sdk.model.common.Node;
import com.shopify.sdk.model.common.CurrencyCode;
import com.shopify.sdk.model.store.MarketRegion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a market.
 * A market is a group of one or more regions that you want to target for international sales.
 * By creating a market, you can configure a distinct, localized shopping experience for
 * customers from a specific area of the world.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Market implements Node {
    /**
     * A globally-unique identifier for the market
     */
    private String id;
    
    /**
     * The name of the market
     */
    private String name;
    
    /**
     * The handle of the market
     */
    private String handle;
    
    /**
     * Whether the market is enabled
     */
    private Boolean enabled;
    
    /**
     * Whether the market is the primary market
     */
    private Boolean primary;
    
    /**
     * The regions in the market
     */
    private List<MarketRegion> regions;
    
    /**
     * The market's currency settings
     */
    private MarketCurrencySettings currencySettings;
    
    /**
     * The localization settings for the market
     */
    private MarketLocalizationSettings localizationSettings;
    
    /**
     * The price lists for the market
     */
    private PriceListConnection priceLists;
    
    /**
     * The catalogs associated with the market
     */
    private CatalogConnection catalogs;
}