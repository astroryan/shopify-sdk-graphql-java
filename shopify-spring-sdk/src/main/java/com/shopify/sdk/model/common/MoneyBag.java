package com.shopify.sdk.model.common;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

/**
 * Represents a collection of monetary values in their respective currencies.
 * Used for multi-currency support where amounts are shown in both shop and presentment currencies.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoneyBag {
    
    @GraphQLQuery(name = "presentmentMoney", description = "The amount in presentment currency")
    private MoneyV2 presentmentMoney;
    
    @GraphQLQuery(name = "shopMoney", description = "The amount in shop currency")
    private MoneyV2 shopMoney;
}