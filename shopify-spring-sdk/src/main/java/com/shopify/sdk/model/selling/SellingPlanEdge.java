package com.shopify.sdk.model.selling;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * An edge in a selling plan connection.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanEdge {
    /**
     * The cursor for pagination.
     */
    @JsonProperty("cursor")
    private String cursor;
    
    /**
     * The node.
     */
    @JsonProperty("node")
    private SellingPlan node;
}