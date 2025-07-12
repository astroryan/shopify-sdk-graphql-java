package com.shopify.sdk.model.checkout;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for checkout branding customizations.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingCustomizationsInput {
    /**
     * The global customizations.
     */
    @JsonProperty("global")
    private String global;
    
    /**
     * The header position.
     */
    @JsonProperty("headerPosition")
    private CheckoutBrandingLogoPosition headerPosition;
    
    /**
     * The header alignment.
     */
    @JsonProperty("headerAlignment")
    private CheckoutBrandingHorizontalAlignment headerAlignment;
    
    /**
     * The main area background style.
     */
    @JsonProperty("mainAreaBackgroundStyle")
    private CheckoutBrandingBackgroundStyle mainAreaBackgroundStyle;
    
    /**
     * The order summary background style.
     */
    @JsonProperty("orderSummaryBackgroundStyle")
    private CheckoutBrandingBackgroundStyle orderSummaryBackgroundStyle;
}