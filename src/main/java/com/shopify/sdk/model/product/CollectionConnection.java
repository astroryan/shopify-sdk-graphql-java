package com.shopify.sdk.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Connection;
import com.shopify.sdk.model.common.Edge;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * An auto-generated type for paginating through multiple Collections.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CollectionConnection extends Connection<Collection> {
    
    /**
     * A list of edges.
     */
    @JsonProperty("edges")
    private List<CollectionEdge> edges;
    
    /**
     * A list of the nodes contained in CollectionEdge.
     */
    @JsonProperty("nodes")
    private List<Collection> nodes;
    
    /**
     * Information to aid in pagination.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
    
    @Override
    public List<Edge<Collection>> getEdges() {
        return edges != null ? List.copyOf(edges) : List.of();
    }
    
    public List<CollectionEdge> getCollectionEdges() {
        return edges;
    }
    
    public void setCollectionEdges(List<CollectionEdge> edges) {
        this.edges = edges;
    }
    
    public List<Collection> getNodes() {
        return nodes;
    }
    
    public void setNodes(List<Collection> nodes) {
        this.nodes = nodes;
    }
    
    public PageInfo getPageInfo() {
        return pageInfo;
    }
    
    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}