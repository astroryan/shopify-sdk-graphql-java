package com.shopify.sdk.model.marketplace;

import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a connection to a list of markets.
 * This connection type is used for paginated queries of markets in the Shopify Admin API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketConnection {
    /**
     * A list of edges containing market nodes and cursors
     */
    private List<MarketEdge> edges;
    
    /**
     * Information about pagination in the connection
     */
    private PageInfo pageInfo;
}