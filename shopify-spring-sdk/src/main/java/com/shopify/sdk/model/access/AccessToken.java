package com.shopify.sdk.model.access;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Represents an access token used for accessing the Shopify Admin API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken implements Node {
    /**
     * The globally unique identifier of the access token.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The access token string.
     */
    @JsonProperty("accessToken")
    private String accessToken;
    
    /**
     * The date and time when the access token was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * The date and time when the access token was last updated.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
    
    /**
     * The title or name associated with the access token.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The status of the access token.
     */
    @JsonProperty("status")
    private AccessTokenStatus status;
    
    /**
     * The scopes granted to the access token.
     */
    @JsonProperty("scopes")
    private List<AccessScope> scopes;
}

/**
 * The status of an access token.
 */
enum AccessTokenStatus {
    @JsonProperty("ACTIVE")
    ACTIVE,
    
    @JsonProperty("INACTIVE")
    INACTIVE,
    
    @JsonProperty("REVOKED")
    REVOKED
}