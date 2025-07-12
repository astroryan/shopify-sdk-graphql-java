package com.shopify.sdk.model.metafield;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a connection to a list of metafields.
 * Implements the Relay connection pattern for pagination.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldConnection {
    /**
     * A list of edges containing the nodes
     */
    @JsonProperty("edges")
    private List<MetafieldEdge> edges;
    
    /**
     * A list of the nodes contained in MetafieldEdge
     */
    @JsonProperty("nodes")
    private List<Metafield> nodes;
    
    /**
     * Information to aid in pagination
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
    
    /**
     * The total count of metafields in the connection
     */
    @JsonProperty("totalCount")
    private Integer totalCount;
}

/**
 * An edge in a connection to a Metafield
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MetafieldEdge {
    /**
     * A cursor for use in pagination
     */
    @JsonProperty("cursor")
    private String cursor;
    
    /**
     * The item at the end of MetafieldEdge
     */
    @JsonProperty("node")
    private Metafield node;
}