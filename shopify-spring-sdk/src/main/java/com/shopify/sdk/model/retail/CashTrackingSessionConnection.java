package com.shopify.sdk.model.retail;

import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a connection to a list of cash tracking sessions.
 * This connection type is used for paginated queries of cash tracking sessions in the Shopify Admin API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashTrackingSessionConnection {
    /**
     * A list of edges containing cash tracking session nodes and cursors
     */
    private List<CashTrackingSessionEdge> edges;
    
    /**
     * Information about pagination in the connection
     */
    private PageInfo pageInfo;
}