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
 * Input for updating an article.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdateInput {
    /**
     * The title of the article.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The content of the article.
     */
    @JsonProperty("content")
    private String content;
    
    /**
     * The summary of the article.
     */
    @JsonProperty("summary")
    private String summary;
    
    /**
     * The handle of the article.
     */
    @JsonProperty("handle")
    private String handle;
    
    /**
     * The author of the article.
     */
    @JsonProperty("authorEmail")
    private String authorEmail;
    
    /**
     * The tags associated with the article.
     */
    @JsonProperty("tags")
    private List<String> tags;
    
    /**
     * Whether the article is published.
     */
    @JsonProperty("published")
    private Boolean published;
    
    /**
     * The date and time when the article was published.
     */
    @JsonProperty("publishedAt")
    private String publishedAt;
    
    /**
     * The SEO information for the article.
     */
    @JsonProperty("seo")
    private SEOInput seo;
    
    /**
     * The metafields to associate with the article.
     */
    @JsonProperty("metafields")
    private List<MetafieldInput> metafields;
}