package com.shopify.sdk.model.market;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import com.shopify.sdk.model.common.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a region within a market.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketRegion implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The name of the region.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The country in this region.
     */
    @JsonProperty("country")
    private Country country;
}