package com.shopify.sdk.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import com.shopify.sdk.model.product.Product;
import com.shopify.sdk.model.product.ProductVariant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a line item in an order
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LineItem {
    /**
     * The globally unique identifier for the line item
     */
    @JsonProperty("id")
    private ID id;
    
    /**
     * The title of the product
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The name of the line item
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The SKU of the product variant
     */
    @JsonProperty("sku")
    private String sku;
    
    /**
     * The quantity of the line item
     */
    @JsonProperty("quantity")
    private Integer quantity;
    
    /**
     * The quantity that can be fulfilled
     */
    @JsonProperty("fulfillableQuantity")
    private Integer fulfillableQuantity;
    
    /**
     * The quantity that has been fulfilled
     */
    @JsonProperty("currentQuantity")
    private Integer currentQuantity;
    
    /**
     * Whether the line item requires shipping
     */
    @JsonProperty("requiresShipping")
    private Boolean requiresShipping;
    
    /**
     * Whether the line item is taxable
     */
    @JsonProperty("taxable")
    private Boolean taxable;
    
    /**
     * Whether the line item is a gift card
     */
    @JsonProperty("isGiftCard")
    private Boolean isGiftCard;
    
    /**
     * The price of the line item
     */
    @JsonProperty("originalUnitPriceSet")
    private MoneyBag originalUnitPriceSet;
    
    /**
     * The discounted price of the line item
     */
    @JsonProperty("discountedUnitPriceSet")
    private MoneyBag discountedUnitPriceSet;
    
    /**
     * The total price for the line item
     */
    @JsonProperty("originalTotalSet")
    private MoneyBag originalTotalSet;
    
    /**
     * The total discounted price for the line item
     */
    @JsonProperty("discountedTotalSet")
    private MoneyBag discountedTotalSet;
    
    /**
     * The discount allocations for the line item
     */
    @JsonProperty("discountAllocations")
    private List<DiscountAllocation> discountAllocations;
    
    /**
     * The tax lines for the line item
     */
    @JsonProperty("taxLines")
    private List<TaxLine> taxLines;
    
    /**
     * The product associated with the line item
     */
    @JsonProperty("product")
    private Product product;
    
    /**
     * The product variant associated with the line item
     */
    @JsonProperty("variant")
    private ProductVariant variant;
    
    /**
     * The variant title
     */
    @JsonProperty("variantTitle")
    private String variantTitle;
    
    /**
     * The vendor of the product
     */
    @JsonProperty("vendor")
    private String vendor;
    
    /**
     * Custom attributes for the line item
     */
    @JsonProperty("customAttributes")
    private List<Attribute> customAttributes;
}