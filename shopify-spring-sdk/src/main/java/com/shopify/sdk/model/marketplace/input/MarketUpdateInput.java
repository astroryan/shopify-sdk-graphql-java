package com.shopify.sdk.model.marketplace.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for updating a market.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketUpdateInput {
    /**
     * The name of the market
     */
    private String name;
    
    /**
     * The handle for the market
     */
    private String handle;
    
    /**
     * Whether the market is enabled
     */
    private Boolean enabled;
    
    /**
     * The regions to add to the market
     */
    private List<String> addRegionIds;
    
    /**
     * The regions to remove from the market
     */
    private List<String> removeRegionIds;
}