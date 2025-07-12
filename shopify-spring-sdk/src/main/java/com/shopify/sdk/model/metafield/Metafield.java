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
 * Represents a metafield, which is a custom field that extends Shopify resources.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Metafield implements Node {
    /**
     * A globally unique identifier.
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
     * The description of the metafield.
     */
    @JsonProperty("description")
    private String description;
    
    /**
     * The date and time when the metafield was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * The date and time when the metafield was last updated.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
    
    /**
     * The definition of the metafield.
     */
    @JsonProperty("definition")
    private MetafieldDefinition definition;
    
    /**
     * The owner resource of the metafield.
     */
    @JsonProperty("owner")
    private MetafieldOwner owner;
    
    /**
     * The owner type of the metafield.
     */
    @JsonProperty("ownerType")
    private MetafieldOwnerType ownerType;
    
    /**
     * The legacy resource ID of the metafield.
     */
    @JsonProperty("legacyResourceId")
    private String legacyResourceId;
    
    /**
     * References to other resources.
     */
    @JsonProperty("references")
    private MetafieldReferenceConnection references;
}

/**
 * The owner of a metafield.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MetafieldOwner {
    /**
     * The globally unique identifier of the owner.
     */
    @JsonProperty("id")
    private String id;
}


/**
 * A connection to metafield references.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MetafieldReferenceConnection {
    /**
     * The edges in the connection.
     */
    @JsonProperty("edges")
    private List<MetafieldReferenceEdge> edges;
    
    /**
     * The nodes in the connection.
     */
    @JsonProperty("nodes")
    private List<MetafieldReference> nodes;
}

/**
 * An edge in a metafield reference connection.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MetafieldReferenceEdge {
    /**
     * The cursor for pagination.
     */
    @JsonProperty("cursor")
    private String cursor;
    
    /**
     * The metafield reference node.
     */
    @JsonProperty("node")
    private MetafieldReference node;
}

/**
 * A reference from a metafield to another resource.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MetafieldReference {
    /**
     * The globally unique identifier of the referenced resource.
     */
    @JsonProperty("id")
    private String id;
}