package com.shopify.sdk.model.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an edge in a DomainConnection.
 * Contains a domain node and a cursor for pagination.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainEdge {
    /**
     * A cursor for use in pagination
     */
    private String cursor;
    
    /**
     * The domain at the end of the edge
     */
    private Domain node;
}