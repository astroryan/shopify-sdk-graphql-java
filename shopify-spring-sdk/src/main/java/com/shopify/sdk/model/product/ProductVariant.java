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
public class ProductVariant {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("sku")
    private String sku;
    
    @JsonProperty("barcode")
    private String barcode;
    
    @JsonProperty("price")
    private Money price;
    
    @JsonProperty("compareAtPrice")
    private Money compareAtPrice;
    
    @JsonProperty("weight")
    private Double weight;
    
    @JsonProperty("weightUnit")
    private WeightUnit weightUnit;
    
    @JsonProperty("inventoryQuantity")
    private Integer inventoryQuantity;
    
    @JsonProperty("availableForSale")
    private Boolean availableForSale;
    
    @JsonProperty("requiresShipping")
    private Boolean requiresShipping;
    
    @JsonProperty("taxable")
    private Boolean taxable;
    
    @JsonProperty("taxCode")
    private String taxCode;
    
    @JsonProperty("position")
    private Integer position;
    
    @JsonProperty("selectedOptions")
    private List<SelectedOption> selectedOptions;
    
    @JsonProperty("image")
    private Image image;
    
    @JsonProperty("product")
    private Product product;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
}