package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import com.shopify.sdk.model.order.Order;
import com.shopify.sdk.model.common.Address;
import com.shopify.sdk.model.order.LineItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Represents a group of items in an order that are to be fulfilled from the same location.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentOrder implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The order that's associated with the fulfillment order.
     */
    @JsonProperty("order")
    private Order order;
    
    /**
     * The fulfillment order's status.
     */
    @JsonProperty("status")
    private FulfillmentOrderStatus status;
    
    /**
     * The date and time when the fulfillment order was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * The date and time when the fulfillment order was last updated.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
    
    /**
     * The address to ship the fulfillment.
     */
    @JsonProperty("deliveryAddress")
    private FulfillmentOrderDeliveryAddress deliveryAddress;
    
    /**
     * The line items in the fulfillment order.
     */
    @JsonProperty("lineItems")
    private FulfillmentOrderLineItemConnection lineItems;
    
    /**
     * The fulfillment holds applied to the fulfillment order.
     */
    @JsonProperty("fulfillmentHolds")
    private List<FulfillmentHold> fulfillmentHolds;
    
    /**
     * The location that will be fulfilling the order.
     */
    @JsonProperty("assignedLocation")
    private FulfillmentOrderAssignedLocation assignedLocation;
    
    /**
     * The merchant requests for the fulfillment order.
     */
    @JsonProperty("merchantRequests")
    private FulfillmentOrderMerchantRequestConnection merchantRequests;
    
    /**
     * The date and time when the fulfillment order will be ready for fulfillment.
     */
    @JsonProperty("fulfillAt")
    private OffsetDateTime fulfillAt;
    
    /**
     * The international duties for the fulfillment order.
     */
    @JsonProperty("internationalDuties")
    private FulfillmentOrderInternationalDuties internationalDuties;
    
    /**
     * The fulfillments associated with the fulfillment order.
     */
    @JsonProperty("fulfillments")
    private List<Fulfillment> fulfillments;
    
    /**
     * The request status of the fulfillment order.
     */
    @JsonProperty("requestStatus")
    private FulfillmentOrderRequestStatus requestStatus;
    
    /**
     * The actions that can be performed on this fulfillment order.
     */
    @JsonProperty("supportedActions")
    private List<FulfillmentOrderSupportedAction> supportedActions;
}