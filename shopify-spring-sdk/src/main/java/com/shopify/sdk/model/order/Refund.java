package com.shopify.sdk.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Refund {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("note")
    private String note;
    
    @JsonProperty("order")
    private Order order;
    
    @JsonProperty("refundLineItems")
    private RefundLineItemConnection refundLineItems;
    
    @JsonProperty("staffMember")
    private StaffMember staffMember;
    
    @JsonProperty("totalRefundedSet")
    private MoneyBag totalRefundedSet;
    
    @JsonProperty("transactions")
    private List<OrderTransaction> transactions;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class RefundLineItem {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("lineItem")
    private LineItem lineItem;
    
    @JsonProperty("location")
    private Location location;
    
    @JsonProperty("priceSet")
    private MoneyBag priceSet;
    
    @JsonProperty("quantity")
    private Integer quantity;
    
    @JsonProperty("restockType")
    private RefundLineItemRestockType restockType;
    
    @JsonProperty("restocked")
    private Boolean restocked;
    
    @JsonProperty("subtotalSet")
    private MoneyBag subtotalSet;
    
    @JsonProperty("totalTaxSet")
    private MoneyBag totalTaxSet;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class RefundLineItemConnection {
    
    @JsonProperty("edges")
    private List<RefundLineItemEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class RefundLineItemEdge {
    
    @JsonProperty("node")
    private RefundLineItem node;
    
    @JsonProperty("cursor")
    private String cursor;
}