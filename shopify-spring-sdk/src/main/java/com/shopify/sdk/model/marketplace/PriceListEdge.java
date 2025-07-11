package com.shopify.sdk.model.marketplace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an edge in a PriceListConnection.
 * Contains a price list node and a cursor for pagination.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceListEdge {
    /**
     * A cursor for use in pagination
     */
    private String cursor;
    
    /**
     * The price list at the end of the edge
     */
    private PriceList node;
}