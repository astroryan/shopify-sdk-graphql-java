package com.shopify.sdk.model.retail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an edge in a RetailSessionConnection.
 * Contains a retail session node and a cursor for pagination.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetailSessionEdge {
    /**
     * A cursor for use in pagination
     */
    private String cursor;
    
    /**
     * The retail session at the end of the edge
     */
    private RetailSession node;
}