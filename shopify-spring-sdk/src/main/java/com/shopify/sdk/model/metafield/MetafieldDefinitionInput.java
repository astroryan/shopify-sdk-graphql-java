package com.shopify.sdk.model.metafield;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for creating a metafield definition.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldDefinitionInput {
    /**
     * The namespace of the metafield definition.
     */
    @JsonProperty("namespace")
    private String namespace;
    
    /**
     * The key of the metafield definition.
     */
    @JsonProperty("key")
    private String key;
    
    /**
     * The human-readable name of the metafield definition.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The description of the metafield definition.
     */
    @JsonProperty("description")
    private String description;
    
    /**
     * The type of the metafield definition.
     */
    @JsonProperty("type")
    private String type;
    
    /**
     * The resource type that the metafield definition applies to.
     */
    @JsonProperty("ownerType")
    private MetafieldOwnerType ownerType;
    
    /**
     * The validation rules for the metafield definition.
     */
    @JsonProperty("validations")
    private List<MetafieldDefinitionValidationInput> validations;
    
    /**
     * Whether metafields of this definition should be visible in the Storefront API.
     */
    @JsonProperty("visibleToStorefrontApi")
    private Boolean visibleToStorefrontApi;
    
    /**
     * Whether the metafield definition should be pinned.
     */
    @JsonProperty("pin")
    private Boolean pin;
}

/**
 * Input for a metafield definition validation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MetafieldDefinitionValidationInput {
    /**
     * The name of the validation.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The value of the validation.
     */
    @JsonProperty("value")
    private String value;
}