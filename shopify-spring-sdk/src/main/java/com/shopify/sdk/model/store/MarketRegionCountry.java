package com.shopify.sdk.model.store;

import com.shopify.sdk.model.common.CountryCode;
import com.shopify.sdk.model.common.CurrencySetting;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

/**
 * A country which comprises a market.
 * Requires `read_markets` for queries and both `read_markets` and `write_markets` for mutations.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketRegionCountry implements MarketRegion {
    
    @GraphQLQuery(name = "id", description = "A globally-unique ID")
    private String id;
    
    @GraphQLQuery(name = "name", description = "The name of the region")
    private String name;
    
    @GraphQLQuery(name = "code", description = "The ISO code identifying the country")
    private CountryCode code;
    
    @GraphQLQuery(name = "currency", description = "The currency which this country uses given its market settings")
    private CurrencySetting currency;
}