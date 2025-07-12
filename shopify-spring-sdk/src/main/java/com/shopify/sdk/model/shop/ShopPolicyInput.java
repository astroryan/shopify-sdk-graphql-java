package com.shopify.sdk.model.shop;

import com.fasterxml.jackson.annotation.JsonProperty;
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
     * The type of the shop policy.
     */
    @JsonProperty("type")
    private ShopPolicyType type;
    
    /**
     * The body of the shop policy.
     */
    @JsonProperty("body")
    private String body;
}