package com.shopify.sdk.model.metafield;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for creating or updating a metafield.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldInput {
    /**
     * The ID of the metafield (for updates).
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The namespace of the metafield.
     */
    @JsonProperty("namespace")
    private String namespace;
    
    /**
     * The key of the metafield.
     */
    @JsonProperty("key")
    private String key;
    
    /**
     * The value of the metafield.
     */
    @JsonProperty("value")
    private String value;
    
    /**
     * The type of the metafield value.
     */
    @JsonProperty("type")
    private String type;
}