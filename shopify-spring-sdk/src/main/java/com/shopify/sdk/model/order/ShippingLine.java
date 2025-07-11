package com.shopify.sdk.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a shipping line for an order
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingLine {
    /**
     * The ID of the shipping line
     */
    @JsonProperty("id")
    private ID id;
    
    /**
     * The carrier identifier
     */
    @JsonProperty("carrierIdentifier")
    private String carrierIdentifier;
    
    /**
     * The code for the shipping line
     */
    @JsonProperty("code")
    private String code;
    
    /**
     * The delivery category
     */
    @JsonProperty("deliveryCategory")
    private String deliveryCategory;
    
    /**
     * The discount allocations for the shipping line
     */
    @JsonProperty("discountAllocations")
    private List<DiscountAllocation> discountAllocations;
    
    /**
     * The discounted price of the shipping line
     */
    @JsonProperty("discountedPriceSet")
    private MoneyBag discountedPriceSet;
    
    /**
     * The original price of the shipping line
     */
    @JsonProperty("originalPriceSet")
    private MoneyBag originalPriceSet;
    
    /**
     * The phone number for the shipping line
     */
    @JsonProperty("phone")
    private String phone;
    
    /**
     * The requested fulfillment service
     */
    @JsonProperty("requestedFulfillmentService")
    private String requestedFulfillmentService;
    
    /**
     * The source of the shipping line
     */
    @JsonProperty("source")
    private String source;
    
    /**
     * The tax lines for the shipping line
     */
    @JsonProperty("taxLines")
    private List<TaxLine> taxLines;
    
    /**
     * The title of the shipping line
     */
    @JsonProperty("title")
    private String title;
}