package com.shopify.sdk.model.marketplace;

import com.shopify.sdk.model.common.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The market's currency settings.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketCurrencySettings {
    /**
     * The currency code for the market
     */
    private CurrencyCode baseCurrency;
    
    /**
     * Whether the market supports multiple currencies
     */
    private Boolean supportMultipleCurrencies;
}