package com.shopify.sdk.model.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.DateTime;
import com.shopify.sdk.model.common.ID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryLevel {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("available")
    private Integer available;
    
    @JsonProperty("canDeactivate")
    private Boolean canDeactivate;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("deactivationAlert")
    private String deactivationAlert;
    
    @JsonProperty("incoming")
    private Integer incoming;
    
    @JsonProperty("inventoryItem")
    private InventoryItem inventoryItem;
    
    @JsonProperty("location")
    private Location location;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
}