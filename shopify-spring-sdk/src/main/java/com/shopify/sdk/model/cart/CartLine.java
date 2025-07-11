package com.shopify.sdk.model.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.ID;
import com.shopify.sdk.model.common.Money;
import com.shopify.sdk.model.product.ProductVariant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartLine {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("quantity")
    private Integer quantity;
    
    @JsonProperty("merchandise")
    private ProductVariant merchandise;
    
    @JsonProperty("attributes")
    private List<Attribute> attributes;
    
    @JsonProperty("cost")
    private CartLineCost cost;
    
    @JsonProperty("discountAllocations")
    private List<CartDiscountAllocation> discountAllocations;
}