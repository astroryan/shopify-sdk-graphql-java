package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a weight measurement with value and unit.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weight {
    
    @JsonProperty("value")
    private Double value;
    
    @JsonProperty("unit")
    private WeightUnit unit;
    
    public static Weight of(Double value, WeightUnit unit) {
        return new Weight(value, unit);
    }
    
    public static Weight grams(Double value) {
        return new Weight(value, WeightUnit.GRAMS);
    }
    
    public static Weight kilograms(Double value) {
        return new Weight(value, WeightUnit.KILOGRAMS);
    }
    
    public static Weight pounds(Double value) {
        return new Weight(value, WeightUnit.POUNDS);
    }
    
    public static Weight ounces(Double value) {
        return new Weight(value, WeightUnit.OUNCES);
    }
    
    public enum WeightUnit {
        GRAMS,
        KILOGRAMS, 
        POUNDS,
        OUNCES
    }
}