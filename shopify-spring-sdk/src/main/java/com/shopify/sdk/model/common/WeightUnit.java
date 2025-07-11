package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WeightUnit {
    GRAMS("GRAMS"),
    KILOGRAMS("KILOGRAMS"),
    OUNCES("OUNCES"),
    POUNDS("POUNDS");
    
    @JsonValue
    private final String value;
}