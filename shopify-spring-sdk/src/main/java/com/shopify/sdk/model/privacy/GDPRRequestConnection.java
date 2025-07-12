package com.shopify.sdk.model.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A connection to GDPR requests.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GDPRRequestConnection {
    /**
     * A list of edges.
     */
    @JsonProperty("edges")
    private List<GDPRRequestEdge> edges;
    
    /**
     * A list of nodes.
     */
    @JsonProperty("nodes")
    private List<GDPRRequest> nodes;
    
    /**
     * Pagination information.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}