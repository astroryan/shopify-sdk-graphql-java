package com.shopify.sdk.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.DateTime;
import com.shopify.sdk.model.common.HTML;
import com.shopify.sdk.model.common.ID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a collection of products.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Collection {
    
    /**
     * A globally-unique identifier.
     */
    @JsonProperty("id")
    private ID id;
    
    /**
     * The collection's name. Limit of 255 characters.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * A human-friendly unique string for the collection automatically generated from its title.
     */
    @JsonProperty("handle")
    private String handle;
    
    /**
     * Stripped description of the collection, single line with HTML tags removed.
     */
    @JsonProperty("description")
    private String description;
    
    /**
     * The description of the collection, complete with HTML formatting.
     */
    @JsonProperty("descriptionHtml")
    private HTML descriptionHtml;
    
    /**
     * The date and time when the collection was created.
     */
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    /**
     * The date and time when the collection was last modified.
     */
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    /**
     * The date and time when the collection was published to the channel.
     */
    @JsonProperty("publishedAt")
    private DateTime publishedAt;
    
    /**
     * The collection's SEO information.
     */
    @JsonProperty("seo")
    private SEO seo;
    
    /**
     * The storefront ID of the collection.
     */
    @JsonProperty("storefrontId")
    private ID storefrontId;
    
    /**
     * The theme template used when viewing the collection in a store.
     */
    @JsonProperty("templateSuffix")
    private String templateSuffix;
    
    /**
     * The number of products in the collection.
     */
    @JsonProperty("productsCount")
    private Integer productsCount;
    
    /**
     * Whether the collection is available on the Online Store channel.
     */
    @JsonProperty("availablePublicationCount")
    private Integer availablePublicationCount;
    
    /**
     * Whether the collection has any products.
     */
    @JsonProperty("hasProduct")
    private Boolean hasProduct;
    
    /**
     * The online store URL for the collection.
     */
    @JsonProperty("onlineStoreUrl")
    private String onlineStoreUrl;
    
    /**
     * The online store preview URL for the collection.
     */
    @JsonProperty("onlineStorePreviewUrl")
    private String onlineStorePreviewUrl;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SEO {
        
        @JsonProperty("title")
        private String title;
        
        @JsonProperty("description")
        private String description;
    }
}