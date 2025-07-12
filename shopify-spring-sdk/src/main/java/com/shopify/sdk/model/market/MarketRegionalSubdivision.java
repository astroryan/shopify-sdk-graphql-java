package com.shopify.sdk.model.market;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a regional subdivision for a market.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketRegionalSubdivision {
    /**
     * The ID of the regional subdivision.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The code of the regional subdivision.
     */
    @JsonProperty("code")
    private String code;
    
    /**
     * The name of the regional subdivision.
     */
    @JsonProperty("name")
    private String name;
}