package com.shopify.sdk.model.store;

import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a connection to a list of storefront access tokens.
 * This connection type is used for paginated queries of storefront access tokens in the Shopify Admin API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorefrontAccessTokenConnection {
    /**
     * A list of edges containing storefront access token nodes and cursors
     */
    private List<StorefrontAccessTokenEdge> edges;
    
    /**
     * Information about pagination in the connection
     */
    private PageInfo pageInfo;
}