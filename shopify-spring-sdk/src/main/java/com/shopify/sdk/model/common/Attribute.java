package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a custom attribute with a key-value pair
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attribute {
    /**
     * The key/name of the attribute
     */
    @JsonProperty("key")
    private String key;
    
    /**
     * The value of the attribute
     */
    @JsonProperty("value")
    private String value;
}