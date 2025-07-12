package com.shopify.sdk.model.market;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a market.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Market implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The name of the market.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The handle of the market.
     */
    @JsonProperty("handle")
    private String handle;
    
    /**
     * Whether the market is enabled.
     */
    @JsonProperty("enabled")
    private Boolean enabled;
    
    /**
     * Whether this is the primary market.
     */
    @JsonProperty("primary")
    private Boolean primary;
    
    /**
     * The regions in the market.
     */
    @JsonProperty("regions")
    private MarketRegionConnection regions;
    
    /**
     * The currency settings for the market.
     */
    @JsonProperty("currencySettings")
    private MarketCurrencySettings currencySettings;
}