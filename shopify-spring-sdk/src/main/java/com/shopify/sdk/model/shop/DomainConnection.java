package com.shopify.sdk.model.shop;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A connection to domains.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainConnection {
    /**
     * The edges in the connection.
     */
    @JsonProperty("edges")
    private List<DomainEdge> edges;
    
    /**
     * The nodes in the connection.
     */
    @JsonProperty("nodes")
    private List<Domain> nodes;
    
    /**
     * Information about pagination.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}