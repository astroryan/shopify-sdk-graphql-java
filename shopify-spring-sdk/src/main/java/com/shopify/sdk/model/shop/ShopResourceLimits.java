package com.shopify.sdk.model.shop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the resource limits for a shop.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopResourceLimits {
    /**
     * The maximum number of locations allowed.
     */
    @JsonProperty("locationLimit")
    private Integer locationLimit;
    
    /**
     * The maximum number of product options allowed.
     */
    @JsonProperty("maxProductOptions")
    private Integer maxProductOptions;
    
    /**
     * The maximum number of product variants allowed.
     */
    @JsonProperty("maxProductVariants")
    private Integer maxProductVariants;
    
    /**
     * Whether the shop has reached the redirect limit.
     */
    @JsonProperty("redirectLimitReached")
    private Boolean redirectLimitReached;
    
    /**
     * The SKU resource limits.
     */
    @JsonProperty("skuResourceLimits")
    private SkuResourceLimits skuResourceLimits;
}