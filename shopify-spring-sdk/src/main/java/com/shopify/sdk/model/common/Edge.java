package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic edge in a GraphQL connection.
 *
 * @param <T> the type of node in this edge
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edge<T> {
    
    /**
     * The item at the end of the edge.
     */
    @JsonProperty("node")
    private T node;
    
    /**
     * A cursor for use in pagination.
     */
    @JsonProperty("cursor")
    private String cursor;
}