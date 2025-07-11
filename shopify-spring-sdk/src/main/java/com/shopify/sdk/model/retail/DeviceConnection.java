package com.shopify.sdk.model.retail;

import com.shopify.sdk.model.common.PageInfo;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.util.List;

/**
 * Connection type for devices.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceConnection {
    
    @GraphQLQuery(name = "edges", description = "The edges in the connection")
    private List<DeviceEdge> edges;
    
    @GraphQLQuery(name = "nodes", description = "The nodes in the connection")
    private List<Device> nodes;
    
    @GraphQLQuery(name = "pageInfo", description = "Information about pagination")
    private PageInfo pageInfo;
    
    /**
     * Edge type for devices
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceEdge {
        @GraphQLQuery(name = "cursor", description = "The cursor for the edge")
        private String cursor;
        
        @GraphQLQuery(name = "node", description = "The node at the edge")
        private Device node;
    }
}