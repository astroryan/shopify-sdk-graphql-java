package com.shopify.sdk.model.onlinestore;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for updating a blog.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogUpdateInput {
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
}