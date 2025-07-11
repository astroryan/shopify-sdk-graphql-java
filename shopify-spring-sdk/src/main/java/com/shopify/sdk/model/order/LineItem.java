package com.shopify.sdk.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
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
public class LineItem {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("variantTitle")
    private String variantTitle;
    
    @JsonProperty("quantity")
    private Integer quantity;
    
    @JsonProperty("variant")
    private ProductVariant variant;
    
    @JsonProperty("product")
    private Product product;
    
    @JsonProperty("originalTotalSet")
    private MoneyBag originalTotalSet;
    
    @JsonProperty("originalUnitPriceSet")
    private MoneyBag originalUnitPriceSet;
    
    @JsonProperty("discountedTotalSet")
    private MoneyBag discountedTotalSet;
    
    @JsonProperty("discountedUnitPriceSet")
    private MoneyBag discountedUnitPriceSet;
    
    @JsonProperty("totalDiscountSet")
    private MoneyBag totalDiscountSet;
    
    @JsonProperty("discountAllocations")
    private List<DiscountAllocation> discountAllocations;
    
    @JsonProperty("taxLines")
    private List<TaxLine> taxLines;
    
    @JsonProperty("taxable")
    private Boolean taxable;
    
    @JsonProperty("customAttributes")
    private List<Attribute> customAttributes;
    
    @JsonProperty("fulfillableQuantity")
    private Integer fulfillableQuantity;
    
    @JsonProperty("fulfillmentService")
    private FulfillmentService fulfillmentService;
    
    @JsonProperty("fulfillmentStatus")
    private String fulfillmentStatus;
    
    @JsonProperty("giftCard")
    private Boolean giftCard;
    
    @JsonProperty("merchantEditable")
    private Boolean merchantEditable;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("nonFulfillableQuantity")
    private Integer nonFulfillableQuantity;
    
    @JsonProperty("refundableQuantity")
    private Integer refundableQuantity;
    
    @JsonProperty("requiresShipping")
    private Boolean requiresShipping;
    
    @JsonProperty("restockable")
    private Boolean restockable;
    
    @JsonProperty("sku")
    private String sku;
    
    @JsonProperty("unfulfilledDiscountedTotalSet")
    private MoneyBag unfulfilledDiscountedTotalSet;
    
    @JsonProperty("unfulfilledOriginalTotalSet")
    private MoneyBag unfulfilledOriginalTotalSet;
    
    @JsonProperty("unfulfilledQuantity")
    private Integer unfulfilledQuantity;
    
    @JsonProperty("vendor")
    private String vendor;
}