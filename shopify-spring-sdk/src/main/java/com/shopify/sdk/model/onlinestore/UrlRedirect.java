package com.shopify.sdk.model.onlinestore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a URL redirect.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlRedirect implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The path to redirect from.
     */
    @JsonProperty("path")
    private String path;
    
    /**
     * The target URL.
     */
    @JsonProperty("target")
    private String target;
}