package com.shopify.sdk.model.b2b;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A connection to company locations.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyLocationConnection {
    /**
     * The edges in the connection.
     */
    @JsonProperty("edges")
    private List<CompanyLocationEdge> edges;
    
    /**
     * The nodes in the connection.
     */
    @JsonProperty("nodes")
    private List<CompanyLocation> nodes;
    
    /**
     * Information about pagination.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}