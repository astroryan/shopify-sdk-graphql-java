package com.shopify.sdk.model.shop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SKU resource limits.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuResourceLimits {
    /**
     * The quantity available.
     */
    @JsonProperty("quantityAvailable")
    private Integer quantityAvailable;
    
    /**
     * The quantity limit.
     */
    @JsonProperty("quantityLimit")
    private Integer quantityLimit;
    
    /**
     * The quantity used.
     */
    @JsonProperty("quantityUsed")
    private Integer quantityUsed;
}