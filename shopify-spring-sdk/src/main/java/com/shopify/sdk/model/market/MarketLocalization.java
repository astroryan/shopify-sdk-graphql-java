package com.shopify.sdk.model.market;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a market localization.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketLocalization implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
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
    
    /**
     * The market this localization belongs to.
     */
    @JsonProperty("market")
    private Market market;
}