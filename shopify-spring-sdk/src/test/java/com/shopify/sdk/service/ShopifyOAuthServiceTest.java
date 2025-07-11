package com.shopify.sdk.service;

import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.access.AccessScope;
import com.shopify.sdk.model.access.AccessToken;
import com.shopify.sdk.service.auth.ShopifyOAuthService;
import okhttp3.*;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * ShopifyOAuthService 테스트
 * OAuth 인증 플로우와 보안 기능을 검증합니다.
 */
@ExtendWith(MockitoExtension.class)
class ShopifyOAuthServiceTest {
    
    private ShopifyOAuthService oAuthService;
    private MockWebServer mockServer;
    private OkHttpClient httpClient;
    
    @Mock
    private ShopifyGraphQLClient graphQLClient;
    
    private static final String CLIENT_ID = "test-client-id";
    private static final String CLIENT_SECRET = "test-client-secret";
    private static final String SHOP_DOMAIN = "test-shop.myshopify.com";
    private static final String REDIRECT_URI = "https://myapp.com/callback";
    
    @BeforeEach
    void setUp() throws IOException {
        mockServer = new MockWebServer();
        mockServer.start();
        
        httpClient = new OkHttpClient.Builder().build();
        oAuthService = new ShopifyOAuthService(httpClient);
    }
    
    @AfterEach
    void tearDown() throws IOException {
        mockServer.shutdown();
    }
    
