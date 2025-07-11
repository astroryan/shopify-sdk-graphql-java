package com.shopify.sdk.model.marketplace.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for creating a market.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketCreateInput {
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
     * The regions to include in the market
     */
    private List<String> regionIds;
}