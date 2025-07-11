package com.shopify.sdk.model.store.input;

import com.shopify.sdk.model.store.ShopPolicyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for creating a shop policy.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopPolicyInput {
    /**
     * The type of policy
     */
    private ShopPolicyType type;
    
    /**
     * The title of the policy
     */
    private String title;
    
    /**
     * The body/content of the policy
     */
    private String body;
}