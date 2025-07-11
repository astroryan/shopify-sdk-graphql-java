package com.shopify.sdk.model.event;

import com.shopify.sdk.model.common.MoneyV2;
import com.shopify.sdk.model.common.Node;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.time.Instant;

/**
 * Represents a record of app revenue attribution.
 * This type is restricted to apps with `app_attributions` scope using offline tokens.
 * Note: This is part of a private program that is not available to all partners.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppRevenueAttributionRecord implements Node {
    
    @GraphQLQuery(name = "id", description = "A globally-unique ID")
    private String id;
    
    @GraphQLQuery(name = "amount", description = "The financial amount captured in this attribution")
    private MoneyV2 amount;
    
    @GraphQLQuery(name = "capturedAt", description = "The timestamp when the financial amount was captured")
    private Instant capturedAt;
    
    @GraphQLQuery(name = "createdAt", description = "The timestamp at which this revenue attribution was issued")
    private Instant createdAt;
    
    @GraphQLQuery(name = "idempotencyKey", description = "The unique value submitted during the creation of the app revenue attribution record")
    private String idempotencyKey;
    
    @GraphQLQuery(name = "test", description = "Indicates whether this is a test submission")
    private Boolean test;
    
    @GraphQLQuery(name = "type", description = "The type of revenue attribution")
    private AppRevenueAttributionType type;
    
    /**
     * The billing types of revenue attribution
     */
    public enum AppRevenueAttributionType {
        /**
         * App purchase related revenue collection
         */
        APPLICATION_PURCHASE,
        
        /**
         * App subscription revenue collection
         */
        APPLICATION_SUBSCRIPTION,
        
        /**
         * App usage-based revenue collection
         */
        APPLICATION_USAGE,
        
        /**
         * Other app revenue collection type
         */
        OTHER
    }
}