package com.shopify.sdk.model.storefront;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Represents a storefront access token.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorefrontAccessToken implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The access token string.
     */
    @JsonProperty("accessToken")
    private String accessToken;
    
    /**
     * The scopes granted to the access token.
     */
    @JsonProperty("accessScopes")
    private List<StorefrontAccessScope> accessScopes;
    
    /**
     * The date and time when the access token was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * The title of the access token.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The date and time when the access token was last updated.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
}

/**
 * Represents a storefront access scope.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class StorefrontAccessScope {
    /**
     * The handle of the access scope.
     */
    @JsonProperty("handle")
    private String handle;
    
    /**
     * The description of the access scope.
     */
    @JsonProperty("description")
    private String description;
}