    @Test
    @DisplayName("OAuth 인증 URL 생성")
    void testBuildAuthorizationUrl() {
        // Given
        List<String> scopes = Arrays.asList("read_products", "write_products", "read_orders");
        String state = "random-state-123";
        
        // When
        String authUrl = oAuthService.buildAuthorizationUrl(
            SHOP_DOMAIN,
            CLIENT_ID,
            REDIRECT_URI,
            scopes,
            state
        );
        
        // Then
        assertNotNull(authUrl);
        assertTrue(authUrl.startsWith("https://test-shop.myshopify.com/admin/oauth/authorize"));
        assertTrue(authUrl.contains("client_id=" + CLIENT_ID));
        assertTrue(authUrl.contains("redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8)));
        assertTrue(authUrl.contains("scope=read_products,write_products,read_orders"));
        assertTrue(authUrl.contains("state=" + state));
    }
    
    @Test
    @DisplayName("Access Token 교환 - 성공")
    void testExchangeCodeForToken_Success() throws Exception {
        // Given
        String authCode = "test-auth-code-123";
        String expectedToken = "shpat_test_token_123456";
        String expectedScope = "read_products,write_products,read_orders";
        
        // Mock server response
        String mockResponse = """
            {
                "access_token": "%s",
                "scope": "%s",
                "associated_user_scope": "%s",
                "associated_user": {
                    "id": 12345,
                    "first_name": "John",
                    "last_name": "Doe",
                    "email": "john@example.com",
                    "account_owner": true
                }
            }
            """.formatted(expectedToken, expectedScope, expectedScope);
        
        mockServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody(mockResponse));
        
        // Override base URL for testing
        oAuthService = new ShopifyOAuthService(httpClient) {
            @Override
            protected String getTokenEndpoint(String shopDomain) {
                return mockServer.url("/admin/oauth/access_token").toString();
            }
        };
        
        // When
        AccessToken token = oAuthService.exchangeCodeForToken(
            SHOP_DOMAIN,
            authCode,
            CLIENT_ID,
            CLIENT_SECRET
        );
        
        // Then
        assertNotNull(token);
        assertEquals(expectedToken, token.getAccessToken());
        assertEquals(expectedScope, token.getScope());
        assertNotNull(token.getAssociatedUser());
        assertEquals("John", token.getAssociatedUser().getFirstName());
        assertTrue(token.getAssociatedUser().isAccountOwner());
        
        // Verify request
        RecordedRequest request = mockServer.takeRequest();
        assertEquals("POST", request.getMethod());
        String body = request.getBody().readUtf8();
        assertTrue(body.contains("client_id=" + CLIENT_ID));
        assertTrue(body.contains("client_secret=" + CLIENT_SECRET));
        assertTrue(body.contains("code=" + authCode));
    }
    
    @Test
    @DisplayName("Access Token 교환 - 실패")
    void testExchangeCodeForToken_Failure() throws Exception {
        // Given
        String authCode = "invalid-code";
        
        mockServer.enqueue(new MockResponse()
            .setResponseCode(401)
            .setBody("{\"error\":\"invalid_request\",\"error_description\":\"Invalid authorization code\"}"));
        
        // Override base URL for testing
        oAuthService = new ShopifyOAuthService(httpClient) {
            @Override
            protected String getTokenEndpoint(String shopDomain) {
                return mockServer.url("/admin/oauth/access_token").toString();
            }
        };
        
        // When & Then
        ShopifyApiException exception = assertThrows(ShopifyApiException.class, () -> {
            oAuthService.exchangeCodeForToken(SHOP_DOMAIN, authCode, CLIENT_ID, CLIENT_SECRET);
        });
        
        assertTrue(exception.getMessage().contains("Failed to exchange code for token"));
    }
    
    @Test
    @DisplayName("Request 검증 - HMAC 서명 검증")
    void testVerifyRequest_ValidHmac() throws NoSuchAlgorithmException, InvalidKeyException {
        // Given
        Map<String, String> queryParams = new LinkedHashMap<>();
        queryParams.put("code", "test-code");
        queryParams.put("shop", SHOP_DOMAIN);
        queryParams.put("state", "test-state");
        queryParams.put("timestamp", "1234567890");
        
        // Generate valid HMAC
        String queryString = queryParams.entrySet().stream()
            .map(e -> e.getKey() + "=" + e.getValue())
            .collect(Collectors.joining("&"));
        
        String hmac = generateHmac(queryString, CLIENT_SECRET);
        queryParams.put("hmac", hmac);
        
        // When
        boolean isValid = oAuthService.verifyRequest(queryParams, CLIENT_SECRET);
        
        // Then
        assertTrue(isValid);
    }
    
    @Test
    @DisplayName("Request 검증 - 잘못된 HMAC")
    void testVerifyRequest_InvalidHmac() {
        // Given
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("code", "test-code");
        queryParams.put("shop", SHOP_DOMAIN);
        queryParams.put("hmac", "invalid-hmac-signature");
        queryParams.put("timestamp", "1234567890");
        
        // When
        boolean isValid = oAuthService.verifyRequest(queryParams, CLIENT_SECRET);
        
        // Then
        assertFalse(isValid);
    }
    
    @Test
    @DisplayName("Webhook 검증 - 유효한 서명")
    void testVerifyWebhook_ValidSignature() throws Exception {
        // Given
        String webhookBody = """
            {
                "id": 1234567890,
                "email": "john@example.com",
                "created_at": "2024-01-15T10:00:00Z"
            }
            """;
        String webhookSecret = "webhook-secret-key";
        
        // Generate valid HMAC
        String hmacHeader = Base64.getEncoder().encodeToString(
            generateHmacBytes(webhookBody, webhookSecret)
        );
        
        // When
        boolean isValid = oAuthService.verifyWebhook(webhookBody, hmacHeader, webhookSecret);
        
        // Then
        assertTrue(isValid);
    }
    
    @Test
    @DisplayName("Webhook 검증 - 잘못된 서명")
    void testVerifyWebhook_InvalidSignature() {
        // Given
        String webhookBody = "{\"id\":123}";
        String invalidHmac = "invalid-hmac-signature";
        String webhookSecret = "webhook-secret-key";
        
        // When
        boolean isValid = oAuthService.verifyWebhook(webhookBody, invalidHmac, webhookSecret);
        
        // Then
        assertFalse(isValid);
    }
    
    @Test
    @DisplayName("Access Scope 검증")
    void testGetAccessScopes() {
        // Given
        List<AccessScope> expectedScopes = Arrays.asList(
            AccessScope.builder()
                .handle("read_products")
                .description("Read access to products")
                .build(),
            AccessScope.builder()
                .handle("write_products")
                .description("Write access to products")
                .build(),
            AccessScope.builder()
                .handle("read_orders")
                .description("Read access to orders")
                .build()
        );
        
        when(graphQLClient.execute(any(), any())).thenReturn(
            createMockAccessScopesResponse(expectedScopes)
        );
        
        // When
        List<AccessScope> scopes = oAuthService.getAccessScopes(authContext);
        
        // Then
        assertEquals(3, scopes.size());
        assertTrue(scopes.stream().anyMatch(s -> s.getHandle().equals("read_products")));
        assertTrue(scopes.stream().anyMatch(s -> s.getHandle().equals("write_products")));
        assertTrue(scopes.stream().anyMatch(s -> s.getHandle().equals("read_orders")));
    }
    
    @Test
    @DisplayName("Nonce 검증 - 타이밍 공격 방지")
    void testValidateNonce() {
        // Given
        String nonce = oAuthService.generateNonce();
        
        // When - 첫 번째 검증은 성공해야 함
        boolean firstValidation = oAuthService.validateNonce(nonce);
        
        // Then
        assertTrue(firstValidation);
        
        // When - 같은 nonce로 두 번째 검증은 실패해야 함 (재사용 방지)
        boolean secondValidation = oAuthService.validateNonce(nonce);
        
        // Then
        assertFalse(secondValidation);
    }
    
    @Test
    @DisplayName("Shop 도메인 유효성 검증")
    void testValidateShopDomain() {
        // Valid shop domains
        assertTrue(oAuthService.isValidShopDomain("my-shop.myshopify.com"));
        assertTrue(oAuthService.isValidShopDomain("test-store-123.myshopify.com"));
        
        // Invalid shop domains
        assertFalse(oAuthService.isValidShopDomain("fake-shop.fake.com"));
        assertFalse(oAuthService.isValidShopDomain("my-shop.myshopify.com.evil.com"));
        assertFalse(oAuthService.isValidShopDomain(""));
        assertFalse(oAuthService.isValidShopDomain(null));
        assertFalse(oAuthService.isValidShopDomain("my shop.myshopify.com")); // 공백 포함
        assertFalse(oAuthService.isValidShopDomain("my-shop.myshopify.com/admin")); // 경로 포함
    }
    
    // Helper methods
    private String generateHmac(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKey);
        byte[] hmacBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hmacBytes);
    }
    
    private byte[] generateHmacBytes(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKey);
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }
    
    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
    
    private GraphQLResponse<Object> createMockAccessScopesResponse(List<AccessScope> scopes) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "app", Map.of(
                "installation", Map.of(
                    "accessScopes", Map.of(
                        "edges", scopes.stream()
                            .map(scope -> Map.of("node", scope))
                            .collect(Collectors.toList())
                    )
                )
            )
        ));
        return response;
    }
}