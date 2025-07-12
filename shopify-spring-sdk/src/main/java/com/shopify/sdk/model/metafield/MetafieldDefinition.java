package com.shopify.sdk.model.metafield;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Represents a metafield definition, which defines the structure and validation rules for metafields.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldDefinition implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
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
    private MetafieldDefinitionType type;
    
    /**
     * The resource types that the metafield definition can be applied to.
     */
    @JsonProperty("ownerType")
    private MetafieldOwnerType ownerType;
    
    /**
     * The validation rules for values of metafields.
     */
    @JsonProperty("validations")
    private List<MetafieldDefinitionValidation> validations;
    
    /**
     * Whether metafields of this definition are visible in the Storefront API.
     */
    @JsonProperty("visibleToStorefrontApi")
    private Boolean visibleToStorefrontApi;
    
    /**
     * The date and time when the metafield definition was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * The date and time when the metafield definition was last updated.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
    
    /**
     * Whether the metafield definition is pinned.
     */
    @JsonProperty("pinnedPosition")
    private Integer pinnedPosition;
    
    /**
     * The metafields using this definition.
     */
    @JsonProperty("metafields")
    private MetafieldConnection metafields;
}

/**
 * A type of metafield definition.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MetafieldDefinitionType {
    /**
     * The name of the type.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The category of the type.
     */
    @JsonProperty("category")
    private String category;
}

/**
 * A validation for a metafield definition.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MetafieldDefinitionValidation {
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
    
    /**
     * The type of validation.
     */
    @JsonProperty("type")
    private String type;
}