package com.shopify.sdk.model.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Currency formatting settings for a shop.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyFormats {
    /**
     * The format for displaying money
     */
    private String moneyFormat;
    
    /**
     * The format for displaying money in emails
     */
    private String moneyInEmailsFormat;
    
    /**
     * The format for displaying money with currency
     */
    private String moneyWithCurrencyFormat;
    
    /**
     * The format for displaying money with currency in emails
     */
    private String moneyWithCurrencyInEmailsFormat;
}