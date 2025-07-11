package com.shopify.sdk.model.retail;

import com.shopify.sdk.model.common.MoneyV2;
import com.shopify.sdk.model.common.Node;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.time.Instant;

/**
 * Represents a cash adjustment in a cash tracking session.
 * Used to record additions or removals of cash for various reasons.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashAdjustment implements Node {
    
    @GraphQLQuery(name = "id", description = "A globally-unique ID")
    private String id;
    
    @GraphQLQuery(name = "cashTrackingSession", description = "The cash tracking session this adjustment belongs to")
    private CashTrackingSession cashTrackingSession;
    
    @GraphQLQuery(name = "amount", description = "The amount of the adjustment")
    private MoneyV2 amount;
    
    @GraphQLQuery(name = "type", description = "The type of adjustment")
    private CashAdjustmentType type;
    
    @GraphQLQuery(name = "reason", description = "The reason for the adjustment")
    private String reason;
    
    @GraphQLQuery(name = "staffMember", description = "The staff member who made the adjustment")
    private StaffMember staffMember;
    
    @GraphQLQuery(name = "createdAt", description = "The date and time when the adjustment was created")
    private Instant createdAt;
    
    @GraphQLQuery(name = "note", description = "Additional notes about the adjustment")
    private String note;
    
    /**
     * The type of cash adjustment
     */
    public enum CashAdjustmentType {
        /**
         * Cash added to the drawer
         */
        CASH_IN,
        
        /**
         * Cash removed from the drawer
         */
        CASH_OUT,
        
        /**
         * Bank deposit
         */
        BANK_DEPOSIT,
        
        /**
         * Float adjustment
         */
        FLOAT_ADJUSTMENT,
        
        /**
         * Other adjustment type
         */
        OTHER
    }
}