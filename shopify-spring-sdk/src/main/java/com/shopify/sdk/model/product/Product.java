package com.shopify.sdk.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.DateTime;
import com.shopify.sdk.model.common.HTML;
import com.shopify.sdk.model.common.ID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a product in Shopify.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    /**
     * A globally-unique identifier.
     */
    @JsonProperty("id")
    private ID id;
    
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
     * The description of the product, complete with HTML formatting.
     */
    @JsonProperty("description")
    private String description;
    
    /**
     * The description of the product, complete with HTML formatting.
     */
    @JsonProperty("descriptionHtml")
    private HTML descriptionHtml;
    
    /**
     * The date and time when the product was created.
     */
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    /**
     * The date and time when the product was last modified.
     */
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    /**
     * The date and time when the product was published to the channel.
     */
    @JsonProperty("publishedAt")
    private DateTime publishedAt;
    
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
     * The product's vendor.
     */
    @JsonProperty("tracksInventory")
    private Boolean tracksInventory;
    
    /**
     * Whether the product is a gift card.
     */
    @JsonProperty("isGiftCard")
    private Boolean isGiftCard;
    
    /**
     * The total inventory quantity of the product variant.
     */
    @JsonProperty("totalInventory")
    private Integer totalInventory;
    
    /**
     * The number of variants for the product.
     */
    @JsonProperty("totalVariants")
    private Integer totalVariants;
    
    /**
     * Whether the product has any tags.
     */
    @JsonProperty("hasOnlyDefaultVariant")
    private Boolean hasOnlyDefaultVariant;
    
    /**
     * Whether the product requires shipping.
     */
    @JsonProperty("requiresSellingPlan")
    private Boolean requiresSellingPlan;
    
    /**
     * The product's SEO information.
     */
    @JsonProperty("seo")
    private SEO seo;
    
    /**
     * The online store URL for the product.
     */
    @JsonProperty("onlineStoreUrl")
    private String onlineStoreUrl;
    
    /**
     * The online store preview URL for the product.
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