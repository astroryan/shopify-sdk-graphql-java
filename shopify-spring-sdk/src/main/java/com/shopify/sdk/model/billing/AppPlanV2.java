package com.shopify.sdk.model.billing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.ID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppPlanV2 {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("displayName")
    private String displayName;
    
    @JsonProperty("pricingDetails")
    private AppPricingDetails pricingDetails;
}