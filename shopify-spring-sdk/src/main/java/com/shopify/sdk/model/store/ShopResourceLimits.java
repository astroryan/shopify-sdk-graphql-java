package com.shopify.sdk.model.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Resource limits for a shop.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopResourceLimits {
    /**
     * The maximum number of locations allowed
     */
    private Integer locationLimit;
    
    /**
     * The maximum number of product options allowed
     */
    private Integer maxProductOptions;
    
    /**
     * The maximum number of product variants allowed
     */
    private Integer maxProductVariants;
    
    /**
     * Whether the shop has reached the limit of locations
     */
    private Boolean hasReachedLocationLimit;
}