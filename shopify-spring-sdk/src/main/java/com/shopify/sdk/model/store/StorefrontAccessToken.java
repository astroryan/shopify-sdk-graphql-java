package com.shopify.sdk.model.store;

import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a storefront access token.
 * Storefront access tokens are used to access the Storefront API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorefrontAccessToken implements Node {
    /**
     * A globally-unique identifier for the storefront access token
     */
    private String id;
    
    /**
     * The title of the storefront access token
     */
    private String title;
    
    /**
     * The access token value
     */
    private String accessToken;
    
    /**
     * The access scopes granted to the token
     */
    private List<String> accessScopes;
    
    /**
     * When the token was created
     */
    private LocalDateTime createdAt;
    
    /**
     * When the token was last updated
     */
    private LocalDateTime updatedAt;
}