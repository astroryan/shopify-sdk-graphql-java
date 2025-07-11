package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a location (warehouse, store, etc.)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    /**
     * The ID of the location
     */
    @JsonProperty("id")
    private ID id;
    
    /**
     * The name of the location
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The address of the location
     */
    @JsonProperty("address")
    private LocationAddress address;
    
    /**
     * Whether the location is active
     */
    @JsonProperty("isActive")
    private Boolean isActive;
    
    /**
     * Whether the location is a primary location
     */
    @JsonProperty("isPrimary")
    private Boolean isPrimary;
    
    /**
     * Whether the location supports fulfillment
     */
    @JsonProperty("fulfillsOnlineOrders")
    private Boolean fulfillsOnlineOrders;
}

/**
 * Represents a location address
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class LocationAddress {
    @JsonProperty("address1")
    private String address1;
    
    @JsonProperty("address2")
    private String address2;
    
    @JsonProperty("city")
    private String city;
    
    @JsonProperty("country")
    private String country;
    
    @JsonProperty("countryCode")
    private String countryCode;
    
    @JsonProperty("phone")
    private String phone;
    
    @JsonProperty("province")
    private String province;
    
    @JsonProperty("provinceCode")
    private String provinceCode;
    
    @JsonProperty("zip")
    private String zip;
}