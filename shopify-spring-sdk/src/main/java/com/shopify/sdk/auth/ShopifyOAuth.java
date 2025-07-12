package com.shopify.sdk.auth;

import com.shopify.sdk.config.ShopifyAuthContext;
import com.shopify.sdk.exception.ShopifyApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * OAuth authentication helper for Shopify applications.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ShopifyOAuth {
    
    private final ShopifyAuthContext context;
    
    /**
     * Generates the OAuth authorization URL for Shopify app installation.
     *
     * @param shop the shop domain
     * @param scopes the requested permission scopes
     * @param redirectUri the redirect URI
     * @param state optional state parameter for CSRF protection
     * @return the authorization URL
     */
    public String getAuthorizationUrl(String shop, List<String> scopes, String redirectUri, String state) {
        validateShopDomain(shop);
        
        UriComponentsBuilder builder = UriComponentsBuilder
            .fromHttpUrl(String.format("https://%s/admin/oauth/authorize", normalizeShopDomain(shop)))
            .queryParam("client_id", context.getApiKey())
            .queryParam("scope", String.join(",", scopes))
            .queryParam("redirect_uri", redirectUri);
        
        if (state != null && !state.trim().isEmpty()) {
            builder.queryParam("state", state);
        }
        
        return builder.build().toUriString();
    }
    
    /**
     * Exchanges the authorization code for an access token.
     *
     * @param shop the shop domain
     * @param code the authorization code
     * @return the access token response
     */
    public AccessTokenResponse exchangeCodeForToken(String shop, String code) {
        validateShopDomain(shop);
        
        if (code == null || code.trim().isEmpty()) {
            throw new ShopifyApiException("Authorization code cannot be null or empty");
        }
        
        // This would typically make an HTTP request to Shopify's token endpoint
        // For now, we'll return a placeholder structure
        log.info("Exchanging authorization code for access token for shop: {}", shop);
        
        // TODO: Implement actual HTTP request to /admin/oauth/access_token
        return AccessTokenResponse.builder()
            .accessToken("placeholder_token")
            .scope("read_products,write_products")
            .build();
    }
    
    /**
     * Validates the OAuth callback parameters.
     *
     * @param shop the shop domain
     * @param code the authorization code
     * @param hmac the HMAC signature
     * @param state the state parameter
     * @param queryParams all query parameters
     * @return true if valid, false otherwise
     */
    public boolean validateCallback(String shop, String code, String hmac, String state, Map<String, String> queryParams) {
        try {
            // Validate shop domain
            validateShopDomain(shop);
            
            // Validate HMAC signature
            if (!validateHmac(queryParams, hmac)) {
                log.warn("Invalid HMAC signature for shop: {}", shop);
                return false;
            }
            
            // Validate code presence
            if (code == null || code.trim().isEmpty()) {
                log.warn("Missing authorization code for shop: {}", shop);
                return false;
            }
            
            return true;
        } catch (Exception e) {
            log.error("Error validating OAuth callback", e);
            return false;
        }
    }
    
    /**
     * Validates the HMAC signature of the request parameters.
     *
     * @param params the request parameters
     * @param receivedHmac the received HMAC signature
     * @return true if valid, false otherwise
     */
    public boolean validateHmac(Map<String, String> params, String receivedHmac) {
        if (receivedHmac == null || receivedHmac.trim().isEmpty()) {
            return false;
        }
        
        try {
            // Remove hmac and signature from params for validation
            Map<String, String> filteredParams = new HashMap<>(params);
            filteredParams.remove("hmac");
            filteredParams.remove("signature");
            
            // Build query string for HMAC calculation
            String queryString = buildQueryString(filteredParams);
            
            // Calculate HMAC
            String calculatedHmac = calculateHmac(queryString, context.getApiSecret());
            
            return receivedHmac.equals(calculatedHmac);
        } catch (Exception e) {
            log.error("Error validating HMAC", e);
            return false;
        }
    }
    
    /**
     * Validates a webhook request by verifying its HMAC signature.
     *
     * @param rawBody the raw request body
     * @param receivedHmac the received HMAC signature from header
     * @return true if valid, false otherwise
     */
    public boolean validateWebhook(String rawBody, String receivedHmac) {
        if (receivedHmac == null || rawBody == null) {
            return false;
        }
        
        try {
            // Remove 'sha256=' prefix if present
            String hmacToVerify = receivedHmac.startsWith("sha256=") 
                ? receivedHmac.substring(7) 
                : receivedHmac;
                
            String calculatedHmac = calculateHmacSha256(rawBody, context.getWebhookSecret());
            
            return hmacToVerify.equals(calculatedHmac);
        } catch (Exception e) {
            log.error("Error validating webhook HMAC", e);
            return false;
        }
    }
    
    private void validateShopDomain(String shop) {
        if (shop == null || shop.trim().isEmpty()) {
            throw new ShopifyApiException("Shop domain cannot be null or empty");
        }
        
        String normalizedShop = normalizeShopDomain(shop);
        if (!normalizedShop.endsWith(".myshopify.com")) {
            throw new ShopifyApiException("Invalid shop domain: " + shop);
        }
    }
    
    private String normalizeShopDomain(String shop) {
        if (shop == null) {
            return null;
        }
        
        shop = shop.trim().toLowerCase();
        
        // Remove protocol if present
        if (shop.startsWith("https://")) {
            shop = shop.substring(8);
        } else if (shop.startsWith("http://")) {
            shop = shop.substring(7);
        }
        
        // Add .myshopify.com if not present
        if (!shop.endsWith(".myshopify.com")) {
            shop = shop + ".myshopify.com";
        }
        
        return shop;
    }
    
    private String buildQueryString(Map<String, String> params) {
        return params.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .map(entry -> {
                try {
                    return URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + 
                           "=" + 
                           URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8);
                } catch (Exception e) {
                    throw new RuntimeException("Error encoding parameter", e);
                }
            })
            .reduce((a, b) -> a + "&" + b)
            .orElse("");
    }
    
    private String calculateHmac(String data, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] digest = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(digest);
    }
    
    private String calculateHmacSha256(String data, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] digest = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(digest);
    }
    
    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}