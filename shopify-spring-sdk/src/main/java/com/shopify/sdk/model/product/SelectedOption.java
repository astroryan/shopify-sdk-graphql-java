package com.shopify.sdk.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a selected option for a product variant
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectedOption {
    /**
     * The name of the option
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The value of the option
     */
    @JsonProperty("value")
    private String value;
}