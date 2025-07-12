package com.shopify.sdk.model.shop;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents localization settings for a shop.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopLocalization implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The locale code.
     */
    @JsonProperty("locale")
    private String locale;
    
    /**
     * The country code.
     */
    @JsonProperty("country")
    private String country;
    
    /**
     * The display name.
     */
    @JsonProperty("displayName")
    private String displayName;
}