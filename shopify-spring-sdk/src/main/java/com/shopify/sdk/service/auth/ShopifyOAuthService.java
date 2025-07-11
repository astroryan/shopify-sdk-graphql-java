package com.shopify.sdk.service.auth;

import com.shopify.sdk.config.ShopifyProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopifyOAuthService {
    
    private final ShopifyProperties properties;
    private static final String HMAC_SHA256 = "HmacSHA256";
    
    public String buildAuthorizationUrl(String shop, String clientId, String redirectUri, List<String> scopes, String state) {
        validateShopDomain(shop);
        
        Map<String, String> params = new LinkedHashMap<>();
        params.put("client_id", clientId);
        params.put("scope", String.join(",", scopes));
        params.put("redirect_uri", redirectUri);
        
        if (StringUtils.hasText(state)) {
            params.put("state", state);
        }
        
        String queryString = params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + urlEncode(entry.getValue()))
                .collect(Collectors.joining("&"));
        
        return String.format("https://%s/admin/oauth/authorize?%s", shop, queryString);
    }
    
    public boolean verifyWebhook(String data, String hmacHeader, String secret) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
            mac.init(secretKey);
            
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            String calculatedHmac = Base64.getEncoder().encodeToString(hash);
            
            return calculatedHmac.equals(hmacHeader);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("Failed to verify webhook", e);
            return false;
        }
    }
    
    public boolean verifyRequest(Map<String, String> params, String secret) {
        String hmac = params.get("hmac");
        if (!StringUtils.hasText(hmac)) {
            return false;
        }
        
        Map<String, String> paramsCopy = new TreeMap<>(params);
        paramsCopy.remove("hmac");
        paramsCopy.remove("signature");
        
        String message = paramsCopy.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
        
        return verifyHmac(message, hmac, secret);
    }
    
    private boolean verifyHmac(String message, String hmac, String secret) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
            mac.init(secretKey);
            
            byte[] hash = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
            String calculatedHmac = bytesToHex(hash);
            
            return calculatedHmac.equalsIgnoreCase(hmac);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("Failed to verify HMAC", e);
            return false;
        }
    }
    
    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
    
    private void validateShopDomain(String shop) {
        if (!StringUtils.hasText(shop)) {
            throw new IllegalArgumentException("Shop domain cannot be empty");
        }
        
        if (!shop.matches("^[a-zA-Z0-9][a-zA-Z0-9\\-]*\\.myshopify\\.com$")) {
            throw new IllegalArgumentException("Invalid shop domain format");
        }
    }
    
    private String urlEncode(String value) {
        try {
            return java.net.URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to URL encode value", e);
        }
    }
}