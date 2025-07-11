package com.shopify.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.config.ShopifyProperties;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.exception.ShopifyRateLimitException;
import com.shopify.sdk.model.graphql.GraphQLRequest;
import com.shopify.sdk.model.graphql.GraphQLResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class ShopifyGraphQLClient implements GraphQLClient {
    
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final String HEADER_ACCESS_TOKEN = "X-Shopify-Access-Token";
    private static final String HEADER_RATE_LIMIT_REMAINING = "X-Shopify-Shop-Api-Call-Limit";
    
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final RateLimiter rateLimiter;
    private final ShopifyProperties properties;
    
    private ThreadLocal<ShopifyAuthContext> authContext = new ThreadLocal<>();
    
    public void setAuthContext(ShopifyAuthContext context) {
        authContext.set(context);
    }
    
    @Override
    public <T> GraphQLResponse<T> execute(GraphQLRequest request, Class<T> responseType) {
        try {
            return executeInternal(request, responseType);
        } catch (Exception e) {
            throw new ShopifyApiException("Failed to execute GraphQL request", e);
        }
    }
    
    @Override
    public <T> CompletableFuture<GraphQLResponse<T>> executeAsync(GraphQLRequest request, Class<T> responseType) {
        return CompletableFuture.supplyAsync(() -> execute(request, responseType));
    }
    
    @Override
    public <T> GraphQLResponse<T> executeMutation(GraphQLRequest request, Class<T> responseType) {
        return execute(request, responseType);
    }
    
    @Override
    public <T> CompletableFuture<GraphQLResponse<T>> executeMutationAsync(GraphQLRequest request, Class<T> responseType) {
        return executeAsync(request, responseType);
    }
    
    private <T> GraphQLResponse<T> executeInternal(GraphQLRequest request, Class<T> responseType) throws IOException, InterruptedException {
        ShopifyAuthContext context = authContext.get();
        if (context == null) {
            throw new IllegalStateException("ShopifyAuthContext not set");
        }
        
        // Apply rate limiting
        if (properties.getRateLimit().isEnabled()) {
            if (!rateLimiter.tryAcquire(30, TimeUnit.SECONDS)) {
                throw new ShopifyRateLimitException("Rate limit timeout");
            }
        }
        
        String requestBody = objectMapper.writeValueAsString(request);
        
        if (properties.getLogging().isLogRequests()) {
            log.info("GraphQL Request: {}", requestBody);
        }
        
        Request httpRequest = new Request.Builder()
                .url(context.getGraphQLEndpoint())
                .header(HEADER_ACCESS_TOKEN, context.getAccessToken())
                .post(RequestBody.create(requestBody, JSON))
                .build();
        
        try (Response response = httpClient.newCall(httpRequest).execute()) {
            String responseBody = response.body() != null ? response.body().string() : "";
            
            if (properties.getLogging().isLogResponses()) {
                log.info("GraphQL Response: {}", responseBody);
            }
            
            // Check rate limit headers
            String rateLimitHeader = response.header(HEADER_RATE_LIMIT_REMAINING);
            if (rateLimitHeader != null) {
                log.debug("Rate limit remaining: {}", rateLimitHeader);
            }
            
            if (!response.isSuccessful()) {
                handleErrorResponse(response.code(), responseBody);
            }
            
            TypeReference<GraphQLResponse<T>> typeRef = new TypeReference<>() {
                @Override
                public java.lang.reflect.Type getType() {
                    return objectMapper.getTypeFactory()
                            .constructParametricType(GraphQLResponse.class, responseType);
                }
            };
            
            GraphQLResponse<T> graphQLResponse = objectMapper.readValue(responseBody, typeRef);
            
            if (graphQLResponse.hasErrors()) {
                log.error("GraphQL errors: {}", graphQLResponse.getErrors());
            }
            
            return graphQLResponse;
        }
    }
    
    private void handleErrorResponse(int statusCode, String responseBody) {
        switch (statusCode) {
            case 429:
                throw new ShopifyRateLimitException("Rate limit exceeded");
            case 401:
                throw new ShopifyApiException("Unauthorized: Invalid access token");
            case 403:
                throw new ShopifyApiException("Forbidden: Access denied");
            default:
                throw new ShopifyApiException(
                        String.format("HTTP %d: %s", statusCode, responseBody)
                );
        }
    }
}