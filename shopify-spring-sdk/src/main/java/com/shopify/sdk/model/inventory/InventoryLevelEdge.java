package com.shopify.sdk.model.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * An edge in an inventory level connection.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryLevelEdge {
    /**
     * The cursor for pagination.
     */
    @JsonProperty("cursor")
    private String cursor;
    
    /**
     * The node.
     */
    @JsonProperty("node")
    private InventoryLevel node;
}