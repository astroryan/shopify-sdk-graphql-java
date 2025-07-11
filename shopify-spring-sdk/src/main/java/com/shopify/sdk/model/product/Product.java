package com.shopify.sdk.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a Shopify product
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    /**
     * The globally unique identifier for the product
     */
    @JsonProperty("id")
    private ID id;
    
    /**
     * The title of the product
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The description of the product
     */
    @JsonProperty("description")
    private String description;
    
    /**
     * The description of the product in HTML
     */
    @JsonProperty("descriptionHtml")
    private String descriptionHtml;
    
    /**
     * The handle of the product
     */
    @JsonProperty("handle")
    private String handle;
    
    /**
     * The vendor of the product
     */
    @JsonProperty("vendor")
    private String vendor;
    
    /**
     * The product type
     */
    @JsonProperty("productType")
    private String productType;
    
    /**
     * The status of the product
     */
    @JsonProperty("status")
    private ProductStatus status;
    
    /**
     * When the product was created
     */
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    /**
     * When the product was last updated
     */
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    /**
     * When the product was published
     */
    @JsonProperty("publishedAt")
    private DateTime publishedAt;
    
    /**
     * The product's variants
     */
    @JsonProperty("variants")
    private Connection<ProductVariant> variants;
    
    /**
     * The product's tags
     */
    @JsonProperty("tags")
    private List<String> tags;
    
    /**
     * Whether the product is a gift card
     */
    @JsonProperty("isGiftCard")
    private Boolean isGiftCard;
    
    /**
     * The product's SEO information
     */
    @JsonProperty("seo")
    private SEO seo;
}