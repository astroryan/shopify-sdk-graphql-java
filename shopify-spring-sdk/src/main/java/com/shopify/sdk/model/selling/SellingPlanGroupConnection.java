package com.shopify.sdk.model.selling;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A connection to selling plan groups.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanGroupConnection {
    /**
     * The edges in the connection.
     */
    @JsonProperty("edges")
    private List<SellingPlanGroupEdge> edges;
    
    /**
     * The nodes in the connection.
     */
    @JsonProperty("nodes")
    private List<SellingPlanGroup> nodes;
    
    /**
     * Information about pagination.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}