package com.shopify.sdk.model.selling;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A connection to selling plans.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanConnection {
    /**
     * The edges in the connection.
     */
    @JsonProperty("edges")
    private List<SellingPlanEdge> edges;
    
    /**
     * The nodes in the connection.
     */
    @JsonProperty("nodes")
    private List<SellingPlan> nodes;
    
    /**
     * Information about pagination.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}