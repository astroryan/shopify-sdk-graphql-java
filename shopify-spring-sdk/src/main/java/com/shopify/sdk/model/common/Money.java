package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Money {
    
    @JsonProperty("amount")
    private BigDecimal amount;
    
    @JsonProperty("currencyCode")
    private CurrencyCode currencyCode;
    
    public static Money of(BigDecimal amount, CurrencyCode currencyCode) {
        return Money.builder()
                .amount(amount)
                .currencyCode(currencyCode)
                .build();
    }
    
    public static Money of(String amount, CurrencyCode currencyCode) {
        return Money.builder()
                .amount(new BigDecimal(amount))
                .currencyCode(currencyCode)
                .build();
    }
}