package com.shopify.sdk.model.selling;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for creating or updating a selling plan.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanInput {
    /**
     * The ID of the selling plan (for updates).
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The name of the selling plan.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The options for the selling plan.
     */
    @JsonProperty("options")
    private List<String> options;
    
    /**
     * The position of the selling plan.
     */
    @JsonProperty("position")
    private Integer position;
    
    /**
     * The billing policy.
     */
    @JsonProperty("billingPolicy")
    private SellingPlanBillingPolicyInput billingPolicy;
    
    /**
     * The delivery policy.
     */
    @JsonProperty("deliveryPolicy")
    private SellingPlanDeliveryPolicyInput deliveryPolicy;
    
    /**
     * The pricing policies.
     */
    @JsonProperty("pricingPolicies")
    private List<SellingPlanPricingPolicyInput> pricingPolicies;
}