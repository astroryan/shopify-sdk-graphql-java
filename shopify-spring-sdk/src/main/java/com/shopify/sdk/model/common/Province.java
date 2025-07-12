package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a province or state.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Province {
    /**
     * The code of the province.
     */
    @JsonProperty("code")
    private String code;
    
    /**
     * The name of the province.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The tax percentage of the province.
     */
    @JsonProperty("tax")
    private Double tax;
    
    /**
     * The tax name of the province.
     */
    @JsonProperty("taxName")
    private String taxName;
    
    /**
     * The tax type of the province.
     */
    @JsonProperty("taxType")
    private String taxType;
}