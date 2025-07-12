package com.shopify.sdk.model.billing;

import com.shopify.sdk.model.common.Connection;
import com.shopify.sdk.model.common.Edge;
import com.shopify.sdk.model.common.PageInfo;

import java.util.List;
import java.util.stream.Collectors;

public class AppSubscriptionConnection extends Connection<AppSubscription> {
    
    private List<AppSubscriptionEdge> appSubscriptionEdges;
    private PageInfo pageInfo;
    
    @Override
    public List<Edge<AppSubscription>> getEdges() {
        return appSubscriptionEdges != null ? 
            appSubscriptionEdges.stream().map(edge -> (Edge<AppSubscription>) edge).collect(Collectors.toList()) : 
            null;
    }
    
    @Override
    public void setEdges(List<Edge<AppSubscription>> edges) {
        this.appSubscriptionEdges = edges != null ?
            edges.stream().map(edge -> (AppSubscriptionEdge) edge).collect(Collectors.toList()) :
            null;
    }
    
    public List<AppSubscriptionEdge> getAppSubscriptionEdges() {
        return appSubscriptionEdges;
    }
    
    public void setAppSubscriptionEdges(List<AppSubscriptionEdge> edges) {
        this.appSubscriptionEdges = edges;
    }
    
    @Override
    public PageInfo getPageInfo() {
        return pageInfo;
    }
    
    @Override
    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
    
    public static class AppSubscriptionEdge extends Edge<AppSubscription> {
        private String cursor;
        private AppSubscription node;
        
        @Override
        public String getCursor() {
            return cursor;
        }
        
        @Override
        public void setCursor(String cursor) {
            this.cursor = cursor;
        }
        
        @Override
        public AppSubscription getNode() {
            return node;
        }
        
        @Override
        public void setNode(AppSubscription node) {
            this.node = node;
        }
    }
}