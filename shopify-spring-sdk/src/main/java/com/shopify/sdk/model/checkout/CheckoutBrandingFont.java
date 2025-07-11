package com.shopify.sdk.model.checkout;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a font configuration for checkout branding.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingFont {
    
    /**
     * The font family name.
     */
    @JsonProperty("family")
    private String family;
    
    /**
     * The font weight.
     */
    @JsonProperty("weight")
    private CheckoutBrandingFontWeight weight;
    
    /**
     * The font style (e.g., normal, italic).
     */
    @JsonProperty("style")
    private String style;
    
    /**
     * The font size.
     */
    @JsonProperty("size")
    private CheckoutBrandingFontSizeSelection size;
    
    /**
     * The font source URL.
     */
    @JsonProperty("sources")
    private String sources;
    
    /**
     * Whether the font is a system font.
     */
    @JsonProperty("isSystemFont")
    private Boolean isSystemFont;
}