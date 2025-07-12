package com.shopify.sdk.model.selling;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.MoneyV2;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a pricing policy for a selling plan.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanPricingPolicy {
    /**
     * The fixed discount amount.
     */
    @JsonProperty("fixed")
    private MoneyV2 fixed;
    
    /**
     * The percentage discount.
     */
    @JsonProperty("percentage")
    private Double percentage;
    
    /**
     * The after cycle.
     */
    @JsonProperty("afterCycle")
    private Integer afterCycle;
}