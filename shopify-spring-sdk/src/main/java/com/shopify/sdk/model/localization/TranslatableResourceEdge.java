package com.shopify.sdk.model.localization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * An edge in a translatable resource connection.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslatableResourceEdge {
    /**
     * The cursor for pagination.
     */
    @JsonProperty("cursor")
    private String cursor;
    
    /**
     * The node.
     */
    @JsonProperty("node")
    private TranslatableResource node;
}