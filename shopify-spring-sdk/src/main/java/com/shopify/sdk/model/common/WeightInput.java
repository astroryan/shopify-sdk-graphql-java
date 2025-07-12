package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input type for weight measurements.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeightInput {
    
    @JsonProperty("value")
    private Double value;
    
    @JsonProperty("unit")
    private Weight.WeightUnit unit;
    
    public static WeightInput of(Double value, Weight.WeightUnit unit) {
        return new WeightInput(value, unit);
    }
}