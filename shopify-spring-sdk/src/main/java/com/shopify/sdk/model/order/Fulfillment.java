package com.shopify.sdk.model.order;

import com.shopify.sdk.model.common.*;
import com.shopify.sdk.model.store.FulfillmentService;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.time.Instant;
import java.util.List;

/**
 * Represents a fulfillment of one or more items in an order.
 * A fulfillment represents a shipment of goods in an order.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fulfillment implements Node {
    
    @GraphQLQuery(name = "id", description = "A globally-unique identifier")
    private String id;
    
    @GraphQLQuery(name = "legacyResourceId", description = "The ID of the corresponding resource in the REST Admin API")
    private String legacyResourceId;
    
    @GraphQLQuery(name = "status", description = "The status of the fulfillment")
    private FulfillmentStatus status;
    
    @GraphQLQuery(name = "displayStatus", description = "The human-readable status of the fulfillment")
    private FulfillmentDisplayStatus displayStatus;
    
    @GraphQLQuery(name = "order", description = "The order for which the fulfillment was created")
    private Order order;
    
    @GraphQLQuery(name = "location", description = "The location where the fulfillment was processed")
    private Location location;
    
    @GraphQLQuery(name = "service", description = "The fulfillment service associated with the fulfillment")
    private FulfillmentService service;
    
    @GraphQLQuery(name = "trackingInfo", description = "Tracking information for the fulfillment")
    private List<FulfillmentTrackingInfo> trackingInfo;
    
    @GraphQLQuery(name = "fulfillmentLineItems", description = "The line items in the fulfillment")
    private FulfillmentLineItemConnection fulfillmentLineItems;
    
    @GraphQLQuery(name = "totalQuantity", description = "The sum of all line item quantities in the fulfillment")
    private Integer totalQuantity;
    
    @GraphQLQuery(name = "name", description = "A unique identifier for the fulfillment")
    private String name;
    
    @GraphQLQuery(name = "createdAt", description = "The date and time when the fulfillment was created")
    private Instant createdAt;
    
    @GraphQLQuery(name = "updatedAt", description = "The date and time when the fulfillment was last updated")
    private Instant updatedAt;
    
    @GraphQLQuery(name = "deliveredAt", description = "The date and time when the fulfillment was delivered")
    private Instant deliveredAt;
    
    @GraphQLQuery(name = "estimatedDeliveryAt", description = "The estimated date and time for delivery")
    private Instant estimatedDeliveryAt;
    
    @GraphQLQuery(name = "inTransitAt", description = "The date and time when the fulfillment went in transit")
    private Instant inTransitAt;
    
    @GraphQLQuery(name = "requiresShipping", description = "Whether the fulfillment requires shipping")
    private Boolean requiresShipping;
    
    /**
     * The status of a fulfillment
     */
    public enum FulfillmentStatus {
        /**
         * The fulfillment was completed successfully
         */
        SUCCESS,
        
        /**
         * The fulfillment was canceled
         */
        CANCELLED,
        
        /**
         * There was an error with the fulfillment request
         */
        ERROR,
        
        /**
         * The fulfillment request failed
         */
        FAILURE,
        
        /**
         * @deprecated Use SUCCESS instead
         */
        @Deprecated
        OPEN,
        
        /**
         * @deprecated Use SUCCESS instead
         */
        @Deprecated
        PENDING
    }
    
    /**
     * The display status of a fulfillment
     */
    public enum FulfillmentDisplayStatus {
        ATTEMPTED_DELIVERY,
        CANCELED,
        CONFIRMED,
        DELIVERED,
        FAILURE,
        FULFILLED,
        IN_TRANSIT,
        LABEL_PRINTED,
        LABEL_PURCHASED,
        LABEL_VOIDED,
        MARKED_AS_FULFILLED,
        NOT_DELIVERED,
        OUT_FOR_DELIVERY,
        PICKED_UP,
        READY_FOR_PICKUP,
        SUBMITTED
    }
    
    /**
     * Tracking information for a fulfillment
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FulfillmentTrackingInfo {
        @GraphQLQuery(name = "company", description = "The name of the tracking company")
        private String company;
        
        @GraphQLQuery(name = "number", description = "The tracking number")
        private String number;
        
        @GraphQLQuery(name = "url", description = "The URL for tracking the fulfillment")
        private String url;
    }
    
    /**
     * Connection type for fulfillment line items
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FulfillmentLineItemConnection {
        @GraphQLQuery(name = "edges", description = "The edges in the connection")
        private List<FulfillmentLineItemEdge> edges;
        
        @GraphQLQuery(name = "nodes", description = "The nodes in the connection")
        private List<FulfillmentLineItem> nodes;
        
        @GraphQLQuery(name = "pageInfo", description = "Information about pagination")
        private PageInfo pageInfo;
    }
    
    /**
     * Edge type for fulfillment line items
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FulfillmentLineItemEdge {
        @GraphQLQuery(name = "cursor", description = "The cursor for the edge")
        private String cursor;
        
        @GraphQLQuery(name = "node", description = "The node at the edge")
        private FulfillmentLineItem node;
    }
    
    /**
     * Represents a line item in a fulfillment
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FulfillmentLineItem implements Node {
        @GraphQLQuery(name = "id", description = "A globally-unique identifier")
        private String id;
        
        @GraphQLQuery(name = "lineItem", description = "The line item associated with this fulfillment line item")
        private LineItem lineItem;
        
        @GraphQLQuery(name = "quantity", description = "The quantity fulfilled")
        private Integer quantity;
        
        @GraphQLQuery(name = "discountedTotalSet", description = "The total line price after discounts")
        private MoneyBag discountedTotalSet;
        
        @GraphQLQuery(name = "originalTotalSet", description = "The total line price before discounts")
        private MoneyBag originalTotalSet;
    }
}