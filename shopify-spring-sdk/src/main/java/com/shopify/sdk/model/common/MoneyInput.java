package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for a monetary value.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoneyInput {
    /**
     * The amount.
     */
    @JsonProperty("amount")
    private String amount;
    
    /**
     * The currency code.
     */
    @JsonProperty("currencyCode")
    private CurrencyCode currencyCode;
}