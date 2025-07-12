package com.shopify.sdk.model.onlinestore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents an online store page.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Page implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
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
     * The summary of the page body.
     */
    @JsonProperty("bodySummary")
    private String bodySummary;
    
    /**
     * The author of the page.
     */
    @JsonProperty("author")
    private String author;
    
    /**
     * Whether the page is published.
     */
    @JsonProperty("isPublished")
    private Boolean isPublished;
    
    /**
     * The date and time when the page was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * The date and time when the page was last modified.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
    
    /**
     * The date and time when the page was published.
     */
    @JsonProperty("publishedAt")
    private OffsetDateTime publishedAt;
    
    /**
     * The suffix of the page's URL.
     */
    @JsonProperty("suffix")
    private String suffix;
    
    /**
     * The template suffix of the page.
     */
    @JsonProperty("templateSuffix")
    private String templateSuffix;
}