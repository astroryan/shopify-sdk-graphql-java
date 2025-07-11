package com.shopify.sdk.model.retail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an edge in a DeviceConnection.
 * Contains a device node and a cursor for pagination.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceEdge {
    /**
     * A cursor for use in pagination
     */
    private String cursor;
    
    /**
     * The device at the end of the edge
     */
    private Device node;
}