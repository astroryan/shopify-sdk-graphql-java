package com.shopify.sdk.model.marketplace;

import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a connection to a list of price lists.
 * This connection type is used for paginated queries of price lists in the Shopify Admin API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceListConnection {
    /**
     * A list of edges containing price list nodes and cursors
     */
    private List<PriceListEdge> edges;
    
    /**
     * Information about pagination in the connection
     */
    private PageInfo pageInfo;
}