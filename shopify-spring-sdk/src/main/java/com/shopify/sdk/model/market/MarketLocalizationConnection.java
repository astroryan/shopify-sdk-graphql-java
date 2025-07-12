package com.shopify.sdk.model.market;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A connection to market localizations.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketLocalizationConnection {
    /**
     * The edges in the connection.
     */
    @JsonProperty("edges")
    private List<MarketLocalizationEdge> edges;
    
    /**
     * The nodes in the connection.
     */
    @JsonProperty("nodes")
    private List<MarketLocalization> nodes;
    
    /**
     * Information about pagination.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}