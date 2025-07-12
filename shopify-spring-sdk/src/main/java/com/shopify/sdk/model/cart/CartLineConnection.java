package com.shopify.sdk.model.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A connection to cart lines.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartLineConnection {
    /**
     * The edges in the connection.
     */
    @JsonProperty("edges")
    private List<CartLineEdge> edges;
    
    /**
     * The nodes in the connection.
     */
    @JsonProperty("nodes")
    private List<CartLine> nodes;
    
    /**
     * Information about pagination.
     */
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}