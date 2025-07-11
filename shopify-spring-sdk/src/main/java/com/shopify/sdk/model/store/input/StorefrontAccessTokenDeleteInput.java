package com.shopify.sdk.model.store.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for deleting a storefront access token.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorefrontAccessTokenDeleteInput {
    /**
     * The ID of the storefront access token to delete
     */
    private String id;
}