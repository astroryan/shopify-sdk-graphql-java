package com.shopify.sdk.model.shop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a shop's subscription plan.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopPlan {
    /**
     * The name of the plan.
     */
    @JsonProperty("displayName")
    private String displayName;
    
    /**
     * Whether the plan is a partner development plan.
     */
    @JsonProperty("partnerDevelopment")
    private Boolean partnerDevelopment;
    
    /**
     * Whether the shop is on a paid plan.
     */
    @JsonProperty("shopifyPlus")
    private Boolean shopifyPlus;
}