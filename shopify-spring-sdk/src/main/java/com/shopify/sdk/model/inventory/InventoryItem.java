package com.shopify.sdk.model.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents an inventory item.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItem implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The SKU of the inventory item.
     */
    @JsonProperty("sku")
    private String sku;
    
    /**
     * Whether the inventory item is tracked.
     */
    @JsonProperty("tracked")
    private Boolean tracked;
    
    /**
     * Whether the inventory item requires shipping.
     */
    @JsonProperty("requiresShipping")
    private Boolean requiresShipping;
    
    /**
     * The cost of the item.
     */
    @JsonProperty("cost")
    private String cost;
    
    /**
     * The country code of origin.
     */
    @JsonProperty("countryCodeOfOrigin")
    private String countryCodeOfOrigin;
    
    /**
     * The harmonized system code.
     */
    @JsonProperty("harmonizedSystemCode")
    private String harmonizedSystemCode;
    
    /**
     * The province code of origin.
     */
    @JsonProperty("provinceCodeOfOrigin")
    private String provinceCodeOfOrigin;
    
    /**
     * When the inventory item was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * When the inventory item was last updated.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
    
    /**
     * Inventory levels for this item.
     */
    @JsonProperty("inventoryLevels")
    private InventoryLevelConnection inventoryLevels;
}