package com.shopify.sdk.model.shop;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents a policy for a shop.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopPolicy implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The body of the policy.
     */
    @JsonProperty("body")
    private String body;
    
    /**
     * The title of the policy.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The type of the policy.
     */
    @JsonProperty("type")
    private ShopPolicyType type;
    
    /**
     * The URL of the policy.
     */
    @JsonProperty("url")
    private String url;
    
    /**
     * When the policy was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * When the policy was last updated.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
}