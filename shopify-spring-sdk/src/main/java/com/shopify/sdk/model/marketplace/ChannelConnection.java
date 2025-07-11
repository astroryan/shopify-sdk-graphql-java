package com.shopify.sdk.model.marketplace;

import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a connection to a list of channels.
 * This connection type is used for paginated queries of channels in the Shopify Admin API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelConnection {
    /**
     * A list of edges containing channel nodes and cursors
     */
    private List<ChannelEdge> edges;
    
    /**
     * Information about pagination in the connection
     */
    private PageInfo pageInfo;
}