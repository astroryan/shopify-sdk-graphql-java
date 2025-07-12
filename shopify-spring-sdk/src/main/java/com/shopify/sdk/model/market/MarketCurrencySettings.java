package com.shopify.sdk.model.market;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Currency settings for a market.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketCurrencySettings {
    /**
     * The base currency code for the market.
     */
    @JsonProperty("baseCurrency")
    private CurrencyCode baseCurrency;
    
    /**
     * Whether local currencies are enabled.
     */
    @JsonProperty("localCurrencies")
    private Boolean localCurrencies;
}