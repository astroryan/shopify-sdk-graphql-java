package com.shopify.sdk.model.store;

import com.shopify.sdk.model.common.Node;
import com.shopify.sdk.model.common.CurrencyCode;
import com.shopify.sdk.model.common.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a shop.
 * Contains all the shop's configuration and settings.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shop implements Node {
    /**
     * A globally-unique identifier for the shop
     */
    private String id;
    
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
     * The shop's primary domain
     */
    private Domain primaryDomain;
    
    /**
     * The shop's myshopify.com domain
     */
    private String myshopifyDomain;
    
    /**
     * The shop's currency code
     */
    private CurrencyCode currencyCode;
    
    /**
     * The shop's currency formatting settings
     */
    private CurrencyFormats currencyFormats;
    
    /**
     * The shop's customer accounts setting
     */
    private ShopCustomerAccountsSetting customerAccounts;
    
    /**
     * The currencies enabled for the shop
     */
    private List<CurrencyCode> enabledCurrencies;
    
    /**
     * The locales enabled for the shop
     */
    private List<ShopLocale> enabledLocales;
    
    /**
     * The shop's features
     */
    private ShopFeatures features;
    
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
     * The shop's payment settings
     */
    private PaymentSettings paymentSettings;
    
    /**
     * The shop's plan
     */
    private ShopPlan plan;
    
    /**
     * The number of publications for the shop
     */
    private Integer publicationCount;
    
    /**
     * The shop's resource limits
     */
    private ShopResourceLimits resourceLimits;
    
    /**
     * Whether the shop charges taxes
     */
    private Boolean taxesIncluded;
    
    /**
     * The shop's weight unit
     */
    private WeightUnit weightUnit;
    
    /**
     * When the shop was created
     */
    private LocalDateTime createdAt;
    
    /**
     * When the shop was last updated
     */
    private LocalDateTime updatedAt;
}