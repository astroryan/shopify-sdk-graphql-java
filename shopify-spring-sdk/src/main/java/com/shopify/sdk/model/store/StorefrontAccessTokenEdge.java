package com.shopify.sdk.model.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an edge in a StorefrontAccessTokenConnection.
 * Contains a storefront access token node and a cursor for pagination.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorefrontAccessTokenEdge {
    /**
     * A cursor for use in pagination
     */
    private String cursor;
    
    /**
     * The storefront access token at the end of the edge
     */
    private StorefrontAccessToken node;
}