package com.shopify.sdk.model.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The shop's billing plan.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopPlan {
    /**
     * The display name of the plan
     */
    private String displayName;
    
    /**
     * Whether this is a partner development plan
     */
    private Boolean partnerDevelopment;
    
    /**
     * Whether this is a Shopify Plus plan
     */
    private Boolean shopifyPlus;
}