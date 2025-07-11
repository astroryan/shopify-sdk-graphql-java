package com.shopify.sdk.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents a product variant
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariant {
    /**
     * The globally unique identifier for the variant
     */
    @JsonProperty("id")
    private ID id;
    
    /**
     * The title of the variant
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The SKU of the variant
     */
    @JsonProperty("sku")
    private String sku;
    
    /**
     * The barcode of the variant
     */
    @JsonProperty("barcode")
    private String barcode;
    
    /**
     * The price of the variant
     */
    @JsonProperty("price")
    private Money price;
    
    /**
     * The compare at price of the variant
     */
    @JsonProperty("compareAtPrice")
    private Money compareAtPrice;
    
    /**
     * The weight of the variant
     */
    @JsonProperty("weight")
    private Double weight;
    
    /**
     * The weight unit of the variant
     */
    @JsonProperty("weightUnit")
    private WeightUnit weightUnit;
    
    /**
     * Whether inventory is tracked for this variant
     */
    @JsonProperty("inventoryManagement")
    private String inventoryManagement;
    
    /**
     * The inventory policy for the variant
     */
    @JsonProperty("inventoryPolicy")
    private String inventoryPolicy;
    
    /**
     * The inventory quantity of the variant
     */
    @JsonProperty("inventoryQuantity")
    private Integer inventoryQuantity;
    
    /**
     * Whether the variant is available for sale
     */
    @JsonProperty("availableForSale")
    private Boolean availableForSale;
    
    /**
     * Whether the variant is taxable
     */
    @JsonProperty("taxable")
    private Boolean taxable;
    
    /**
     * The tax code for the variant
     */
    @JsonProperty("taxCode")
    private String taxCode;
    
    /**
     * The position of the variant
     */
    @JsonProperty("position")
    private Integer position;
    
    /**
     * When the variant was created
     */
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    /**
     * When the variant was last updated
     */
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    /**
     * The product this variant belongs to
     */
    @JsonProperty("product")
    private Product product;
    
    /**
     * Selected options for this variant
     */
    @JsonProperty("selectedOptions")
    private List<SelectedOption> selectedOptions;
}