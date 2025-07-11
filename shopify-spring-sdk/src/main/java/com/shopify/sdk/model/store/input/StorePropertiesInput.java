package com.shopify.sdk.model.store.input;

import com.shopify.sdk.model.common.CurrencyCode;
import com.shopify.sdk.model.store.ShopCustomerAccountsSetting;
import com.shopify.sdk.model.store.WeightUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for updating store properties.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorePropertiesInput {
    /**
     * The shop's name
     */
    private String name;
    
    /**
     * The shop's description
     */
    private String description;
    
    /**
     * The shop's email address
     */
    private String email;
    
    /**
     * The shop's contact email address
     */
    private String contactEmail;
    
    /**
     * The shop's currency code
     */
    private CurrencyCode currencyCode;
    
    /**
     * The shop's customer accounts setting
     */
    private ShopCustomerAccountsSetting customerAccounts;
    
    /**
     * The shop's IANA timezone
     */
    private String ianaTimezone;
    
    /**
     * The prefix for order numbers
     */
    private String orderNumberFormatPrefix;
    
    /**
     * The suffix for order numbers
     */
    private String orderNumberFormatSuffix;
    
    /**
     * Whether the shop charges taxes
     */
    private Boolean taxesIncluded;
    
    /**
     * The shop's weight unit
     */
    private WeightUnit weightUnit;
}