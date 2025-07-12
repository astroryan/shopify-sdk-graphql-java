package com.shopify.sdk.model.checkout;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for checkout branding settings.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingInput {
    /**
     * The color schemes.
     */
    @JsonProperty("colorSchemes")
    private CheckoutBrandingColorSchemes colorSchemes;
    
    /**
     * The typography settings.
     */
    @JsonProperty("typography")
    private CheckoutBrandingTypographyInput typography;
    
    /**
     * The customizations.
     */
    @JsonProperty("customizations")
    private CheckoutBrandingCustomizationsInput customizations;
    
    /**
     * The design system settings.
     */
    @JsonProperty("designSystem")
    private String designSystem;
}