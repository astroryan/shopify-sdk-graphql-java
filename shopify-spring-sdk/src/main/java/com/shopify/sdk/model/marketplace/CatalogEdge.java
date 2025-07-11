package com.shopify.sdk.model.marketplace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an edge in a CatalogConnection.
 * Contains a catalog node and a cursor for pagination.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogEdge {
    /**
     * A cursor for use in pagination
     */
    private String cursor;
    
    /**
     * The catalog at the end of the edge
     */
    private Catalog node;
}