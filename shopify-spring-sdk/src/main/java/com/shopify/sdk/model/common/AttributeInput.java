package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input type for key-value attribute pairs.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeInput {
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("value")
    private String value;
    
    public static AttributeInput of(String key, String value) {
        return new AttributeInput(key, value);
    }
}