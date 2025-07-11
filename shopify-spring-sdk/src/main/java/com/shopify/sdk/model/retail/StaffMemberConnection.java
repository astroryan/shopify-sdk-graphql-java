package com.shopify.sdk.model.retail;

import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a connection to a list of staff members.
 * This connection type is used for paginated queries of staff members in the Shopify Admin API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffMemberConnection {
    /**
     * A list of edges containing staff member nodes and cursors
     */
    private List<StaffMemberEdge> edges;
    
    /**
     * Information about pagination in the connection
     */
    private PageInfo pageInfo;
}