package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.ID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanInput {
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("options")
    private List<String> options;
    
    @JsonProperty("position")
    private Integer position;
    
    @JsonProperty("billingPolicy")
    private SellingPlanBillingPolicyInput billingPolicy;
    
    @JsonProperty("deliveryPolicy")
    private SellingPlanDeliveryPolicyInput deliveryPolicy;
    
    @JsonProperty("pricingPolicies")
    private List<SellingPlanPricingPolicyInput> pricingPolicies;
}