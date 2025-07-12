package com.shopify.sdk.model.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A connection to inventory levels.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryLevelConnection {
    /**
     * The edges in the connection.
     */
    @JsonProperty("edges")
    private List<InventoryLevelEdge> edges;
    
    /**
     * The nodes in the connection.
     */
    @JsonProperty("nodes")
    private List<InventoryLevel> nodes;
    
    /**
     * Information about pagination.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}