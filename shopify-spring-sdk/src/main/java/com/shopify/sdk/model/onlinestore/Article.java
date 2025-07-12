package com.shopify.sdk.model.onlinestore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import com.shopify.sdk.model.common.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Represents a blog article.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The title of the article.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The handle of the article.
     */
    @JsonProperty("handle")
    private String handle;
    
    /**
     * The content of the article.
     */
    @JsonProperty("content")
    private String content;
    
    /**
     * The content HTML of the article.
     */
    @JsonProperty("contentHtml")
    private String contentHtml;
    
    /**
     * The summary of the article.
     */
    @JsonProperty("summary")
    private String summary;
    
    /**
     * The author of the article.
     */
    @JsonProperty("authorV2")
    private ArticleAuthor authorV2;
    
    /**
     * The blog that contains the article.
     */
    @JsonProperty("blog")
    private Blog blog;
    
    /**
     * The tags associated with the article.
     */
    @JsonProperty("tags")
    private List<String> tags;
    
    /**
     * Whether the article is published.
     */
    @JsonProperty("isPublished")
    private Boolean isPublished;
    
    /**
     * The date and time when the article was published.
     */
    @JsonProperty("publishedAt")
    private OffsetDateTime publishedAt;
    
    /**
     * The date and time when the article was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * The date and time when the article was last modified.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
    
    /**
     * The image associated with the article.
     */
    @JsonProperty("image")
    private Image image;
}