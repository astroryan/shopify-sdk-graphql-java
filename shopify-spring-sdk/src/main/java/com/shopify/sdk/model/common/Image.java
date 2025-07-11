package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an image in the Shopify API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    
    /**
     * A word or phrase to share the nature or contents of an image.
     */
    @JsonProperty("altText")
    private String altText;
    
    /**
     * The original height of the image in pixels. Returns null if the image is not hosted by Shopify.
     */
    @JsonProperty("height")
    private Integer height;
    
    /**
     * A unique identifier for the image.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The location of the original image as a URL.
     */
    @JsonProperty("originalSrc")
    private String originalSrc;
    
    /**
     * The location of the transformed image as a URL.
     */
    @JsonProperty("transformedSrc")
    private String transformedSrc;
    
    /**
     * The location of the image as a URL.
     */
    @JsonProperty("url")
    private String url;
    
    /**
     * The original width of the image in pixels. Returns null if the image is not hosted by Shopify.
     */
    @JsonProperty("width")
    private Integer width;
}