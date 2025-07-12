package com.shopify.sdk.client.graphql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.client.HttpClientConfig;
import com.shopify.sdk.config.ShopifyAuthContext;
import com.shopify.sdk.exception.ShopifyGraphQLException;
import com.shopify.sdk.exception.ShopifyHttpException;
import com.shopify.sdk.model.common.ShopifyHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GraphQL client for Shopify Admin and Storefront APIs.
 */
@Component
@RequiredArgsConstructor
public class GraphQLClient {
    
    private final HttpClientConfig httpClientConfig;
    private final ObjectMapper objectMapper;
    
    /**
     * Executes a GraphQL query against the Admin API.
     *
     * @param context the Shopify configuration context
     * @param shop the shop domain
     * @param accessToken the access token
     * @param request the GraphQL request
     * @return Mono of GraphQLResponse
     */
    public Mono<GraphQLResponse> executeAdminQuery(ShopifyAuthContext context, String shop, String accessToken, GraphQLRequest request) {
        WebClient webClient = httpClientConfig.createAdminApiClient(context, shop);
        return executeQuery(webClient, accessToken, request, true);
    }
    
    /**
     * Executes a GraphQL query against the Storefront API.
     *
     * @param context the Shopify configuration context
     * @param shop the shop domain
     * @param accessToken the storefront access token
     * @param request the GraphQL request
     * @return Mono of GraphQLResponse
     */
    public Mono<GraphQLResponse> executeStorefrontQuery(ShopifyAuthContext context, String shop, String accessToken, GraphQLRequest request) {
        WebClient webClient = httpClientConfig.createStorefrontApiClient(context, shop);
        return executeQuery(webClient, accessToken, request, false);
    }
    
    /**
     * Executes a GraphQL query with variables.
     *
     * @param context the Shopify configuration context
     * @param shop the shop domain
     * @param accessToken the access token
     * @param query the GraphQL query string
     * @param variables the query variables
     * @return Mono of GraphQLResponse
     */
    public Mono<GraphQLResponse> query(ShopifyAuthContext context, String shop, String accessToken, String query, Map<String, Object> variables) {
        GraphQLRequest request = GraphQLRequest.of(query, variables);
        return executeAdminQuery(context, shop, accessToken, request);
    }
    
    /**
     * Executes a simple GraphQL query without variables.
     *
     * @param context the Shopify configuration context
     * @param shop the shop domain
     * @param accessToken the access token
     * @param query the GraphQL query string
     * @return Mono of GraphQLResponse
     */
    public Mono<GraphQLResponse> query(ShopifyAuthContext context, String shop, String accessToken, String query) {
        return query(context, shop, accessToken, query, null);
    }
    
    private Mono<GraphQLResponse> executeQuery(WebClient webClient, String accessToken, GraphQLRequest request, boolean isAdminApi) {
        try {
            String requestBody = objectMapper.writeValueAsString(request);
            
            WebClient.RequestHeadersSpec<?> requestSpec = webClient
                .post()
                .uri(isAdminApi ? "/graphql.json" : "/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody);
            
            // Add appropriate access token header
            if (isAdminApi) {
                requestSpec = requestSpec.header(ShopifyHeader.ACCESS_TOKEN.getHeaderName(), accessToken);
            } else {
                requestSpec = requestSpec.header(ShopifyHeader.STOREFRONT_PRIVATE_TOKEN.getHeaderName(), accessToken);
            }
            
            return requestSpec
                .retrieve()
                .bodyToMono(String.class)
                .map(this::parseResponse)
                .flatMap(this::validateResponse)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                    .filter(this::shouldRetry));
            
        } catch (JsonProcessingException e) {
            return Mono.error(new ShopifyGraphQLException("Failed to serialize GraphQL request", null));
        }
    }
    
    private GraphQLResponse parseResponse(String responseBody) {
        try {
            return objectMapper.readValue(responseBody, GraphQLResponse.class);
        } catch (JsonProcessingException e) {
            throw new ShopifyGraphQLException("Failed to parse GraphQL response: " + e.getMessage(), null);
        }
    }
    
    private Mono<GraphQLResponse> validateResponse(GraphQLResponse response) {
        if (response.hasErrors()) {
            return Mono.error(new ShopifyGraphQLException(
                response.getErrors().stream()
                    .map(GraphQLResponse.GraphQLError::getMessage)
                    .collect(Collectors.joining(", ")),
                convertErrors(response.getErrors())
            ));
        }
        return Mono.just(response);
    }
    
    private boolean shouldRetry(Throwable throwable) {
        if (throwable instanceof ShopifyHttpException) {
            ShopifyHttpException httpEx = (ShopifyHttpException) throwable;
            return httpEx.isRateLimited() || httpEx.isServerError();
        }
        return false;
    }
    
    private java.util.List<ShopifyGraphQLException.GraphQLError> convertErrors(java.util.List<GraphQLResponse.GraphQLError> errors) {
        return errors.stream()
            .map(error -> new ShopifyGraphQLException.GraphQLError(
                error.getMessage(),
                error.getLocations() != null ? 
                    error.getLocations().stream()
                        .map(loc -> new ShopifyGraphQLException.GraphQLError.Location(loc.getLine(), loc.getColumn()))
                        .collect(Collectors.toList()) : null,
                error.getPath(),
                error.getExtensions() != null ? error.getExtensions().toString() : null
            ))
            .collect(Collectors.toList());
    }
}