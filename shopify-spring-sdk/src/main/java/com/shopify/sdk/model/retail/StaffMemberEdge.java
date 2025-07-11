package com.shopify.sdk.model.retail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an edge in a StaffMemberConnection.
 * Contains a staff member node and a cursor for pagination.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffMemberEdge {
    /**
     * A cursor for use in pagination
     */
    private String cursor;
    
    /**
     * The staff member at the end of the edge
     */
    private StaffMember node;
}