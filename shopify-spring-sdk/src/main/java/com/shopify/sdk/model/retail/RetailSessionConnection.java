package com.shopify.sdk.model.retail;

import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a connection to a list of retail sessions.
 * This connection type is used for paginated queries of retail sessions in the Shopify Admin API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetailSessionConnection {
    /**
     * A list of edges containing retail session nodes and cursors
     */
    private List<RetailSessionEdge> edges;
    
    /**
     * Information about pagination in the connection
     */
    private PageInfo pageInfo;
}