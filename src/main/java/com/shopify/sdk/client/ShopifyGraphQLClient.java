package com.shopify.sdk.client;

import com.shopify.sdk.client.graphql.GraphQLClient;
import com.shopify.sdk.client.graphql.GraphQLRequest;
import com.shopify.sdk.client.graphql.GraphQLResponse;
import com.shopify.sdk.config.ShopifyAuthContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Main GraphQL client for Shopify APIs.
 * This is the primary entry point for GraphQL operations.
 */
@Component
@RequiredArgsConstructor
public class ShopifyGraphQLClient {
    
    private final GraphQLClient graphQLClient;
    private final ShopifyAuthContext context;
    
    /**
     * Executes a GraphQL query against the Admin API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param query the GraphQL query string
     * @return Mono of GraphQLResponse
     */
    public Mono<GraphQLResponse> query(String shop, String accessToken, String query) {
        return graphQLClient.query(context, shop, accessToken, query);
    }
    
    /**
     * Executes a GraphQL query with variables against the Admin API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param query the GraphQL query string
     * @param variables the query variables
     * @return Mono of GraphQLResponse
     */
    public Mono<GraphQLResponse> query(String shop, String accessToken, String query, Map<String, Object> variables) {
        return graphQLClient.query(context, shop, accessToken, query, variables);
    }
    
    /**
     * Executes a GraphQL request against the Admin API.
     *
     * @param shop the shop domain
     * @param accessToken the access token
     * @param request the GraphQL request
     * @return Mono of GraphQLResponse
     */
    public Mono<GraphQLResponse> execute(String shop, String accessToken, GraphQLRequest request) {
        return graphQLClient.executeAdminQuery(context, shop, accessToken, request);
    }
    
    /**
     * Executes a GraphQL query against the Storefront API.
     *
     * @param shop the shop domain
     * @param storefrontAccessToken the storefront access token
     * @param query the GraphQL query string
     * @return Mono of GraphQLResponse
     */
    public Mono<GraphQLResponse> storefrontQuery(String shop, String storefrontAccessToken, String query) {
        GraphQLRequest request = GraphQLRequest.of(query);
        return graphQLClient.executeStorefrontQuery(context, shop, storefrontAccessToken, request);
    }
    
    /**
     * Executes a GraphQL query with variables against the Storefront API.
     *
     * @param shop the shop domain
     * @param storefrontAccessToken the storefront access token
     * @param query the GraphQL query string
     * @param variables the query variables
     * @return Mono of GraphQLResponse
     */
    public Mono<GraphQLResponse> storefrontQuery(String shop, String storefrontAccessToken, String query, Map<String, Object> variables) {
        GraphQLRequest request = GraphQLRequest.of(query, variables);
        return graphQLClient.executeStorefrontQuery(context, shop, storefrontAccessToken, request);
    }
}