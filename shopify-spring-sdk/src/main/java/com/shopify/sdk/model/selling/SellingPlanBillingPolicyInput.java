package com.shopify.sdk.model.selling;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for a selling plan billing policy.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanBillingPolicyInput {
    /**
     * The billing interval.
     */
    @JsonProperty("interval")
    private SellingPlanInterval interval;
    
    /**
     * The interval count.
     */
    @JsonProperty("intervalCount")
    private Integer intervalCount;
}