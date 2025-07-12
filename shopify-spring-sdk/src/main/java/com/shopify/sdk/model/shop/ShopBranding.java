package com.shopify.sdk.model.shop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Shop branding settings.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopBranding {
    /**
     * The level of branding.
     */
    @JsonProperty("level")
    private ShopBrandingLevel level;
}