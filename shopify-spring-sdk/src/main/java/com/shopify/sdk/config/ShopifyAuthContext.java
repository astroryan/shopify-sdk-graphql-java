package com.shopify.sdk.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Authentication context for Shopify API requests
 * Contains the necessary credentials and shop information
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyAuthContext {
    /**
     * The shop domain (e.g., "myshop.myshopify.com")
     */
    private String shopDomain;
    
    /**
     * The access token for API authentication
     */
    private String accessToken;
    
    /**
     * The API version to use (e.g., "2024-01")
     * If not specified, the default version will be used
     */
    private String apiVersion;
    
    /**
     * Whether this is a private app authentication
     */
    private boolean privateApp;
    
    /**
     * The API key (for OAuth flow)
     */
    private String apiKey;
    
    /**
     * The API secret key (for OAuth flow and webhook verification)
     */
    private String apiSecretKey;
    
    /**
     * Create an auth context for a private app
     * @param shopDomain The shop domain
     * @param accessToken The private app access token
     * @return A new ShopifyAuthContext instance
     */
    public static ShopifyAuthContext privateApp(String shopDomain, String accessToken) {
        return ShopifyAuthContext.builder()
                .shopDomain(shopDomain)
                .accessToken(accessToken)
                .privateApp(true)
                .build();
    }
    
    /**
     * Create an auth context for a public app
     * @param shopDomain The shop domain
     * @param accessToken The OAuth access token
     * @param apiKey The app's API key
     * @param apiSecretKey The app's API secret key
     * @return A new ShopifyAuthContext instance
     */
    public static ShopifyAuthContext publicApp(String shopDomain, String accessToken, 
                                              String apiKey, String apiSecretKey) {
        return ShopifyAuthContext.builder()
                .shopDomain(shopDomain)
                .accessToken(accessToken)
                .apiKey(apiKey)
                .apiSecretKey(apiSecretKey)
                .privateApp(false)
                .build();
    }
    
    /**
     * Get the base URL for the shop
     * @return The shop's base URL
     */
    public String getShopUrl() {
        return "https://" + shopDomain;
    }
    
    /**
     * Check if the auth context has valid credentials
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        return shopDomain != null && !shopDomain.isEmpty() 
            && accessToken != null && !accessToken.isEmpty();
    }
}