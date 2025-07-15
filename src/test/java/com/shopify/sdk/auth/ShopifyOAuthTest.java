package com.shopify.sdk.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.client.HttpClientService;
import com.shopify.sdk.client.ShopifyHttpRequest;
import com.shopify.sdk.client.ShopifyHttpResponse;
import com.shopify.sdk.config.ShopifyAuthContext;
import com.shopify.sdk.exception.ShopifyAuthException;
import com.shopify.sdk.exception.ShopifyApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShopifyOAuthTest {
    
    @Mock
    private HttpClientService httpClientService;
    
    @Mock
    private ObjectMapper objectMapper;
    
    private ShopifyOAuth shopifyOAuth;
    private ShopifyAuthContext authContext;
    
    @BeforeEach
    void setUp() {
        authContext = ShopifyAuthContext.builder()
            .apiKey("test-api-key")
            .apiSecretKey("test-api-secret")
            .scopes(List.of("read_products", "write_orders"))
            .hostName("test-app.example.com")
            .webhookSecret("test-webhook-secret")
            .build();
        
        shopifyOAuth = new ShopifyOAuth(authContext, httpClientService, objectMapper);
    }
    
    @Test
    @DisplayName("Should generate valid authorization URL")
    void testGenerateAuthorizationUrl() {
        String shop = "test-shop.myshopify.com";
        String redirectUri = "https://test-app.example.com/callback";
        String state = "test-state-123";
        List<String> scopes = authContext.getScopes();
        
        String authUrl = shopifyOAuth.getAuthorizationUrl(shop, scopes, redirectUri, state);
        
        assertThat(authUrl)
            .contains("https://test-shop.myshopify.com/admin/oauth/authorize")
            .contains("client_id=test-api-key")
            .contains("scope=read_products,write_orders")
            .contains("redirect_uri=https://test-app.example.com/callback")
            .contains("state=test-state-123");
    }
    
    @Test
    @DisplayName("Should generate authorization URL without state")
    void testGenerateAuthorizationUrlWithoutState() {
        String shop = "test-shop.myshopify.com";
        String redirectUri = "https://test-app.example.com/callback";
        List<String> scopes = authContext.getScopes();
        
        String authUrl = shopifyOAuth.getAuthorizationUrl(shop, scopes, redirectUri, null);
        
        assertThat(authUrl)
            .contains("https://test-shop.myshopify.com/admin/oauth/authorize")
            .doesNotContain("state=");
    }
    
    @Test
    @DisplayName("Should validate HMAC signature correctly")
    void testValidateHmac() {
        Map<String, String> params = Map.of(
            "code", "test-code",
            "shop", "test-shop.myshopify.com",
            "timestamp", "1234567890"
        );
        
        // Calculate the expected HMAC using the same algorithm as ShopifyOAuth
        String validHmac = calculateHmac(params, authContext.getApiSecret());
        
        // Should validate successfully
        boolean isValid = shopifyOAuth.validateCallback("test-shop.myshopify.com", "test-code", 
                                                       validHmac, null, params);
        assertThat(isValid).isTrue();
    }
    
    @Test
    @DisplayName("Should reject invalid HMAC signature")
    void testRejectInvalidHmac() {
        Map<String, String> params = Map.of(
            "code", "test-code",
            "shop", "test-shop.myshopify.com",
            "timestamp", "1234567890",
            "hmac", "invalid-hmac-signature"
        );
        
        // Should not validate successfully
        boolean isValid = shopifyOAuth.validateCallback("test-shop.myshopify.com", "test-code", 
                                                       "invalid-hmac-signature", null, params);
        assertThat(isValid).isFalse();
    }
    
    @Test
    @DisplayName("Should exchange code for access token")
    void testExchangeCodeForToken() throws Exception {
        String shop = "test-shop.myshopify.com";
        String code = "test-authorization-code";
        
        // Mock the HTTP response
        ShopifyHttpResponse mockResponse = new ShopifyHttpResponse(
            HttpStatus.OK,
            Map.of("Content-Type", "application/json"),
            "{\"access_token\":\"test-access-token\",\"scope\":\"read_products,write_orders\"}"
        );
        
        // Mock the HTTP client service
        when(httpClientService.execute(eq(authContext), any(ShopifyHttpRequest.class)))
            .thenReturn(Mono.just(mockResponse));
        
        // Mock ObjectMapper
        when(objectMapper.writeValueAsString(any())).thenReturn("{}");
        when(objectMapper.readValue(anyString(), eq(Map.class)))
            .thenReturn(Map.of(
                "access_token", "test-access-token",
                "scope", "read_products,write_orders"
            ));
        
        // Test exchange code for token
        AccessTokenResponse response = shopifyOAuth.exchangeCodeForToken(shop, code);
        
        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isEqualTo("test-access-token");
        assertThat(response.getScope()).isEqualTo("read_products,write_orders");
    }
    
    @Test
    @DisplayName("Should validate webhook signature correctly")
    void testValidateWebhook() {
        String rawBody = "{\"id\":12345,\"email\":\"test@example.com\"}";
        String validHmac = calculateWebhookHmac(rawBody, authContext.getWebhookSecret());
        
        // Test with valid HMAC
        boolean isValid = shopifyOAuth.validateWebhook(rawBody, validHmac);
        assertThat(isValid).isTrue();
    }
    
    @Test
    @DisplayName("Should reject null parameters in validateCallback")
    void testRejectNullParameters() {
        // Test with null shop
        boolean isValid = shopifyOAuth.validateCallback(null, "code", "hmac", "state", Map.of());
        assertThat(isValid).isFalse();
        
        // Test with null code
        isValid = shopifyOAuth.validateCallback("test-shop.myshopify.com", null, "hmac", "state", Map.of());
        assertThat(isValid).isFalse();
    }
    
    @Test
    @DisplayName("Should handle invalid shop domain in getAuthorizationUrl")
    void testInvalidShopDomainThrowsException() {
        assertThatThrownBy(() -> 
            shopifyOAuth.getAuthorizationUrl("", List.of("read_products"), "https://example.com", "state")
        ).isInstanceOf(ShopifyApiException.class);
    }
    
    private String calculateHmac(Map<String, String> params, String secret) {
        try {
            // Build query string (same as ShopifyOAuth.buildQueryString)
            String queryString = params.entrySet().stream()
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
            
            // Calculate HMAC-SHA256 (same as ShopifyOAuth.calculateHmac)
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] digest = mac.doFinal(queryString.getBytes(StandardCharsets.UTF_8));
            
            // Convert to hex string (same as ShopifyOAuth.bytesToHex)
            StringBuilder result = new StringBuilder();
            for (byte b : digest) {
                result.append(String.format("%02x", b));
            }
            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error calculating HMAC", e);
        }
    }
    
    private String calculateWebhookHmac(String data, String secret) {
        try {
            // Calculate HMAC-SHA256 (same as ShopifyOAuth.calculateHmacSha256)
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] digest = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(digest);
        } catch (Exception e) {
            throw new RuntimeException("Error calculating webhook HMAC", e);
        }
    }
}