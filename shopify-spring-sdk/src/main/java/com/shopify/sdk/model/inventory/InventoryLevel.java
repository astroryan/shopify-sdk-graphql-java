package com.shopify.sdk.model.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import com.shopify.sdk.model.location.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents the inventory quantity of an inventory item at a specific location.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryLevel implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The available quantity of the inventory item at the location.
     */
    @JsonProperty("available")
    private Integer available;
    
    /**
     * The incoming quantity of the inventory item at the location.
     */
    @JsonProperty("incoming")
    private Integer incoming;
    
    /**
     * The inventory item.
     */
    @JsonProperty("item")
    private InventoryItem item;
    
    /**
     * The location.
     */
    @JsonProperty("location")
    private Location location;
    
    /**
     * The date and time when the inventory level was last modified.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
}