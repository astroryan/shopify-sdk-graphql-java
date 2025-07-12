package com.shopify.sdk.model.product;

/**
 * The status of a product.
 */
public enum ProductStatus {
    /**
     * The product is ready to sell and is available to customers on the online store, sales channels, and apps.
     */
    ACTIVE,
    
    /**
     * The product is no longer being sold and isn't available to customers on sales channels and apps.
     */
    ARCHIVED,
    
    /**
     * The product isn't ready to sell and is unavailable to customers on sales channels and apps.
     */
    DRAFT
}