package com.shopify.sdk.model.common;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

/**
 * Represents the currency settings for a market or region.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencySetting {
    
    @GraphQLQuery(name = "currencyCode", description = "The three-letter code for the currency")
    private CurrencyCode currencyCode;
    
    @GraphQLQuery(name = "currencyName", description = "The name of the currency")
    private String currencyName;
    
    @GraphQLQuery(name = "enabled", description = "Whether the currency is enabled")
    private Boolean enabled;
    
    @GraphQLQuery(name = "rateUpdatedAt", description = "The date and time when the exchange rate was last updated")
    private String rateUpdatedAt;
}