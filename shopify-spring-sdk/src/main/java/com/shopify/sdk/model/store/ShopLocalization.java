package com.shopify.sdk.model.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Localization settings for a shop.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopLocalization {
    /**
     * The primary locale of the shop
     */
    private String primaryLocale;
    
    /**
     * The enabled locales for the shop
     */
    private ShopLocaleConnection locales;
}