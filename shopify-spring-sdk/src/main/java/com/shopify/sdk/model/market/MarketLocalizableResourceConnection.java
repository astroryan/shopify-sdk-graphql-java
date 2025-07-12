package com.shopify.sdk.model.market;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A connection to market localizable resources.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketLocalizableResourceConnection {
    /**
     * The edges in the connection.
     */
    @JsonProperty("edges")
    private List<MarketLocalizableResourceEdge> edges;
    
    /**
     * The nodes in the connection.
     */
    @JsonProperty("nodes")
    private List<MarketLocalizableResource> nodes;
    
    /**
     * Information about pagination.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}