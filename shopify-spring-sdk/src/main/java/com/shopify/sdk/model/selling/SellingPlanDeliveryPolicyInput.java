package com.shopify.sdk.model.selling;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for a selling plan delivery policy.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanDeliveryPolicyInput {
    /**
     * The delivery interval.
     */
    @JsonProperty("interval")
    private SellingPlanInterval interval;
    
    /**
     * The interval count.
     */
    @JsonProperty("intervalCount")
    private Integer intervalCount;
}