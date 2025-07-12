package com.shopify.sdk.model.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import com.shopify.sdk.model.common.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents a location.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The name of the location.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The address of the location.
     */
    @JsonProperty("address")
    private Address address;
    
    /**
     * Whether the location is active.
     */
    @JsonProperty("isActive")
    private Boolean isActive;
    
    /**
     * Whether the location is the primary location.
     */
    @JsonProperty("isPrimary")
    private Boolean isPrimary;
    
    /**
     * Whether the location has active inventory.
     */
    @JsonProperty("hasActiveInventory")
    private Boolean hasActiveInventory;
    
    /**
     * Whether the location can fulfill orders.
     */
    @JsonProperty("fulfillsOnlineOrders")
    private Boolean fulfillsOnlineOrders;
    
    /**
     * The date and time when the location was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * The date and time when the location was last modified.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
}