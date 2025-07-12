package com.shopify.sdk.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.shopify.sdk.model.metafield.MetafieldInput;

import java.util.List;

/**
 * Input for creating or updating a product.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInput {
    /**
     * The title of the product.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The description of the product.
     */
    @JsonProperty("descriptionHtml")
    private String descriptionHtml;
    
    /**
     * The handle of the product.
     */
    @JsonProperty("handle")
    private String handle;
    
    /**
     * The vendor of the product.
     */
    @JsonProperty("vendor")
    private String vendor;
    
    /**
     * The product type.
     */
    @JsonProperty("productType")
    private String productType;
    
    /**
     * The tags associated with the product.
     */
    @JsonProperty("tags")
    private List<String> tags;
    
    /**
     * The status of the product.
     */
    @JsonProperty("status")
    private ProductStatus status;
    
    /**
     * The template suffix of the product.
     */
    @JsonProperty("templateSuffix")
    private String templateSuffix;
    
    /**
     * Whether the product should be gift card.
     */
    @JsonProperty("giftCard")
    private Boolean giftCard;
    
    /**
     * The SEO information for the product.
     */
    @JsonProperty("seo")
    private SEOInput seo;
    
    /**
     * The options for the product.
     */
    @JsonProperty("options")
    private List<String> options;
    
    /**
     * The metafields to associate with the product.
     */
    @JsonProperty("metafields")
    private List<MetafieldInput> metafields;
}