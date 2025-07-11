package com.shopify.sdk.model.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.CountryCode;
import com.shopify.sdk.model.common.DateTime;
import com.shopify.sdk.model.common.ID;
import com.shopify.sdk.model.common.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItem {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("countryCodeOfOrigin")
    private CountryCode countryCodeOfOrigin;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("duplicateSkuCount")
    private Integer duplicateSkuCount;
    
    @JsonProperty("harmonizedSystemCode")
    private String harmonizedSystemCode;
    
    @JsonProperty("inventoryHistoryUrl")
    private String inventoryHistoryUrl;
    
    @JsonProperty("inventoryLevel")
    private InventoryLevel inventoryLevel;
    
    @JsonProperty("inventoryLevels")
    private InventoryLevelConnection inventoryLevels;
    
    @JsonProperty("legacyResourceId")
    private String legacyResourceId;
    
    @JsonProperty("locationsCount")
    private Integer locationsCount;
    
    @JsonProperty("provinceCodeOfOrigin")
    private String provinceCodeOfOrigin;
    
    @JsonProperty("requiresShipping")
    private Boolean requiresShipping;
    
    @JsonProperty("sku")
    private String sku;
    
    @JsonProperty("tracked")
    private Boolean tracked;
    
    @JsonProperty("trackedEditable")
    private EditableProperty trackedEditable;
    
    @JsonProperty("unitCost")
    private Money unitCost;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    @JsonProperty("variant")
    private ProductVariant variant;
}