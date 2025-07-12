package com.shopify.sdk.model.checkout;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for checkout branding typography settings.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingTypographyInput {
    /**
     * The primary font.
     */
    @JsonProperty("primary")
    private CheckoutBrandingFontGroup primary;
    
    /**
     * The secondary font.
     */
    @JsonProperty("secondary")
    private CheckoutBrandingFontGroup secondary;
    
    /**
     * The size settings.
     */
    @JsonProperty("size")
    private CheckoutBrandingFontSizeSelection size;
    
    /**
     * The kerning settings.
     */
    @JsonProperty("kerning")
    private CheckoutBrandingKerning kerning;
    
    /**
     * The letter case settings.
     */
    @JsonProperty("letterCase")
    private CheckoutBrandingLetterCase letterCase;
    
    /**
     * The font weight settings.
     */
    @JsonProperty("weight")
    private CheckoutBrandingFontWeight weight;
}