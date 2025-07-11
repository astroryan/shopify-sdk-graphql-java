package com.shopify.sdk.model.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a locale available in a shop.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopLocale {
    /**
     * The locale identifier (e.g., "en", "fr", "es")
     */
    private String locale;
    
    /**
     * The name of the locale
     */
    private String name;
    
    /**
     * Whether this is the primary locale
     */
    private Boolean primary;
    
    /**
     * Whether this locale is published
     */
    private Boolean published;
}