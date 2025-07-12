package com.shopify.sdk.model.selling;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the delivery policy for a selling plan.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanDeliveryPolicy {
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
    
    /**
     * The anchors for the delivery policy.
     */
    @JsonProperty("anchors")
    private SellingPlanAnchor anchors;
}