package com.shopify.sdk.model.store;

import com.shopify.sdk.model.common.Node;
import io.leangen.graphql.annotations.GraphQLQuery;

/**
 * A geographic region which comprises a market.
 * Requires `read_markets` for queries and both `read_markets` and `write_markets` for mutations.
 */
public interface MarketRegion extends Node {
    
    @GraphQLQuery(name = "id", description = "A globally-unique ID")
    String getId();
    
    @GraphQLQuery(name = "name", description = "The name of the region")
    String getName();
}