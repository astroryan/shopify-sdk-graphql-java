package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an edge in a GraphQL connection
 * @param <T> The type of the node
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Edge<T> {
    /**
     * The cursor for this edge
     */
    @JsonProperty("cursor")
    private String cursor;
    
    /**
     * The node at this edge
     */
    @JsonProperty("node")
    private T node;
}