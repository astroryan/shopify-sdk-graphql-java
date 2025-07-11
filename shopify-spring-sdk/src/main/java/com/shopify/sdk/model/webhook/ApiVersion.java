package com.shopify.sdk.model.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Shopify API version
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiVersion {
    /**
     * The display name of the API version
     */
    @JsonProperty("displayName")
    private String displayName;
    
    /**
     * The handle/identifier of the API version (e.g., "2024-01")
     */
    @JsonProperty("handle")
    private String handle;
    
    /**
     * Whether this API version is stable
     */
    @JsonProperty("stable")
    private Boolean stable;
    
    /**
     * Whether this API version is supported
     */
    @JsonProperty("supported")
    private Boolean supported;
}