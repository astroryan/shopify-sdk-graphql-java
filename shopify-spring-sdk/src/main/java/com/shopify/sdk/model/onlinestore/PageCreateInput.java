package com.shopify.sdk.model.onlinestore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.SEOInput;
import com.shopify.sdk.model.metafield.MetafieldInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for creating a page.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageCreateInput {
    /**
     * The title of the page.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The handle of the page.
     */
    @JsonProperty("handle")
    private String handle;
    
    /**
     * The body content of the page.
     */
    @JsonProperty("body")
    private String body;
    
    /**
     * Whether the page is published.
     */
    @JsonProperty("published")
    private Boolean published;
    
    /**
     * The date and time when the page was published.
     */
    @JsonProperty("publishedAt")
    private String publishedAt;
    
    /**
     * The SEO information for the page.
     */
    @JsonProperty("seo")
    private SEOInput seo;
    
    /**
     * The metafields to associate with the page.
     */
    @JsonProperty("metafields")
    private List<MetafieldInput> metafields;
}