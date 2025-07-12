package com.shopify.sdk.model.metaobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A connection to metaobjects.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectConnection {
    /**
     * A list of edges.
     */
    @JsonProperty("edges")
    private List<MetaobjectEdge> edges;
    
    /**
     * A list of nodes.
     */
    @JsonProperty("nodes")
    private List<Metaobject> nodes;
    
    /**
     * Pagination information.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}