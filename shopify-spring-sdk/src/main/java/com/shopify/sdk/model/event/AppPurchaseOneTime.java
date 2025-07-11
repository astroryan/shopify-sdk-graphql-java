package com.shopify.sdk.model.event;

import com.shopify.sdk.model.common.MoneyV2;
import com.shopify.sdk.model.common.Node;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.time.Instant;

/**
 * Represents a one-time app purchase.
 * This is used when an app charges a one-time fee to the merchant.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppPurchaseOneTime implements Node {
    
    @GraphQLQuery(name = "id", description = "A globally-unique ID")
    private String id;
    
    @GraphQLQuery(name = "name", description = "The name of the app purchase")
    private String name;
    
    @GraphQLQuery(name = "price", description = "The amount to be charged to the store for the app purchase")
    private MoneyV2 price;
    
    @GraphQLQuery(name = "status", description = "The status of the app purchase")
    private AppPurchaseStatus status;
    
    @GraphQLQuery(name = "test", description = "Whether the app purchase is a test transaction")
    private Boolean test;
    
    @GraphQLQuery(name = "createdAt", description = "The date and time when the app purchase occurred")
    private Instant createdAt;
    
    /**
     * The status of an app purchase
     */
    public enum AppPurchaseStatus {
        /**
         * The app purchase has been approved and is currently active
         */
        ACTIVE,
        
        /**
         * The app purchase is awaiting merchant approval
         */
        PENDING,
        
        /**
         * The merchant has declined the app purchase
         */
        DECLINED,
        
        /**
         * The app purchase has expired
         */
        EXPIRED
    }
}