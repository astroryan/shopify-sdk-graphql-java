package com.shopify.sdk.model.order.input;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.util.List;

/**
 * Input for creating a fulfillment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentInput {
    
    @GraphQLQuery(name = "orderId", description = "The ID of the order being fulfilled")
    private String orderId;
    
    @GraphQLQuery(name = "lineItemsByFulfillmentOrder", description = "Line items grouped by fulfillment order")
    private List<FulfillmentOrderLineItemsInput> lineItemsByFulfillmentOrder;
    
    @GraphQLQuery(name = "notifyCustomer", description = "Whether to notify the customer")
    private Boolean notifyCustomer;
    
    @GraphQLQuery(name = "shippingMethod", description = "The shipping method for the fulfillment")
    private String shippingMethod;
    
    @GraphQLQuery(name = "trackingInfo", description = "Tracking information for the fulfillment")
    private FulfillmentTrackingInput trackingInfo;
    
    @GraphQLQuery(name = "originAddress", description = "The origin address for the fulfillment")
    private FulfillmentOriginAddressInput originAddress;
    
    /**
     * Input for fulfillment order line items
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FulfillmentOrderLineItemsInput {
        @GraphQLQuery(name = "fulfillmentOrderId", description = "The ID of the fulfillment order")
        private String fulfillmentOrderId;
        
        @GraphQLQuery(name = "fulfillmentOrderLineItems", description = "The line items to fulfill")
        private List<FulfillmentOrderLineItemInput> fulfillmentOrderLineItems;
    }
    
    /**
     * Input for a single fulfillment order line item
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FulfillmentOrderLineItemInput {
        @GraphQLQuery(name = "id", description = "The ID of the fulfillment order line item")
        private String id;
        
        @GraphQLQuery(name = "quantity", description = "The quantity to fulfill")
        private Integer quantity;
    }
    
    /**
     * Input for fulfillment tracking information
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FulfillmentTrackingInput {
        @GraphQLQuery(name = "company", description = "The name of the tracking company")
        private String company;
        
        @GraphQLQuery(name = "number", description = "The tracking number")
        private String number;
        
        @GraphQLQuery(name = "url", description = "The URL for tracking the fulfillment")
        private String url;
    }
    
    /**
     * Input for fulfillment origin address
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FulfillmentOriginAddressInput {
        @GraphQLQuery(name = "address1", description = "The first line of the address")
        private String address1;
        
        @GraphQLQuery(name = "address2", description = "The second line of the address")
        private String address2;
        
        @GraphQLQuery(name = "city", description = "The city")
        private String city;
        
        @GraphQLQuery(name = "countryCode", description = "The country code")
        private String countryCode;
        
        @GraphQLQuery(name = "provinceCode", description = "The province or state code")
        private String provinceCode;
        
        @GraphQLQuery(name = "zip", description = "The postal code")
        private String zip;
    }
}