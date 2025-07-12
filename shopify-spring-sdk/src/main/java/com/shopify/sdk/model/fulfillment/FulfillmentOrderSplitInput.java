package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for splitting a fulfillment order.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentOrderSplitInput {
    /**
     * The fulfillment order line items to be moved to a new fulfillment order.
     */
    @JsonProperty("fulfillmentOrderLineItems")
    private List<FulfillmentOrderLineItemInput> fulfillmentOrderLineItems;
    
    /**
     * The new fulfillment order to be created.
     */
    @JsonProperty("fulfillmentOrder")
    private SplitFulfillmentOrderInput fulfillmentOrder;
}

/**
 * Input for the new fulfillment order when splitting.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class SplitFulfillmentOrderInput {
    /**
     * The ID of the location that will fulfill the split fulfillment order.
     */
    @JsonProperty("assignedLocationId")
    private String assignedLocationId;
    
    /**
     * The date and time when the fulfillment order will be ready for fulfillment.
     */
    @JsonProperty("fulfillAt")
    private String fulfillAt;
}