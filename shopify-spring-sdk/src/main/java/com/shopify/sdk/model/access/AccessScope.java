package com.shopify.sdk.model.access;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a scope or permission that a merchant has granted to an app.
 * Access scopes determine what parts of a merchant's store an app can access.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessScope {
    /**
     * A description of the access scope.
     */
    @JsonProperty("description")
    private String description;
    
    /**
     * A human-readable string representing the access scope.
     */
    @JsonProperty("handle")
    private String handle;
}