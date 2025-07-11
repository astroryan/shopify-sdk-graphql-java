package com.shopify.sdk.model.retail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an edge in a CashTrackingSessionConnection.
 * Contains a cash tracking session node and a cursor for pagination.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashTrackingSessionEdge {
    /**
     * A cursor for use in pagination
     */
    private String cursor;
    
    /**
     * The cash tracking session at the end of the edge
     */
    private CashTrackingSession node;
}