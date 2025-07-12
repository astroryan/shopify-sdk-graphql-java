package com.shopify.sdk.model.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * An edge in a cart delivery group connection.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDeliveryGroupEdge {
    /**
     * The cursor for pagination.
     */
    @JsonProperty("cursor")
    private String cursor;
    
    /**
     * The node.
     */
    @JsonProperty("node")
    private CartDeliveryGroup node;
}