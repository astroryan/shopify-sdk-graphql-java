package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a language.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Language {
    /**
     * The ISO code of the language.
     */
    @JsonProperty("isoCode")
    private LanguageCode isoCode;
    
    /**
     * The name of the language.
     */
    @JsonProperty("name")
    private String name;
}