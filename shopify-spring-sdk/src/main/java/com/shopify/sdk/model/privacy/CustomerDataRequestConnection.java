package com.shopify.sdk.model.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A connection to customer data requests.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDataRequestConnection {
    /**
     * A list of edges.
     */
    @JsonProperty("edges")
    private List<CustomerDataRequestEdge> edges;
    
    /**
     * A list of nodes.
     */
    @JsonProperty("nodes")
    private List<CustomerDataRequest> nodes;
    
    /**
     * Pagination information.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}