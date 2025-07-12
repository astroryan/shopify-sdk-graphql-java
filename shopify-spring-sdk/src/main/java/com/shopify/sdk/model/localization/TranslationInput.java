package com.shopify.sdk.model.localization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for creating or updating a translation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslationInput {
    /**
     * The key for the translation.
     */
    @JsonProperty("key")
    private String key;
    
    /**
     * The locale of the translation.
     */
    @JsonProperty("locale")
    private String locale;
    
    /**
     * The value of the translation.
     */
    @JsonProperty("value")
    private String value;
    
    /**
     * The ID of the resource being translated.
     */
    @JsonProperty("resourceId")
    private String resourceId;
}