package com.shopify.sdk.model.marketplace;

import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a connection to a list of catalogs.
 * This connection type is used for paginated queries of catalogs in the Shopify Admin API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogConnection {
    /**
     * A list of edges containing catalog nodes and cursors
     */
    private List<CatalogEdge> edges;
    
    /**
     * Information about pagination in the connection
     */
    private PageInfo pageInfo;
}