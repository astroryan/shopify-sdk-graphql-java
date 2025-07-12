package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a country.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    /**
     * The ISO code of the country.
     */
    @JsonProperty("isoCode")
    private CountryCode isoCode;
    
    /**
     * The name of the country.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The tax of the country.
     */
    @JsonProperty("tax")
    private Double tax;
    
    /**
     * The tax name of the country.
     */
    @JsonProperty("taxName")
    private String taxName;
    
    /**
     * The provinces of the country.
     */
    @JsonProperty("provinces")
    private List<Province> provinces;
}