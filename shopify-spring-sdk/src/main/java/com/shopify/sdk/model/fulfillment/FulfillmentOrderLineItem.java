package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.order.LineItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A line item in a fulfillment order.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentOrderLineItem {
    /**
     * The globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The associated order line item.
     */
    @JsonProperty("lineItem")
    private LineItem lineItem;
    
    /**
     * The number of units remaining to be fulfilled.
     */
    @JsonProperty("remainingQuantity")
    private Integer remainingQuantity;
    
    /**
     * The total quantity of the line item.
     */
    @JsonProperty("totalQuantity")
    private Integer totalQuantity;
}