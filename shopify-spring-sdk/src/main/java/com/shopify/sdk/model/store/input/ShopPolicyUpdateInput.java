package com.shopify.sdk.model.store.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for updating a shop policy.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopPolicyUpdateInput {
    /**
     * The title of the policy
     */
    private String title;
    
    /**
     * The body/content of the policy
     */
    private String body;
}