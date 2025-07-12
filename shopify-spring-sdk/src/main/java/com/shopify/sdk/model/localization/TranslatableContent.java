package com.shopify.sdk.model.localization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents translatable content for a resource.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslatableContent {
    /**
     * The key for the content.
     */
    @JsonProperty("key")
    private String key;
    
    /**
     * The value of the content.
     */
    @JsonProperty("value")
    private String value;
    
    /**
     * The locale of the content.
     */
    @JsonProperty("locale")
    private String locale;
    
    /**
     * The type of the content.
     */
    @JsonProperty("type")
    private String type;
}