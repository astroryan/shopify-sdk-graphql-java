package com.shopify.sdk.model.marketplace;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a connection to publications
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicationConnection {
    /**
     * The edges in the connection
     */
    @JsonProperty("edges")
    private List<PublicationEdge> edges;
    
    /**
     * The nodes in the connection
     */
    @JsonProperty("nodes")
    private List<Publication> nodes;
    
    /**
     * Page information
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

/**
 * Represents an edge to a publication
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class PublicationEdge {
    @JsonProperty("cursor")
    private String cursor;
    
    @JsonProperty("node")
    private Publication node;
}