package com.shopify.sdk.model.shop;

import com.fasterxml.jackson.annotation.JsonProperty;
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
     * The ID of the shop policy to update.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The updated body of the shop policy.
     */
    @JsonProperty("body")
    private String body;
}