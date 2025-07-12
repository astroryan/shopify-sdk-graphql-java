package com.shopify.sdk.model.onlinestore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A connection to articles.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleConnection {
    /**
     * The edges in the connection.
     */
    @JsonProperty("edges")
    private List<ArticleEdge> edges;
    
    /**
     * The nodes in the connection.
     */
    @JsonProperty("nodes")
    private List<Article> nodes;
    
    /**
     * Information about pagination.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}