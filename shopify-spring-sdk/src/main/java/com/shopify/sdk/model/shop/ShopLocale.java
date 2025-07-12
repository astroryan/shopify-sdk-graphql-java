package com.shopify.sdk.model.shop;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a locale for a shop.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopLocale implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The locale string.
     */
    @JsonProperty("locale")
    private String locale;
    
    /**
     * The name of the locale.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * Whether this is the primary locale.
     */
    @JsonProperty("primary")
    private Boolean primary;
    
    /**
     * Whether the locale is published.
     */
    @JsonProperty("published")
    private Boolean published;
}