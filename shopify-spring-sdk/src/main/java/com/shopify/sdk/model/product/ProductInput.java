package com.shopify.sdk.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInput {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("title")
    private String title;
    
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
    
    @JsonProperty("seo")
    private SEOInput seo;
    
    @JsonProperty("giftCard")
    private Boolean giftCard;
    
    @JsonProperty("giftCardTemplateSuffix")
    private String giftCardTemplateSuffix;
    
    @JsonProperty("requiresSellingPlan")
    private Boolean requiresSellingPlan;
    
    @JsonProperty("templateSuffix")
    private String templateSuffix;
}