package com.shopify.sdk.client;

import com.shopify.sdk.config.ShopifyAuthContext;
import com.shopify.sdk.model.common.ShopifyConstants;
import com.shopify.sdk.model.common.ShopifyHeader;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Configuration for HTTP clients used by the Shopify SDK.
 */
@Component
public class HttpClientConfig {
    
    private static final int MAX_CONNECTIONS = 100;
    private static final int MAX_PENDING_ACQUIRE = 500;
    private static final Duration PENDING_ACQUIRE_TIMEOUT = Duration.ofSeconds(45);
    private static final Duration MAX_IDLE_TIME = Duration.ofSeconds(30);
    private static final Duration MAX_LIFE_TIME = Duration.ofMinutes(5);
    
    private WebClient defaultWebClient;
    private String defaultUserAgent;
    private final ConnectionProvider connectionProvider;
    
    public HttpClientConfig() {
        this.connectionProvider = ConnectionProvider.builder("shopify-http-pool")
            .maxConnections(MAX_CONNECTIONS)
            .pendingAcquireMaxCount(MAX_PENDING_ACQUIRE)
            .pendingAcquireTimeout(PENDING_ACQUIRE_TIMEOUT)
            .maxIdleTime(MAX_IDLE_TIME)
            .maxLifeTime(MAX_LIFE_TIME)
            .evictInBackground(Duration.ofSeconds(60))
            .build();
    }
    
    /**
     * Creates a configured WebClient for Shopify API requests.
     *
     * @param context the Shopify configuration context
     * @return configured WebClient
     */
    public WebClient createWebClient(ShopifyAuthContext context) {
        return createWebClient(context, null);
    }
    
    /**
     * Creates a configured WebClient for Shopify API requests with base URL.
     *
     * @param context the Shopify configuration context
     * @param baseUrl the base URL for the client
     * @return configured WebClient
     */
    public WebClient createWebClient(ShopifyAuthContext context, String baseUrl) {
        int connectTimeout = isTestEnvironment() ? 5000 : 10000; // 10 seconds connect timeout
        int readTimeout = isTestEnvironment() ? 5000 : 30000; // 30 seconds read timeout
        
        HttpClient httpClient = HttpClient.create(connectionProvider)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
            .responseTimeout(Duration.ofMillis(readTimeout))
            .doOnConnected(conn ->
                conn.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS))
                    .addHandlerLast(new WriteTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS)))
            .compress(true) // Enable compression
            .followRedirect(true); // Follow redirects
        
        WebClient.Builder builder = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .defaultHeader("User-Agent", buildUserAgent(context))
            .defaultHeader(ShopifyHeader.API_VERSION.getHeaderName(), context.getApiVersion().getVersion())
            .codecs(configurer -> configurer
                .defaultCodecs()
                .maxInMemorySize(5 * 1024 * 1024)); // 5MB max response size
        
        if (baseUrl != null) {
            builder.baseUrl(baseUrl);
        }
        
        return builder.build();
    }
    
    /**
     * Creates a WebClient specifically for Admin API requests.
     *
     * @param context the Shopify configuration context
     * @param shop the shop domain
     * @return configured WebClient for Admin API
     */
    public WebClient createAdminApiClient(ShopifyAuthContext context, String shop) {
        String baseUrl = buildAdminApiUrl(shop, context.getApiVersion().getVersion());
        return createWebClient(context, baseUrl);
    }
    
    /**
     * Creates a WebClient specifically for Storefront API requests.
     *
     * @param context the Shopify configuration context
     * @param shop the shop domain
     * @return configured WebClient for Storefront API
     */
    public WebClient createStorefrontApiClient(ShopifyAuthContext context, String shop) {
        String baseUrl = buildStorefrontApiUrl(shop, context.getApiVersion().getVersion());
        return createWebClient(context, baseUrl);
    }
    
    private String buildUserAgent(ShopifyAuthContext context) {
        StringBuilder userAgent = new StringBuilder();
        
        if (context.getUserAgentPrefix() != null && !context.getUserAgentPrefix().trim().isEmpty()) {
            userAgent.append(context.getUserAgentPrefix()).append(" ");
        }
        
        userAgent.append(ShopifyConstants.DEFAULT_USER_AGENT);
        
        return userAgent.toString();
    }
    
    private String buildAdminApiUrl(String shop, String apiVersion) {
        String protocol = isTestEnvironment() ? "http" : "https";
        return String.format("%s://%s/admin/api/%s", protocol, shop, apiVersion);
    }
    
    private String buildStorefrontApiUrl(String shop, String apiVersion) {
        String protocol = isTestEnvironment() ? "http" : "https";
        return String.format("%s://%s/api/%s/graphql", protocol, shop, apiVersion);
    }
    
    private boolean isTestEnvironment() {
        // Check if we're in a test environment
        String activeProfiles = System.getProperty("spring.profiles.active", "");
        return activeProfiles.contains("test") || 
               "false".equals(System.getProperty("shopify.use-ssl")) ||
               System.getProperty("test.mode") != null;
    }
    
    /**
     * Gets the default WebClient instance.
     *
     * @return the default WebClient
     */
    public WebClient getWebClient() {
        if (defaultWebClient == null) {
            defaultWebClient = createDefaultWebClient();
        }
        return defaultWebClient;
    }
    
    /**
     * Gets the default User-Agent string.
     *
     * @return the default User-Agent
     */
    public String getUserAgent() {
        if (defaultUserAgent == null) {
            defaultUserAgent = ShopifyConstants.DEFAULT_USER_AGENT;
        }
        return defaultUserAgent;
    }
    
    private WebClient createDefaultWebClient() {
        int connectTimeout = isTestEnvironment() ? 5000 : 10000;
        int readTimeout = isTestEnvironment() ? 5000 : 30000;
        
        HttpClient httpClient = HttpClient.create(connectionProvider)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
            .responseTimeout(Duration.ofMillis(readTimeout))
            .doOnConnected(conn ->
                conn.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS))
                    .addHandlerLast(new WriteTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS)))
            .compress(true)
            .followRedirect(true);
        
        return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .defaultHeader("User-Agent", getUserAgent())
            .codecs(configurer -> configurer
                .defaultCodecs()
                .maxInMemorySize(5 * 1024 * 1024))
            .build();
    }
}