package com.shopify.sdk.model.marketplace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The localization settings for a market.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketLocalizationSettings {
    /**
     * The primary locale for the market
     */
    private String primaryLocale;
    
    /**
     * Whether the market supports multiple locales
     */
    private Boolean supportMultipleLocales;
}