package com.shopify.sdk.model.onlinestore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A connection to pages.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageConnection {
    /**
     * The edges in the connection.
     */
    @JsonProperty("edges")
    private List<PageEdge> edges;
    
    /**
     * The nodes in the connection.
     */
    @JsonProperty("nodes")
    private List<Page> nodes;
    
    /**
     * Information about pagination.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}