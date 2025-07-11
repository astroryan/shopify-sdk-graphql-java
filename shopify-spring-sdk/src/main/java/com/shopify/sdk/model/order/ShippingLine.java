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
public class ShippingLine {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("carrierIdentifier")
    private String carrierIdentifier;
    
    @JsonProperty("code")
    private String code;
    
    @JsonProperty("custom")
    private Boolean custom;
    
    @JsonProperty("deliveryCategory")
    private String deliveryCategory;
    
    @JsonProperty("discountAllocations")
    private List<DiscountAllocation> discountAllocations;
    
    @JsonProperty("discountedPriceSet")
    private MoneyBag discountedPriceSet;
    
    @JsonProperty("originalPriceSet")
    private MoneyBag originalPriceSet;
    
    @JsonProperty("phone")
    private String phone;
    
    @JsonProperty("requestedFulfillmentService")
    private FulfillmentService requestedFulfillmentService;
    
    @JsonProperty("shippingRateHandle")
    private String shippingRateHandle;
    
    @JsonProperty("source")
    private String source;
    
    @JsonProperty("taxLines")
    private List<TaxLine> taxLines;
    
    @JsonProperty("title")
    private String title;
}