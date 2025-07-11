package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Represents a monetary value with currency
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Money {
    /**
     * The amount of money
     */
    @JsonProperty("amount")
    private BigDecimal amount;
    
    /**
     * The currency code (e.g., USD, EUR, CAD)
     */
    @JsonProperty("currencyCode")
    private CurrencyCode currencyCode;
    
    /**
     * Create a Money instance
     * @param amount The amount
     * @param currencyCode The currency code
     * @return A new Money instance
     */
    public static Money of(BigDecimal amount, CurrencyCode currencyCode) {
        return Money.builder()
                .amount(amount)
                .currencyCode(currencyCode)
                .build();
    }
    
    /**
     * Create a Money instance from a string amount
     * @param amount The amount as string
     * @param currencyCode The currency code
     * @return A new Money instance
     */
    public static Money of(String amount, CurrencyCode currencyCode) {
        return of(new BigDecimal(amount), currencyCode);
    }
    
    /**
     * Create a Money instance from a double amount
     * @param amount The amount as double
     * @param currencyCode The currency code
     * @return A new Money instance
     */
    public static Money of(double amount, CurrencyCode currencyCode) {
        return of(BigDecimal.valueOf(amount), currencyCode);
    }
}