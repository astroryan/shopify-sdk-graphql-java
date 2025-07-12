package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import com.shopify.sdk.model.order.LineItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a line item in a fulfillment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentLineItem implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The associated order line item.
     */
    @JsonProperty("lineItem")
    private LineItem lineItem;
    
    /**
     * The quantity fulfilled.
     */
    @JsonProperty("quantity")
    private Integer quantity;
}