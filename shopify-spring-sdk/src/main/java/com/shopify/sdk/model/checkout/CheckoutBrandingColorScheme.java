package com.shopify.sdk.model.checkout;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a color scheme for checkout branding.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingColorScheme {
    
    /**
     * The main background color.
     */
    @JsonProperty("background")
    private String background;
    
    /**
     * The secondary background color.
     */
    @JsonProperty("backgroundSecondary")
    private String backgroundSecondary;
    
    /**
     * The main text color.
     */
    @JsonProperty("text")
    private String text;
    
    /**
     * The secondary text color.
     */
    @JsonProperty("textSecondary")
    private String textSecondary;
    
    /**
     * The primary button background color.
     */
    @JsonProperty("primaryButton")
    private String primaryButton;
    
    /**
     * The primary button text color.
     */
    @JsonProperty("primaryButtonText")
    private String primaryButtonText;
    
    /**
     * The secondary button background color.
     */
    @JsonProperty("secondaryButton")
    private String secondaryButton;
    
    /**
     * The secondary button text color.
     */
    @JsonProperty("secondaryButtonText")
    private String secondaryButtonText;
    
    /**
     * The form accent color.
     */
    @JsonProperty("formAccent")
    private String formAccent;
    
    /**
     * The link color.
     */
    @JsonProperty("link")
    private String link;
    
    /**
     * The icon color.
     */
    @JsonProperty("icon")
    private String icon;
    
    /**
     * The border color.
     */
    @JsonProperty("border")
    private String border;
    
    /**
     * The error color.
     */
    @JsonProperty("error")
    private String error;
    
    /**
     * The success color.
     */
    @JsonProperty("success")
    private String success;
    
    /**
     * The critical color.
     */
    @JsonProperty("critical")
    private String critical;
}