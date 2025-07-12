package com.shopify.sdk.model.order;

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
 * An auto-generated type for paginating through multiple OrderLineItems.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderLineItemConnection extends Connection<OrderLineItem> {
    
    /**
     * A list of edges.
     */
    @JsonProperty("edges")
    private List<OrderLineItemEdge> edges;
    
    /**
     * A list of the nodes contained in OrderLineItemEdge.
     */
    @JsonProperty("nodes")
    private List<OrderLineItem> nodes;
    
    /**
     * Information to aid in pagination.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
    
    @Override
    public List<Edge<OrderLineItem>> getEdges() {
        return edges != null ? List.copyOf(edges) : List.of();
    }
    
    public List<OrderLineItemEdge> getOrderLineItemEdges() {
        return edges;
    }
    
    public void setOrderLineItemEdges(List<OrderLineItemEdge> edges) {
        this.edges = edges;
    }
    
    public List<OrderLineItem> getNodes() {
        return nodes;
    }
    
    public void setNodes(List<OrderLineItem> nodes) {
        this.nodes = nodes;
    }
    
    public PageInfo getPageInfo() {
        return pageInfo;
    }
    
    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}