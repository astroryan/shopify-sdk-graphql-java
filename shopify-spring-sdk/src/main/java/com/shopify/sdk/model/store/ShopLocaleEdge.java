package com.shopify.sdk.model.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an edge in a ShopLocaleConnection.
 * Contains a shop locale node and a cursor for pagination.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopLocaleEdge {
    /**
     * A cursor for use in pagination
     */
    private String cursor;
    
    /**
     * The shop locale at the end of the edge
     */
    private ShopLocale node;
}