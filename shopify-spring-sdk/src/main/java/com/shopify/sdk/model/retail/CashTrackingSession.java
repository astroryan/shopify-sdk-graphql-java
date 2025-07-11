package com.shopify.sdk.model.retail;

import com.shopify.sdk.model.common.MoneyV2;
import com.shopify.sdk.model.common.Node;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.time.Instant;
import java.util.List;

/**
 * Represents a cash tracking session.
 * Used to track cash transactions and reconciliation in retail operations.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashTrackingSession implements Node {
    
    @GraphQLQuery(name = "id", description = "A globally-unique ID")
    private String id;
    
    @GraphQLQuery(name = "retailSession", description = "The retail session this cash tracking is associated with")
    private RetailSession retailSession;
    
    @GraphQLQuery(name = "openingCashAmount", description = "The opening cash amount")
    private MoneyV2 openingCashAmount;
    
    @GraphQLQuery(name = "expectedCashAmount", description = "The expected cash amount based on transactions")
    private MoneyV2 expectedCashAmount;
    
    @GraphQLQuery(name = "closingCashAmount", description = "The actual closing cash amount")
    private MoneyV2 closingCashAmount;
    
    @GraphQLQuery(name = "cashDiscrepancy", description = "The difference between expected and actual cash")
    private MoneyV2 cashDiscrepancy;
    
    @GraphQLQuery(name = "status", description = "The status of the cash tracking session")
    private CashTrackingSessionStatus status;
    
    @GraphQLQuery(name = "openedAt", description = "When the cash tracking session was opened")
    private Instant openedAt;
    
    @GraphQLQuery(name = "closedAt", description = "When the cash tracking session was closed")
    private Instant closedAt;
    
    @GraphQLQuery(name = "openedBy", description = "The staff member who opened the session")
    private StaffMember openedBy;
    
    @GraphQLQuery(name = "closedBy", description = "The staff member who closed the session")
    private StaffMember closedBy;
    
    @GraphQLQuery(name = "cashAdjustments", description = "Cash adjustments made during the session")
    private List<CashAdjustment> cashAdjustments;
    
    @GraphQLQuery(name = "notes", description = "Notes about the cash tracking session")
    private String notes;
    
    @GraphQLQuery(name = "createdAt", description = "The date and time when the session was created")
    private Instant createdAt;
    
    @GraphQLQuery(name = "updatedAt", description = "The date and time when the session was last updated")
    private Instant updatedAt;
    
    /**
     * The status of a cash tracking session
     */
    public enum CashTrackingSessionStatus {
        /**
         * The session is open
         */
        OPEN,
        
        /**
         * The session is closed and reconciled
         */
        CLOSED,
        
        /**
         * The session has discrepancies that need review
         */
        REVIEW_REQUIRED,
        
        /**
         * The session was approved despite discrepancies
         */
        APPROVED
    }
}