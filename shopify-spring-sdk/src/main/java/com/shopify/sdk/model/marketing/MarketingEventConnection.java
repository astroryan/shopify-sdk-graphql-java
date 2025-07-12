package com.shopify.sdk.model.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A connection to marketing events.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingEventConnection {
    /**
     * A list of edges.
     */
    @JsonProperty("edges")
    private List<MarketingEventEdge> edges;
    
    /**
     * A list of nodes.
     */
    @JsonProperty("nodes")
    private List<MarketingEvent> nodes;
    
    /**
     * Pagination information.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}