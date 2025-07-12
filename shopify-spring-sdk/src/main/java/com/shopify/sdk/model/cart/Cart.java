package com.shopify.sdk.model.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import com.shopify.sdk.model.common.Address;
import com.shopify.sdk.model.common.MoneyBag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Represents a cart.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The note left by the buyer.
     */
    @JsonProperty("note")
    private String note;
    
    /**
     * The attributes associated with the cart.
     */
    @JsonProperty("attributes")
    private List<CartAttribute> attributes;
    
    /**
     * The lines in the cart.
     */
    @JsonProperty("lines")
    private CartLineConnection lines;
    
    /**
     * The buyer identity.
     */
    @JsonProperty("buyerIdentity")
    private CartBuyerIdentity buyerIdentity;
    
    /**
     * The delivery groups.
     */
    @JsonProperty("deliveryGroups")
    private CartDeliveryGroupConnection deliveryGroups;
    
    /**
     * The discount codes applied to the cart.
     */
    @JsonProperty("discountCodes")
    private List<CartDiscountCode> discountCodes;
    
    /**
     * The estimated cost of the cart.
     */
    @JsonProperty("estimatedCost")
    private CartEstimatedCost estimatedCost;
    
    /**
     * When the cart was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * When the cart was last updated.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
    
    /**
     * The checkout URL for the cart.
     */
    @JsonProperty("checkoutUrl")
    private String checkoutUrl;
    
    /**
     * The number of items in the cart.
     */
    @JsonProperty("totalQuantity")
    private Integer totalQuantity;
}