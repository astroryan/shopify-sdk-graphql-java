package com.shopify.sdk.model.market;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for updating a market region.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketRegionUpdateInput {
    /**
     * The ID of the region to update.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The countries to add.
     */
    @JsonProperty("addCountries")
    private List<String> addCountries;
    
    /**
     * The countries to remove.
     */
    @JsonProperty("removeCountries")
    private List<String> removeCountries;
}