package com.shopify.sdk.model.localization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents a translation for a resource.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Translation {
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
     * Whether the translation is outdated.
     */
    @JsonProperty("outdated")
    private Boolean outdated;
    
    /**
     * When the translation was last updated.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
}