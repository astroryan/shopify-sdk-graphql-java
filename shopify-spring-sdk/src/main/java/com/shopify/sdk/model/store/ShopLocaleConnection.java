package com.shopify.sdk.model.store;

import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a connection to a list of shop locales.
 * This connection type is used for paginated queries of shop locales in the Shopify Admin API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopLocaleConnection {
    /**
     * A list of edges containing shop locale nodes and cursors
     */
    private List<ShopLocaleEdge> edges;
    
    /**
     * Information about pagination in the connection
     */
    private PageInfo pageInfo;
}