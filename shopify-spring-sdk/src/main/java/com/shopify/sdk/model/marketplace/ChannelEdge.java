package com.shopify.sdk.model.marketplace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an edge in a ChannelConnection.
 * Contains a channel node and a cursor for pagination.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelEdge {
    /**
     * A cursor for use in pagination
     */
    private String cursor;
    
    /**
     * The channel at the end of the edge
     */
    private Channel node;
}