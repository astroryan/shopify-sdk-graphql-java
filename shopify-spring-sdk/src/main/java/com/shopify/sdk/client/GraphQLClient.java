package com.shopify.sdk.client;

import com.shopify.sdk.model.graphql.GraphQLRequest;
import com.shopify.sdk.model.graphql.GraphQLResponse;

import java.util.concurrent.CompletableFuture;

public interface GraphQLClient {
    
    <T> GraphQLResponse<T> execute(GraphQLRequest request, Class<T> responseType);
    
    <T> CompletableFuture<GraphQLResponse<T>> executeAsync(GraphQLRequest request, Class<T> responseType);
    
    <T> GraphQLResponse<T> executeMutation(GraphQLRequest request, Class<T> responseType);
    
    <T> CompletableFuture<GraphQLResponse<T>> executeMutationAsync(GraphQLRequest request, Class<T> responseType);
}