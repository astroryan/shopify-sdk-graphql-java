package com.shopify.sdk.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.shopify.sdk.model.common.Connection;
import com.shopify.sdk.model.common.Edge;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * An auto-generated type for paginating through multiple Products.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductConnection extends Connection<Product> {
    
    /**
     * A list of edges.
     */
    @JsonProperty("edges")
    private List<ProductEdge> edges;
    
    /**
     * A list of the nodes contained in ProductEdge.
     */
    @JsonProperty("nodes")
    private List<Product> nodes;
    
    /**
     * Information to aid in pagination.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
    
    @Override
    @JsonIgnore
    public List<Edge<Product>> getEdges() {
        return edges != null ? List.copyOf(edges) : List.of();
    }
    
    public List<ProductEdge> getProductEdges() {
        return edges;
    }
    
    @JsonSetter("edges")
    public void setProductEdges(List<ProductEdge> edges) {
        this.edges = edges;
    }
    
    public List<Product> getNodes() {
        return nodes;
    }
    
    public void setNodes(List<Product> nodes) {
        this.nodes = nodes;
    }
    
    public PageInfo getPageInfo() {
        return pageInfo;
    }
    
    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}