package com.shopify.sdk.model.event;

import com.shopify.sdk.model.common.MoneyV2;
import com.shopify.sdk.model.common.Node;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.time.Instant;

/**
 * Represents app credits that can be applied by the merchant towards future app purchases, subscriptions, or usage records in Shopify.
 * Merchants are entitled to app credits under certain circumstances, such as when a paid app subscription
 * is downgraded partway through its billing cycle.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppCredit implements Node {
    
    @GraphQLQuery(name = "id", description = "A globally-unique ID")
    private String id;
    
    @GraphQLQuery(name = "amount", description = "The amount that can be used towards future app purchases in Shopify")
    private MoneyV2 amount;
    
    @GraphQLQuery(name = "createdAt", description = "The date and time when the app credit was created")
    private Instant createdAt;
    
    @GraphQLQuery(name = "description", description = "The description of the app credit")
    private String description;
    
    @GraphQLQuery(name = "test", description = "Whether the app credit is a test transaction")
    private Boolean test;
}