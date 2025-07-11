package com.shopify.sdk.model.retail;

import com.shopify.sdk.model.common.MoneyV2;
import com.shopify.sdk.model.common.Node;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.time.Instant;

/**
 * Represents a retail session.
 * A retail session tracks a period of retail activity, typically a shift or business day.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetailSession implements Node {
    
    @GraphQLQuery(name = "id", description = "A globally-unique ID")
    private String id;
    
    @GraphQLQuery(name = "device", description = "The device used for this session")
    private Device device;
    
    @GraphQLQuery(name = "staffMember", description = "The staff member running this session")
    private StaffMember staffMember;
    
    @GraphQLQuery(name = "location", description = "The location of the session")
    private Device.Location location;
    
    @GraphQLQuery(name = "status", description = "The status of the session")
    private RetailSessionStatus status;
    
    @GraphQLQuery(name = "openedAt", description = "When the session was opened")
    private Instant openedAt;
    
    @GraphQLQuery(name = "closedAt", description = "When the session was closed")
    private Instant closedAt;
    
    @GraphQLQuery(name = "cashTrackingSession", description = "The cash tracking session associated with this retail session")
    private CashTrackingSession cashTrackingSession;
    
    @GraphQLQuery(name = "totalSales", description = "Total sales for the session")
    private MoneyV2 totalSales;
    
    @GraphQLQuery(name = "totalRefunds", description = "Total refunds for the session")
    private MoneyV2 totalRefunds;
    
    @GraphQLQuery(name = "totalDiscounts", description = "Total discounts for the session")
    private MoneyV2 totalDiscounts;
    
    @GraphQLQuery(name = "totalTax", description = "Total tax for the session")
    private MoneyV2 totalTax;
    
    @GraphQLQuery(name = "transactionCount", description = "Number of transactions in the session")
    private Integer transactionCount;
    
    @GraphQLQuery(name = "createdAt", description = "The date and time when the session was created")
    private Instant createdAt;
    
    @GraphQLQuery(name = "updatedAt", description = "The date and time when the session was last updated")
    private Instant updatedAt;
    
    /**
     * The status of a retail session
     */
    public enum RetailSessionStatus {
        /**
         * The session is open and active
         */
        OPEN,
        
        /**
         * The session is closed
         */
        CLOSED,
        
        /**
         * The session is suspended
         */
        SUSPENDED,
        
        /**
         * The session was cancelled
         */
        CANCELLED
    }
}