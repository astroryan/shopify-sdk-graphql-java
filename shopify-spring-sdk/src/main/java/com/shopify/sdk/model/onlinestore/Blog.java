package com.shopify.sdk.model.onlinestore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents a blog.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Blog implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The title of the blog.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The handle of the blog.
     */
    @JsonProperty("handle")
    private String handle;
    
    /**
     * The articles in the blog.
     */
    @JsonProperty("articles")
    private ArticleConnection articles;
    
    /**
     * The date and time when the blog was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * The date and time when the blog was last modified.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
}