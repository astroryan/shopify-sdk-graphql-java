package com.shopify.sdk.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("descriptionHtml")
    private String descriptionHtml;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("vendor")
    private String vendor;
    
    @JsonProperty("productType")
    private String productType;
    
    @JsonProperty("tags")
    private List<String> tags;
    
    @JsonProperty("status")
    private ProductStatus status;
    
    @JsonProperty("publishedAt")
    private DateTime publishedAt;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("updatedAt")  
    private DateTime updatedAt;
    
    @JsonProperty("images")
    private ProductImageConnection images;
    
    @JsonProperty("variants")
    private ProductVariantConnection variants;
    
    @JsonProperty("options")
    private List<ProductOption> options;
    
    @JsonProperty("priceRange")
    private ProductPriceRange priceRange;
    
    @JsonProperty("featuredImage")
    private Image featuredImage;
    
    @JsonProperty("seo")
    private SEO seo;
    
    @JsonProperty("totalInventory")
    private Integer totalInventory;
    
    @JsonProperty("tracksInventory")
    private Boolean tracksInventory;
    
    @JsonProperty("requiresSellingPlan")
    private Boolean requiresSellingPlan;
    
    @JsonProperty("giftCard")
    private Boolean giftCard;
}