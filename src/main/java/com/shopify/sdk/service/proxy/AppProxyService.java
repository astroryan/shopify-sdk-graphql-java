package com.shopify.sdk.service.proxy;

import com.shopify.sdk.config.ShopifyAuthContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppProxyService {
    
    private final ShopifyAuthContext authContext;
    
    private static final String HMAC_SHA256 = "HmacSHA256";
    private static final String SIGNATURE_PARAM = "signature";
    private static final String TIMESTAMP_PARAM = "timestamp";
    private static final String SHOP_PARAM = "shop";
    
    /**
     * Validates an app proxy request signature.
     * 
     * @param queryParams The query parameters from the request
     * @return true if the signature is valid, false otherwise
     */
    public boolean validateProxyRequest(Map<String, String> queryParams) {
        try {
            String signature = queryParams.get(SIGNATURE_PARAM);
            if (signature == null || signature.isEmpty()) {
                log.warn("App proxy request missing signature");
                return false;
            }
            
            // Remove signature from parameters for validation
            Map<String, String> paramsWithoutSignature = new HashMap<>(queryParams);
            paramsWithoutSignature.remove(SIGNATURE_PARAM);
            
            // Build query string for validation
            String queryString = buildSortedQueryString(paramsWithoutSignature);
            
            // Calculate expected signature
            String expectedSignature = calculateHmacSha256(queryString, authContext.getApiSecret());
            
            boolean isValid = signature.equals(expectedSignature);
            
            if (!isValid) {
                log.warn("App proxy signature validation failed. Expected: {}, Got: {}", 
                    expectedSignature, signature);
            }
            
            return isValid;
            
        } catch (Exception e) {
            log.error("Error validating app proxy request", e);
            return false;
        }
    }
    
    /**
     * Validates an app proxy request from Spring's MultiValueMap.
     */
    public boolean validateProxyRequest(MultiValueMap<String, String> queryParams) {
        Map<String, String> singleValueMap = new HashMap<>();
        queryParams.forEach((key, values) -> {
            if (!values.isEmpty()) {
                singleValueMap.put(key, values.get(0));
            }
        });
        return validateProxyRequest(singleValueMap);
    }
    
    /**
     * Extracts shop domain from proxy request parameters.
     */
    public String extractShopDomain(Map<String, String> queryParams) {
        String shop = queryParams.get(SHOP_PARAM);
        if (shop == null || shop.isEmpty()) {
            return null;
        }
        
        // Ensure shop ends with .myshopify.com
        if (!shop.endsWith(".myshopify.com")) {
            shop = shop + ".myshopify.com";
        }
        
        return shop;
    }
    
    /**
     * Extracts timestamp from proxy request parameters.
     */
    public Long extractTimestamp(Map<String, String> queryParams) {
        String timestamp = queryParams.get(TIMESTAMP_PARAM);
        if (timestamp == null || timestamp.isEmpty()) {
            return null;
        }
        
        try {
            return Long.parseLong(timestamp);
        } catch (NumberFormatException e) {
            log.warn("Invalid timestamp format: {}", timestamp);
            return null;
        }
    }
    
    /**
     * Checks if the proxy request timestamp is within the acceptable time window.
     */
    public boolean isTimestampValid(Long timestamp, long toleranceSeconds) {
        if (timestamp == null) {
            return false;
        }
        
        long currentTime = System.currentTimeMillis() / 1000;
        long timeDifference = Math.abs(currentTime - timestamp);
        
        return timeDifference <= toleranceSeconds;
    }
    
    /**
     * Checks if the proxy request timestamp is within default time window (5 minutes).
     */
    public boolean isTimestampValid(Long timestamp) {
        return isTimestampValid(timestamp, 300); // 5 minutes
    }
    
    /**
     * Validates a complete app proxy request including signature and timestamp.
     */
    public ProxyValidationResult validateCompleteProxyRequest(Map<String, String> queryParams) {
        ProxyValidationResult result = new ProxyValidationResult();
        
        // Validate signature
        result.setSignatureValid(validateProxyRequest(queryParams));
        
        // Extract shop domain
        result.setShopDomain(extractShopDomain(queryParams));
        
        // Extract and validate timestamp
        Long timestamp = extractTimestamp(queryParams);
        result.setTimestamp(timestamp);
        result.setTimestampValid(isTimestampValid(timestamp));
        
        // Overall validity
        result.setValid(result.isSignatureValid() && result.isTimestampValid() && 
                       result.getShopDomain() != null);
        
        return result;
    }
    
    /**
     * Creates a response that can be embedded in Shopify's app proxy.
     */
    public String createProxyResponse(String content, String contentType) {
        // App proxy responses need to be wrapped in specific format
        if ("application/json".equals(contentType)) {
            return content;
        } else {
            // For HTML/text responses, ensure they're properly formatted
            return content;
        }
    }
    
    /**
     * Creates a JSON response for app proxy.
     */
    public String createJsonProxyResponse(Object data) {
        try {
            // Convert object to JSON string
            // This would typically use ObjectMapper
            if (data instanceof String) {
                return "\"" + data + "\"";
            }
            return data.toString();
        } catch (Exception e) {
            log.error("Failed to create JSON proxy response", e);
            return "{\"error\":\"Failed to create response\"}";
        }
    }
    
    /**
     * Creates an HTML response for app proxy.
     */
    public String createHtmlProxyResponse(String htmlContent) {
        return "<!DOCTYPE html>\n" +
               "<html>\n" +
               "<head>\n" +
               "    <meta charset=\"utf-8\">\n" +
               "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
               "</head>\n" +
               "<body>\n" +
               htmlContent + "\n" +
               "</body>\n" +
               "</html>";
    }
    
    /**
     * Builds a sorted query string for signature validation.
     */
    private String buildSortedQueryString(Map<String, String> params) {
        List<String> pairs = new ArrayList<>();
        
        // Sort parameters by key
        params.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> {
                String key = entry.getKey();
                String value = entry.getValue();
                if (value != null) {
                    pairs.add(key + "=" + value);
                }
            });
        
        return String.join("&", pairs);
    }
    
    /**
     * Calculates HMAC-SHA256 signature.
     */
    private String calculateHmacSha256(String data, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(HMAC_SHA256);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
        mac.init(secretKeySpec);
        
        byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        
        return hexString.toString();
    }
    
    /**
     * Result of proxy validation.
     */
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class ProxyValidationResult {
        private boolean valid;
        private boolean signatureValid;
        private boolean timestampValid;
        private String shopDomain;
        private Long timestamp;
        private String errorMessage;
    }
}