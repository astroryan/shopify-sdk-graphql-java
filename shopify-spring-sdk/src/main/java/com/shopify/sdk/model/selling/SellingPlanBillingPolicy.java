package com.shopify.sdk.model.selling;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the billing policy for a selling plan.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanBillingPolicy {
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
    
    /**
     * The anchors for the billing policy.
     */
    @JsonProperty("anchors")
    private SellingPlanAnchor anchors;
}