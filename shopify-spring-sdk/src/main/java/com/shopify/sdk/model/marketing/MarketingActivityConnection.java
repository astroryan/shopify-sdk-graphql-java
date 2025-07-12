package com.shopify.sdk.model.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A connection to marketing activities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingActivityConnection {
    /**
     * A list of edges.
     */
    @JsonProperty("edges")
    private List<MarketingActivityEdge> edges;
    
    /**
     * A list of nodes.
     */
    @JsonProperty("nodes")
    private List<MarketingActivity> nodes;
    
    /**
     * Pagination information.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}