package com.shopify.sdk.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Edge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * An auto-generated type which holds one OrderLineItem and a cursor during pagination.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderLineItemEdge extends Edge<OrderLineItem> {
    
    /**
     * The item at the end of OrderLineItemEdge.
     */
    @JsonProperty("node")
    private OrderLineItem node;
    
    /**
     * A cursor for use in pagination.
     */
    @JsonProperty("cursor")
    private String cursor;
}