package com.shopify.sdk.model.market;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for creating a market region.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketRegionCreateInput {
    /**
     * The market ID.
     */
    @JsonProperty("marketId")
    private String marketId;
    
    /**
     * The countries to add.
     */
    @JsonProperty("countries")
    private List<String> countries;
}