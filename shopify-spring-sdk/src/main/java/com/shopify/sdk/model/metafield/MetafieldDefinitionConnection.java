package com.shopify.sdk.model.metafield;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a connection to a list of metafield definitions.
 * Implements the Relay connection pattern for pagination.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldDefinitionConnection {
    /**
     * A list of edges containing the nodes
     */
    @JsonProperty("edges")
    private List<MetafieldDefinitionEdge> edges;
    
    /**
     * A list of the nodes contained in MetafieldDefinitionEdge
     */
    @JsonProperty("nodes")
    private List<MetafieldDefinition> nodes;
    
    /**
     * Information to aid in pagination
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
    
    /**
     * The total count of metafield definitions in the connection
     */
    @JsonProperty("totalCount")
    private Integer totalCount;
}

/**
 * An edge in a connection to a MetafieldDefinition
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MetafieldDefinitionEdge {
    /**
     * A cursor for use in pagination
     */
    @JsonProperty("cursor")
    private String cursor;
    
    /**
     * The item at the end of MetafieldDefinitionEdge
     */
    @JsonProperty("node")
    private MetafieldDefinition node;
}