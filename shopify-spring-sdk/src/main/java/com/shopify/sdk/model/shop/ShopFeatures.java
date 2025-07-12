package com.shopify.sdk.model.shop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the features available to a shop.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopFeatures {
    /**
     * Whether the shop has access to Avalara AvaTax.
     */
    @JsonProperty("avalaraAvatax")
    private Boolean avalaraAvatax;
    
    /**
     * Whether the shop is a development shop.
     */
    @JsonProperty("branding")
    private ShopBranding branding;
    
    /**
     * Whether the shop has captcha protection.
     */
    @JsonProperty("captcha")
    private Boolean captcha;
    
    /**
     * Whether the shop has captcha protection on storefront.
     */
    @JsonProperty("captchaExternalDomains")
    private Boolean captchaExternalDomains;
    
    /**
     * Whether the shop has dynamic remarketing enabled.
     */
    @JsonProperty("dynamicRemarketing")
    private Boolean dynamicRemarketing;
    
    /**
     * Whether the shop can be giftCards.
     */
    @JsonProperty("giftCards")
    private Boolean giftCards;
    
    /**
     * Whether the shop has international domains.
     */
    @JsonProperty("internationalDomains")
    private Boolean internationalDomains;
    
    /**
     * Whether the shop has international price overrides.
     */
    @JsonProperty("internationalPriceOverrides")
    private Boolean internationalPriceOverrides;
    
    /**
     * Whether the shop has international price rules.
     */
    @JsonProperty("internationalPriceRules")
    private Boolean internationalPriceRules;
    
    /**
     * Whether the shop has live view.
     */
    @JsonProperty("liveView")
    private Boolean liveView;
    
    /**
     * Whether the shop has online store 2.0.
     */
    @JsonProperty("onlineStore20")
    private Boolean onlineStore20;
    
    /**
     * Whether the shop has reports.
     */
    @JsonProperty("reports")
    private Boolean reports;
    
    /**
     * Whether the shop has storefront.
     */
    @JsonProperty("storefront")
    private Boolean storefront;
}