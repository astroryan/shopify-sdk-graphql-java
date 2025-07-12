package com.shopify.sdk.model.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for creating or updating a delivery location group zone.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryLocationGroupZoneInput {
    /**
     * The name of the zone.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The countries to include in the zone.
     */
    @JsonProperty("countries")
    private List<DeliveryCountryInput> countries;
}

/**
 * Input for a delivery country.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryCountryInput {
    /**
     * The country code.
     */
    @JsonProperty("code")
    private String code;
    
    /**
     * Include all provinces.
     */
    @JsonProperty("includeAllProvinces")
    private Boolean includeAllProvinces;
    
    /**
     * Specific provinces to include.
     */
    @JsonProperty("provinces")
    private List<String> provinces;
    
    /**
     * Whether to restate provinces.
     */
    @JsonProperty("restOfWorld")
    private Boolean restOfWorld;
}