package com.shopify.sdk.model.markets;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a monetary value input with a specified currency.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoneyInput {
    
    /**
     * The amount of money.
     */
    @JsonProperty("amount")
    private String amount;
    
    /**
     * The currency code of the money.
     */
    @JsonProperty("currencyCode")
    private String currencyCode;
}