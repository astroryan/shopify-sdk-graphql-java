package com.shopify.sdk.model.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import com.shopify.sdk.model.product.ProductVariant;
import com.shopify.sdk.model.common.MoneyBag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a line item in a cart.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartLine implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The quantity of the line item.
     */
    @JsonProperty("quantity")
    private Integer quantity;
    
    /**
     * The product variant for the line item.
     */
    @JsonProperty("merchandise")
    private ProductVariant merchandise;
    
    /**
     * The attributes associated with the line item.
     */
    @JsonProperty("attributes")
    private List<CartAttribute> attributes;
    
    /**
     * The estimated cost of the line item.
     */
    @JsonProperty("estimatedCost")
    private CartLineEstimatedCost estimatedCost;
    
    /**
     * The discount allocations for the line item.
     */
    @JsonProperty("discountAllocations")
    private List<CartDiscountAllocation> discountAllocations;
}