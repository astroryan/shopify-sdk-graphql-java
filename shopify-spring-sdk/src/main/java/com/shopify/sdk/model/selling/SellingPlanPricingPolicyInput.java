package com.shopify.sdk.model.selling;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.MoneyInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for a selling plan pricing policy.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanPricingPolicyInput {
    /**
     * The fixed discount amount.
     */
    @JsonProperty("fixed")
    private MoneyInput fixed;
    
    /**
     * The percentage discount.
     */
    @JsonProperty("percentage")
    private Double percentage;
}