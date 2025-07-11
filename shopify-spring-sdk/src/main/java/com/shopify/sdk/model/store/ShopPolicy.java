package com.shopify.sdk.model.store;

import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents a shop policy.
 * Shop policies include terms of service, privacy policy, refund policy, etc.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopPolicy implements Node {
    /**
     * A globally-unique identifier for the shop policy
     */
    private String id;
    
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
    
    /**
     * The URL where the policy can be viewed
     */
    private String url;
    
    /**
     * When the policy was created
     */
    private LocalDateTime createdAt;
    
    /**
     * When the policy was last updated
     */
    private LocalDateTime updatedAt;
}