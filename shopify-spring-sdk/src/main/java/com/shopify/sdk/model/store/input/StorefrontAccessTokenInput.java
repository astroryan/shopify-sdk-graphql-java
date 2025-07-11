package com.shopify.sdk.model.store.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for creating a storefront access token.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorefrontAccessTokenInput {
    /**
     * The title of the storefront access token
     */
    private String title;
    
    /**
     * The access scopes to grant to the token
     */
    private List<String> accessScopes;
}