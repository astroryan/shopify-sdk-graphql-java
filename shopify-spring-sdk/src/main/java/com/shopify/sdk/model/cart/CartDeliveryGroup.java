package com.shopify.sdk.model.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import com.shopify.sdk.model.common.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a delivery group in a cart.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDeliveryGroup implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The cart lines in this delivery group.
     */
    @JsonProperty("cartLines")
    private CartLineConnection cartLines;
    
    /**
     * The delivery address for this group.
     */
    @JsonProperty("deliveryAddress")
    private Address deliveryAddress;
    
    /**
     * The selected delivery option.
     */
    @JsonProperty("selectedDeliveryOption")
    private CartDeliveryOption selectedDeliveryOption;
    
    /**
     * The delivery options available for this group.
     */
    @JsonProperty("deliveryOptions")
    private List<CartDeliveryOption> deliveryOptions;
}