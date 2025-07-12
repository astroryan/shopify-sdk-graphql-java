package com.shopify.sdk.model.checkout;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents checkout branding settings.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingSettings {
    /**
     * The design system.
     */
    @JsonProperty("designSystem")
    private String designSystem;
    
    /**
     * The color schemes.
     */
    @JsonProperty("colorSchemes")
    private CheckoutBrandingColorSchemes colorSchemes;
    
    /**
     * The typography settings.
     */
    @JsonProperty("typography")
    private String typography;
    
    /**
     * The customizations.
     */
    @JsonProperty("customizations")
    private String customizations;
    
    /**
     * The header logo.
     */
    @JsonProperty("headerLogo")
    private String headerLogo;
    
    /**
     * The favicon.
     */
    @JsonProperty("favicon")
    private String favicon;
}