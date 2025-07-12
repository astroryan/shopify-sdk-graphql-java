package com.shopify.sdk.model.metafield;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for setting metafields.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldsSetInput {
    /**
     * The metafields to be set.
     */
    @JsonProperty("metafields")
    private List<MetafieldInput> metafields;
}

/**
 * Input for a single metafield.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MetafieldInput {
    /**
     * The unique identifier of the metafield. Used to update an existing metafield.
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
    
    /**
     * The ID of the owner resource.
     */
    @JsonProperty("ownerId")
    private String ownerId;
}