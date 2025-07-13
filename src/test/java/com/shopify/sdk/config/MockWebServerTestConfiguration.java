package com.shopify.sdk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.client.HttpClientConfig;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.client.ShopifyRestClient;
import com.shopify.sdk.client.graphql.GraphQLClient;
import com.shopify.sdk.client.rest.RestClient;
import com.shopify.sdk.client.rest.RestClientImpl;
import com.shopify.sdk.exception.ShopifyApiException;
import com.fasterxml.jackson.databind.JsonNode;
import com.shopify.sdk.model.common.ApiVersion;
import com.shopify.sdk.model.common.ShopifyConstants;
import com.shopify.sdk.model.common.ShopifyHeader;
import com.shopify.sdk.session.SessionManager;
import com.shopify.sdk.session.SessionStore;
import com.shopify.sdk.auth.JwtTokenValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.netty.http.client.HttpClient;
import reactor.core.publisher.Mono;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.mockito.Mockito;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.Map;

/**
 * Test configuration for MockWebServer-based integration tests.
 * Provides bean overrides for testing with dynamic URLs.
 */
@TestConfiguration
public class MockWebServerTestConfiguration {
    
    @Value("${shopify.host-name:localhost:8080}")
    private String hostName;
    
    @Value("${shopify.api-key:test-api-key}")
    private String apiKey;
    
    @Value("${shopify.api-secret-key:test-api-secret}")
    private String apiSecretKey;
    
    @Bean
    @Primary
    public ShopifyAuthContext testAuthContext() {
        return ShopifyAuthContext.builder()
            .apiKey(apiKey)
            .apiSecretKey(apiSecretKey)
            .scopes(java.util.List.of("read_products", "write_products", "read_orders", "write_orders"))
            .hostName(hostName)
            .apiVersion(ApiVersion.JANUARY_24)
            .isEmbeddedApp(false)
            .adminApiAccessToken("test-access-token")
            .build();
    }
    
    @Bean
    @Primary
    public HttpClientConfig testHttpClientConfig() {
        // Custom HttpClientConfig for tests that uses HTTP instead of HTTPS
        return new HttpClientConfig() {
            @Override
            public WebClient createAdminApiClient(ShopifyAuthContext context, String shop) {
                String baseUrl = String.format("http://%s/admin/api/%s", shop, context.getApiVersion().getVersion());
                return createWebClient(context, baseUrl);
            }
            
            @Override
            public WebClient createStorefrontApiClient(ShopifyAuthContext context, String shop) {
                String baseUrl = String.format("http://%s/api/%s/graphql", shop, context.getApiVersion().getVersion());
                return createWebClient(context, baseUrl);
            }
            
            @Override
            public WebClient createWebClient(ShopifyAuthContext context, String baseUrl) {
                HttpClient httpClient = HttpClient.create()
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, ShopifyConstants.DEFAULT_TIMEOUT_MS)
                    .responseTimeout(Duration.ofMillis(ShopifyConstants.DEFAULT_TIMEOUT_MS))
                    .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(ShopifyConstants.DEFAULT_TIMEOUT_MS, TimeUnit.MILLISECONDS))
                            .addHandlerLast(new WriteTimeoutHandler(ShopifyConstants.DEFAULT_TIMEOUT_MS, TimeUnit.MILLISECONDS)));
                
                WebClient.Builder builder = WebClient.builder()
                    .clientConnector(new ReactorClientHttpConnector(httpClient))
                    .defaultHeader("User-Agent", ShopifyConstants.DEFAULT_USER_AGENT)
                    .defaultHeader(ShopifyHeader.API_VERSION.getHeaderName(), context.getApiVersion().getVersion());
                
                if (baseUrl != null) {
                    builder.baseUrl(baseUrl);
                }
                
