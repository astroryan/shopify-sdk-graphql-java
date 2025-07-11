package com.shopify.sdk.model.billing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppRecurringPricing {
    
    @JsonProperty("discount")
    private AppSubscriptionDiscount discount;
    
    @JsonProperty("interval")
    private AppPricingInterval interval;
    
    @JsonProperty("price")
    private Money price;
}