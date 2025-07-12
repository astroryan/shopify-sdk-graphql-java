package com.shopify.sdk.model.onlinestore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A connection to blogs.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogConnection {
    /**
     * The edges in the connection.
     */
    @JsonProperty("edges")
    private List<BlogEdge> edges;
    
    /**
     * The nodes in the connection.
     */
    @JsonProperty("nodes")
    private List<Blog> nodes;
    
    /**
     * Information about pagination.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}