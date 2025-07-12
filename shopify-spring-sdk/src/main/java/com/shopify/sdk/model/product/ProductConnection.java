package com.shopify.sdk.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a connection to a list of products.
 * Implements the Relay connection pattern for pagination.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductConnection {
    /**
     * A list of edges containing the nodes
     */
    @JsonProperty("edges")
    private List<ProductEdge> edges;
    
    /**
     * A list of the nodes contained in ProductEdge
     */
    @JsonProperty("nodes")
    private List<Product> nodes;
    
    /**
     * Information to aid in pagination
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
    
    /**
     * The total count of products in the connection
     */
    @JsonProperty("totalCount")
    private Integer totalCount;
}

/**
 * An edge in a connection to a Product
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ProductEdge {
    /**
     * A cursor for use in pagination
     */
    @JsonProperty("cursor")
    private String cursor;
    
    /**
     * The item at the end of ProductEdge
     */
    @JsonProperty("node")
    private Product node;
}