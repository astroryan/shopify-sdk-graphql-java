package com.shopify.sdk.model.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A connection to consent records.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsentRecordConnection {
    /**
     * A list of edges.
     */
    @JsonProperty("edges")
    private List<ConsentRecordEdge> edges;
    
    /**
     * A list of nodes.
     */
    @JsonProperty("nodes")
    private List<ConsentRecord> nodes;
    
    /**
     * Pagination information.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}