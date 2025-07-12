package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for SEO information.
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