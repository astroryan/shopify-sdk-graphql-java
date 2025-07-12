package com.shopify.sdk.model.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryLevel {
    
    @JsonProperty("inventory_item_id")
    private String inventoryItemId;
    
    @JsonProperty("location_id")
    private String locationId;
    
    @JsonProperty("available")
    private Integer available;
    
    @JsonProperty("updated_at")
    private Instant updatedAt;
    
    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;
    
    public boolean hasStock() {
        return available != null && available > 0;
    }
    
    public boolean isOutOfStock() {
        return available == null || available <= 0;
    }
    
    public boolean isLowStock(int threshold) {
        return available != null && available > 0 && available <= threshold;
    }
}