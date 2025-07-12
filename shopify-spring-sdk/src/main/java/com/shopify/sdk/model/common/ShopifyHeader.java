package com.shopify.sdk.model.common;

/**
 * Standard Shopify HTTP headers.
 * Maps to Node.js ShopifyHeader enum.
 */
public enum ShopifyHeader {
    ACCESS_TOKEN("X-Shopify-Access-Token"),
    API_VERSION("X-Shopify-API-Version"),
    DOMAIN("X-Shopify-Shop-Domain"),
    HMAC("X-Shopify-Hmac-Sha256"),
    TOPIC("X-Shopify-Topic"),
    SUB_TOPIC("X-Shopify-Sub-Topic"),
    WEBHOOK_ID("X-Shopify-Webhook-Id"),
    STOREFRONT_PRIVATE_TOKEN("Shopify-Storefront-Private-Token"),
    STOREFRONT_SDK_VARIANT("X-SDK-Variant"),
    STOREFRONT_SDK_VERSION("X-SDK-Version");

    private final String headerName;

    ShopifyHeader(String headerName) {
        this.headerName = headerName;
    }

    public String getHeaderName() {
        return headerName;
    }

    @Override
    public String toString() {
        return headerName;
    }
}