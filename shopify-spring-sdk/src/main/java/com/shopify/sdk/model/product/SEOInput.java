package com.shopify.sdk.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SEO information for a product.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SEOInput {
    /**
     * The SEO title.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The SEO description.
     */
    @JsonProperty("description")
    private String description;
}