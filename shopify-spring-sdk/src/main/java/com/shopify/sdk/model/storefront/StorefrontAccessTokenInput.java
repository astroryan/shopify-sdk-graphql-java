package com.shopify.sdk.model.storefront;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for creating a storefront access token.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorefrontAccessTokenInput {
    /**
     * The title of the storefront access token.
     */
    @JsonProperty("title")
    private String title;
}