                return builder.build();
            }
        };
    }
    
    @Bean
    @Primary
    public SessionStore testSessionStore() {
        // Provide a mock session store for tests
        return Mockito.mock(SessionStore.class);
    }
    
    @Bean
    @Primary
    public JwtTokenValidator testJwtTokenValidator() {
        // Provide a mock JWT token validator for tests
        return Mockito.mock(JwtTokenValidator.class);
    }
    
    @Bean
    @Primary
    public SessionManager testSessionManager(SessionStore sessionStore, JwtTokenValidator jwtTokenValidator) {
        // Return a mock or a test implementation
        SessionManager mock = Mockito.mock(SessionManager.class);
        return mock;
    }
    
    @Bean
    @Primary
    public RestClient testRestClient(HttpClientConfig httpClientConfig, ObjectMapper objectMapper) {
        // Custom RestClient for tests that uses HTTP without forcing HTTPS
        System.out.println("Creating test RestClient bean");
        return new RestClient() {
            @Override
            public Mono<JsonNode> get(String shop, String accessToken, String endpoint, Map<String, Object> queryParams) {
                String url = buildTestUrl(shop, endpoint);
                
                UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
                if (queryParams != null) {
                    queryParams.forEach((key, value) -> {
                        if (value != null) {
                            uriBuilder.queryParam(key, value);
                        }
                    });
                }
                
                // Create a WebClient with the test URL
                WebClient webClient = httpClientConfig.createWebClient(testAuthContext(), url);
                return webClient
                    .get()
                    .uri(uriBuilder.build().toUri())
                    .header("X-Shopify-Access-Token", accessToken)
                    .header("User-Agent", httpClientConfig.getUserAgent())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(responseBody -> parseResponse(responseBody, objectMapper))
                    .onErrorMap(this::mapToShopifyApiException);
            }
            
            @Override
            public Mono<JsonNode> post(String shop, String accessToken, String endpoint, Object body) {
                String url = buildTestUrl(shop, endpoint);
                
                WebClient webClient = httpClientConfig.createWebClient(testAuthContext(), url);
                return webClient
                    .post()
                    .uri(url)
                    .header("X-Shopify-Access-Token", accessToken)
                    .header("User-Agent", httpClientConfig.getUserAgent())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(responseBody -> parseResponse(responseBody, objectMapper))
                    .onErrorMap(this::mapToShopifyApiException);
            }
            
            @Override
            public Mono<JsonNode> put(String shop, String accessToken, String endpoint, Object body) {
                String url = buildTestUrl(shop, endpoint);
                
                WebClient webClient = httpClientConfig.createWebClient(testAuthContext(), url);
                return webClient
                    .put()
                    .uri(url)
                    .header("X-Shopify-Access-Token", accessToken)
                    .header("User-Agent", httpClientConfig.getUserAgent())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(responseBody -> parseResponse(responseBody, objectMapper))
                    .onErrorMap(this::mapToShopifyApiException);
            }
            
            @Override
            public Mono<JsonNode> delete(String shop, String accessToken, String endpoint) {
                String url = buildTestUrl(shop, endpoint);
                
                WebClient webClient = httpClientConfig.createWebClient(testAuthContext(), url);
                return webClient
                    .delete()
                    .uri(url)
                    .header("X-Shopify-Access-Token", accessToken)
                    .header("User-Agent", httpClientConfig.getUserAgent())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(responseBody -> parseResponse(responseBody, objectMapper))
                    .onErrorMap(this::mapToShopifyApiException);
            }
            
            private String buildTestUrl(String shop, String endpoint) {
                // For tests, use the shop domain as-is (localhost:port) without forcing HTTPS
                String baseUrl = String.format("http://%s/admin/api/2024-01", shop);
                
                if (!endpoint.startsWith("/")) {
                    endpoint = "/" + endpoint;
                }
                
                return baseUrl + endpoint;
            }
            
            private JsonNode parseResponse(String responseBody, ObjectMapper objectMapper) {
                try {
                    if (responseBody == null || responseBody.trim().isEmpty()) {
                        return objectMapper.createObjectNode();
                    }
                    return objectMapper.readTree(responseBody);
                } catch (Exception e) {
                    throw new ShopifyApiException("Failed to parse REST API response", e);
                }
            }
            
            private Throwable mapToShopifyApiException(Throwable throwable) {
                if (throwable instanceof ShopifyApiException) {
                    return throwable;
                }
                return new ShopifyApiException("REST API request failed", throwable);
            }
        };
    }
    
}