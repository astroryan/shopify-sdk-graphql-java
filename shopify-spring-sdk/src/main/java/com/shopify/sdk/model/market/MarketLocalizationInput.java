package com.shopify.sdk.model.market;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for a market localization.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketLocalizationInput {
    /**
     * The key of the localization.
     */
    @JsonProperty("key")
    private String key;
    
    /**
     * The value of the localization.
     */
    @JsonProperty("value")
    private String value;
    
    /**
     * The locale of the localization.
     */
    @JsonProperty("locale")
    private String locale;
}