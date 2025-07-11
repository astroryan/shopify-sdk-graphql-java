package com.shopify.sdk.model.marketplace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an edge in a MarketConnection.
 * Contains a market node and a cursor for pagination.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketEdge {
    /**
     * A cursor for use in pagination
     */
    private String cursor;
    
    /**
     * The market at the end of the edge
     */
    private Market node;
}