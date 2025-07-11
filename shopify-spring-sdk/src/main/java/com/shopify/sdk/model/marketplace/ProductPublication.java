package com.shopify.sdk.model.marketplace;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import com.shopify.sdk.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a product publication
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPublication {
    /**
     * The ID of the product publication
     */
    @JsonProperty("id")
    private ID id;
    
    /**
     * Whether the product is published
     */
    @JsonProperty("isPublished")
    private Boolean isPublished;
    
    /**
     * The product being published
     */
    @JsonProperty("product")
    private Product product;
    
    /**
     * When the product was published
     */
    @JsonProperty("publishDate")
    private DateTime publishDate;
}