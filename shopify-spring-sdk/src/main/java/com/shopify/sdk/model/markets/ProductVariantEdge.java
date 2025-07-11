package com.shopify.sdk.model.markets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.product.ProductVariant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an edge in a product variant connection.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantEdge {
    
    /**
     * A cursor for use in pagination.
     */
    @JsonProperty("cursor")
    private String cursor;
    
    /**
     * The item at the end of ProductVariantEdge.
     */
    @JsonProperty("node")
    private ProductVariant node;
}