package com.shopify.sdk.model.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.shopify.sdk.model.common.Connection;
import com.shopify.sdk.model.common.Edge;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * An auto-generated type for paginating through multiple Orders.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderConnection extends Connection<Order> {
    
    /**
     * A list of edges.
     */
    @JsonProperty("edges")
    private List<OrderEdge> edges;
    
    /**
     * A list of the nodes contained in OrderEdge.
     */
    @JsonProperty("nodes")
    private List<Order> nodes;
    
    /**
     * Information to aid in pagination.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
    
    @Override
    @JsonIgnore
    public List<Edge<Order>> getEdges() {
        return edges != null ? List.copyOf(edges) : List.of();
    }
    
    public List<OrderEdge> getOrderEdges() {
        return edges;
    }
    
    @JsonSetter("edges")
    public void setOrderEdges(List<OrderEdge> edges) {
        this.edges = edges;
    }
    
    // Manual setters to avoid conflicts
    public void setNodes(List<Order> nodes) {
        this.nodes = nodes;
    }
    
    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}