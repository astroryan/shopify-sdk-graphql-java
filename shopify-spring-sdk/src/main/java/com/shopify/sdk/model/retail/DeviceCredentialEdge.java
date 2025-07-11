package com.shopify.sdk.model.retail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an edge in a DeviceCredentialConnection.
 * Contains a device credential node and a cursor for pagination.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCredentialEdge {
    /**
     * A cursor for use in pagination
     */
    private String cursor;
    
    /**
     * The device credential at the end of the edge
     */
    private DeviceCredential node;
}