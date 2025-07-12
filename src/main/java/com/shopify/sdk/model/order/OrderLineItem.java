package com.shopify.sdk.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.ID;
import com.shopify.sdk.graphql.scalar.MoneyScalar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a single line in an order.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItem {
    
    /**
     * A globally-unique identifier.
     */
    @JsonProperty("id")
    private ID id;
    
    /**
     * The quantity of the line item.
     */
    @JsonProperty("quantity")
    private Integer quantity;
    
    /**
     * The title of the product.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The product variant title of the line item.
     */
    @JsonProperty("variantTitle")
    private String variantTitle;
    
    /**
     * The name of the product variant.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The SKU (stock keeping unit) associated with the variant.
     */
    @JsonProperty("sku")
    private String sku;
    
    /**
     * The vendor of the product variant.
     */
    @JsonProperty("vendor")
    private String vendor;
    
    /**
     * The weight of the line item.
     */
    @JsonProperty("weight")
    private Double weight;
    
    /**
     * Whether the line item requires shipping.
     */
    @JsonProperty("requiresShipping")
    private Boolean requiresShipping;
    
    /**
     * Whether the line item is taxable.
     */
    @JsonProperty("taxable")
    private Boolean taxable;
    
    /**
     * Whether the line item is a gift card.
     */
    @JsonProperty("giftCard")
    private Boolean giftCard;
    
    /**
     * The fulfillment status of this line item.
     */
    @JsonProperty("fulfillmentStatus")
    private String fulfillmentStatus;
    
    /**
     * The amount available to fulfill, calculated as follows: quantity - fulfillableQuantity - pendingFulfillmentQuantity
     */
    @JsonProperty("fulfillableQuantity")
    private Integer fulfillableQuantity;
    
    /**
     * The total amount available to refund.
     */
    @JsonProperty("refundableQuantity")
    private Integer refundableQuantity;
    
    /**
     * The price of the line item.
     */
    @JsonProperty("originalUnitPrice")
    private MoneyScalar originalUnitPrice;
    
    /**
     * The discounted price of the line item.
     */
    @JsonProperty("discountedUnitPrice")
    private MoneyScalar discountedUnitPrice;
    
    /**
     * The total price of the line item.
     */
    @JsonProperty("originalTotalPrice")
    private MoneyScalar originalTotalPrice;
    
    /**
     * The discounted total price of the line item.
     */
    @JsonProperty("discountedTotalPrice")
    private MoneyScalar discountedTotalPrice;
    
    /**
     * The total amount of the discount allocated to the line item.
     */
    @JsonProperty("totalDiscount")
    private MoneyScalar totalDiscount;
    
    /**
     * List of custom attributes associated to the line item.
     */
    @JsonProperty("customAttributes")
    private List<Attribute> customAttributes;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Attribute {
        
        @JsonProperty("key")
        private String key;
        
        @JsonProperty("value")
        private String value;
    }
}