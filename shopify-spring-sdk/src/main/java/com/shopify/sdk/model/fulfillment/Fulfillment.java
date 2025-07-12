package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import com.shopify.sdk.model.order.Order;
import com.shopify.sdk.model.location.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Represents a fulfillment of an order.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fulfillment implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The status of the fulfillment.
     */
    @JsonProperty("status")
    private FulfillmentStatus status;
    
    /**
     * The display status of the fulfillment.
     */
    @JsonProperty("displayStatus")
    private FulfillmentDisplayStatus displayStatus;
    
    /**
     * The tracking company.
     */
    @JsonProperty("trackingCompany")
    private String trackingCompany;
    
    /**
     * The tracking numbers.
     */
    @JsonProperty("trackingNumbers")
    private List<String> trackingNumbers;
    
    /**
     * The tracking URLs.
     */
    @JsonProperty("trackingUrls")
    private List<String> trackingUrls;
    
    /**
     * The location that fulfilled the order.
     */
    @JsonProperty("location")
    private Location location;
    
    /**
     * The date and time when the fulfillment was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * The date and time when the fulfillment was last modified.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
    
    /**
     * The date and time when the fulfillment was delivered.
     */
    @JsonProperty("deliveredAt")
    private OffsetDateTime deliveredAt;
    
    /**
     * The date and time when the fulfillment is estimated to arrive.
     */
    @JsonProperty("estimatedDeliveryAt")
    private OffsetDateTime estimatedDeliveryAt;
    
    /**
     * The date and time when the fulfillment is in transit.
     */
    @JsonProperty("inTransitAt")
    private OffsetDateTime inTransitAt;
    
    /**
     * Whether the customer should be notified.
     */
    @JsonProperty("notifyCustomer")
    private Boolean notifyCustomer;
    
    /**
     * The order that's being fulfilled.
     */
    @JsonProperty("order")
    private Order order;
    
    /**
     * The fulfillment line items.
     */
    @JsonProperty("fulfillmentLineItems")
    private FulfillmentLineItemConnection fulfillmentLineItems;
}