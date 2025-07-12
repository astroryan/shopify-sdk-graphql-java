package com.shopify.sdk.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Edge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * An auto-generated type which holds one Collection and a cursor during pagination.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CollectionEdge extends Edge<Collection> {
    
    /**
     * The item at the end of CollectionEdge.
     */
    @JsonProperty("node")
    private Collection node;
    
    /**
     * A cursor for use in pagination.
     */
    @JsonProperty("cursor")
    private String cursor;
}