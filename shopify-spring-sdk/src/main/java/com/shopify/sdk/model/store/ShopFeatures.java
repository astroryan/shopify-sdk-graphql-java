package com.shopify.sdk.model.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Features available to a shop.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopFeatures {
    /**
     * Whether Avalara AvaTax is enabled
     */
    private Boolean avalaraAvatax;
    
    /**
     * The branding configuration
     */
    private ShopBranding branding;
    
    /**
     * Whether bundles are available
     */
    private Boolean bundles;
    
    /**
     * Whether captcha is enabled
     */
    private Boolean captcha;
    
    /**
     * Whether captcha for external domains is enabled
     */
    private Boolean captchaExternalDomains;
    
    /**
     * Whether delivery profiles are available
     */
    private Boolean deliveryProfiles;
    
    /**
     * Whether dynamic remarketing is enabled
     */
    private Boolean dynamicRemarketing;
    
    /**
     * Whether the shop is eligible for subscription migration
     */
    private Boolean eligibleForSubscriptionMigration;
    
    /**
     * Whether the shop is eligible for subscriptions
     */
    private Boolean eligibleForSubscriptions;
    
    /**
     * Whether gift cards are enabled
     */
    private Boolean giftCards;
    
    /**
     * Whether harmonized system code is available
     */
    private Boolean harmonizedSystemCode;
    
    /**
     * Whether international domains are enabled
     */
    private Boolean internationalDomains;
    
    /**
     * Whether international price overrides are enabled
     */
    private Boolean internationalPriceOverrides;
    
    /**
     * Whether international price rules are enabled
     */
    private Boolean internationalPriceRules;
    
    /**
     * Whether legacy subscription gateway is enabled
     */
    private Boolean legacySubscriptionGatewayEnabled;
    
    /**
     * Whether live view is enabled
     */
    private Boolean liveView;
    
    /**
     * Whether multi-location is enabled
     */
    private Boolean multiLocation;
    
    /**
     * Whether onboarding visual is enabled
     */
    private Boolean onboardingVisual;
    
    /**
     * Whether product publishing is enabled
     */
    private Boolean productPublishing;
    
    /**
     * Whether reports are available
     */
    private Boolean reports;
    
    /**
     * Whether the shop sells subscriptions
     */
    private Boolean sellsSubscriptions;
    
    /**
     * Whether to show metrics
     */
    private Boolean showMetrics;
    
    /**
     * Whether storefront is enabled
     */
    private Boolean storefront;
    
    /**
     * Whether the shop is using Shopify Balance
     */
    private Boolean usingShopifyBalance;
}