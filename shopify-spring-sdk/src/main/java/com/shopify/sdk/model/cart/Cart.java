package com.shopify.sdk.model.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import com.shopify.sdk.model.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("lines")
    private CartLineConnection lines;
    
    @JsonProperty("attributes")
    private List<Attribute> attributes;
    
    @JsonProperty("buyerIdentity")
    private CartBuyerIdentity buyerIdentity;
    
    @JsonProperty("checkoutUrl")
    private String checkoutUrl;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    @JsonProperty("note")
    private String note;
    
    @JsonProperty("cost")
    private CartCost cost;
    
    @JsonProperty("discountCodes")
    private List<CartDiscountCode> discountCodes;
    
    @JsonProperty("discountAllocations")
    private List<CartDiscountAllocation> discountAllocations;
    
    @JsonProperty("totalQuantity")
    private Integer totalQuantity;
}