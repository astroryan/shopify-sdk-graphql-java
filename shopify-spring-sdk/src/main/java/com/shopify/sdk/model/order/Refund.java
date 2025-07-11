package com.shopify.sdk.model.order;

import com.shopify.sdk.model.common.*;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.time.Instant;
import java.util.List;

/**
 * Represents a refund of items or transactions in an order.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Refund implements Node {
    
    @GraphQLQuery(name = "id", description = "A globally-unique ID")
    private String id;
    
    @GraphQLQuery(name = "legacyResourceId", description = "The ID of the corresponding resource in the REST Admin API")
    private String legacyResourceId;
    
    @GraphQLQuery(name = "createdAt", description = "The date and time when the refund was created")
    private Instant createdAt;
    
    @GraphQLQuery(name = "updatedAt", description = "The date and time when the refund was last updated")
    private Instant updatedAt;
    
    @GraphQLQuery(name = "note", description = "The optional note associated with the refund")
    private String note;
    
    @GraphQLQuery(name = "order", description = "The order associated with the refund")
    private Order order;
    
    @GraphQLQuery(name = "refundLineItems", description = "The RefundLineItem resources attached to the refund")
    private RefundLineItemConnection refundLineItems;
    
    @GraphQLQuery(name = "staffMember", description = "The staff member who created the refund")
    private StaffMember staffMember;
    
    @GraphQLQuery(name = "totalRefundedSet", description = "The total amount across all transactions for the refund, in shop and presentment currencies")
    private MoneyBag totalRefundedSet;
    
    @GraphQLQuery(name = "transactions", description = "The transactions associated with the refund")
    private List<OrderTransaction> transactions;
    
    @GraphQLQuery(name = "duties", description = "A list of the refunded duties as part of this refund")
    private List<RefundDuty> duties;
    
    /**
     * Connection type for refund line items
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefundLineItemConnection {
        @GraphQLQuery(name = "edges", description = "The edges in the connection")
        private List<RefundLineItemEdge> edges;
        
        @GraphQLQuery(name = "nodes", description = "The nodes in the connection")
        private List<RefundLineItem> nodes;
        
        @GraphQLQuery(name = "pageInfo", description = "Information about pagination")
        private PageInfo pageInfo;
    }
    
    /**
     * Edge type for refund line items
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefundLineItemEdge {
        @GraphQLQuery(name = "cursor", description = "The cursor for the edge")
        private String cursor;
        
        @GraphQLQuery(name = "node", description = "The node at the edge")
        private RefundLineItem node;
    }
    
    /**
     * Represents a line item being refunded
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefundLineItem implements Node {
        @GraphQLQuery(name = "id", description = "A globally-unique identifier")
        private String id;
        
        @GraphQLQuery(name = "lineItem", description = "The line item associated with this refund line item")
        private LineItem lineItem;
        
        @GraphQLQuery(name = "quantity", description = "The quantity being refunded")
        private Integer quantity;
        
        @GraphQLQuery(name = "restockType", description = "The type of restock for this line item")
        private RefundLineItemRestockType restockType;
        
        @GraphQLQuery(name = "restocked", description = "Whether the line item was restocked")
        private Boolean restocked;
        
        @GraphQLQuery(name = "priceSet", description = "The price of the line item")
        private MoneyBag priceSet;
        
        @GraphQLQuery(name = "subtotalSet", description = "The subtotal of the line item")
        private MoneyBag subtotalSet;
        
        @GraphQLQuery(name = "totalTaxSet", description = "The total tax for the line item")
        private MoneyBag totalTaxSet;
        
        @GraphQLQuery(name = "location", description = "The location where the line item was restocked")
        private Location location;
    }
    
    /**
     * Represents a refunded duty
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefundDuty {
        @GraphQLQuery(name = "duty", description = "The duty associated with this refund")
        private Duty duty;
        
        @GraphQLQuery(name = "amountSet", description = "The amount of duty refunded")
        private MoneyBag amountSet;
    }
    
    /**
     * Represents a duty on a line item
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Duty implements Node {
        @GraphQLQuery(name = "id", description = "A globally-unique identifier")
        private String id;
        
        @GraphQLQuery(name = "countryCodeOfOrigin", description = "The country code of origin")
        private String countryCodeOfOrigin;
        
        @GraphQLQuery(name = "harmonizedSystemCode", description = "The harmonized system code")
        private String harmonizedSystemCode;
        
        @GraphQLQuery(name = "price", description = "The price of the duty")
        private MoneyBag price;
        
        @GraphQLQuery(name = "taxLines", description = "The tax lines for the duty")
        private List<DutyTaxLine> taxLines;
    }
    
    /**
     * Tax line for a duty
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DutyTaxLine {
        @GraphQLQuery(name = "title", description = "The title of the tax")
        private String title;
        
        @GraphQLQuery(name = "rate", description = "The rate of the tax")
        private Double rate;
        
        @GraphQLQuery(name = "priceSet", description = "The price of the tax")
        private MoneyBag priceSet;
    }
}