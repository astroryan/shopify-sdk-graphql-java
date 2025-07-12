package com.shopify.sdk.model.localization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A connection to translatable resources.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslatableResourceConnection {
    /**
     * The edges in the connection.
     */
    @JsonProperty("edges")
    private List<TranslatableResourceEdge> edges;
    
    /**
     * The nodes in the connection.
     */
    @JsonProperty("nodes")
    private List<TranslatableResource> nodes;
    
    /**
     * Information about pagination.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}