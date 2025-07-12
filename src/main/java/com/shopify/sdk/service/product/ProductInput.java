package com.shopify.sdk.service.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.product.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for creating or updating a product.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInput {
    
    /**
     * The product title.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The product handle (URL slug).
     */
    @JsonProperty("handle")
    private String handle;
    
    /**
     * The description of the product.
     */
    @JsonProperty("description")
    private String description;
    
    /**
     * The description of the product, complete with HTML formatting.
     */
    @JsonProperty("descriptionHtml")
    private String descriptionHtml;
    
    /**
     * The product type specified by the merchant.
     */
    @JsonProperty("productType")
    private String productType;
    
    /**
     * The name of the vendor of the product.
     */
    @JsonProperty("vendor")
    private String vendor;
    
    /**
     * A comma separated list of tags that have been added to the product.
     */
    @JsonProperty("tags")
    private List<String> tags;
    
    /**
     * The product's status.
     */
    @JsonProperty("status")
    private ProductStatus status;
    
    /**
     * The theme template used when viewing the product in a store.
     */
    @JsonProperty("templateSuffix")
    private String templateSuffix;
    
    /**
     * Whether the product is a gift card.
     */
    @JsonProperty("giftCard")
    private Boolean giftCard;
    
    /**
     * The product's SEO information.
     */
    @JsonProperty("seo")
    private SEOInput seo;
    
    /**
     * Whether this product is published on the Point of Sale channel.
     */
    @JsonProperty("collectionsToJoin")
    private List<String> collectionsToJoin;
    
    /**
     * A list of collections that the product will be removed from.
     */
    @JsonProperty("collectionsToLeave")
    private List<String> collectionsToLeave;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SEOInput {
        
        @JsonProperty("title")
        private String title;
        
        @JsonProperty("description")
        private String description;
    }
}