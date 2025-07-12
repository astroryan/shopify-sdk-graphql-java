package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a connection to a list of fulfillment orders.
 * Implements the Relay connection pattern for pagination.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentOrderConnection {
    /**
     * A list of edges containing the nodes
     */
    @JsonProperty("edges")
    private List<FulfillmentOrderEdge> edges;
    
    /**
     * A list of the nodes contained in FulfillmentOrderEdge
     */
    @JsonProperty("nodes")
    private List<FulfillmentOrder> nodes;
    
    /**
     * Information to aid in pagination
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
    
    /**
     * The total count of fulfillment orders in the connection
     */
    @JsonProperty("totalCount")
    private Integer totalCount;
}

/**
 * An edge in a connection to a FulfillmentOrder
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentOrderEdge {
    /**
     * A cursor for use in pagination
     */
    @JsonProperty("cursor")
    private String cursor;
    
    /**
     * The item at the end of FulfillmentOrderEdge
     */
    @JsonProperty("node")
    private FulfillmentOrder node;
}