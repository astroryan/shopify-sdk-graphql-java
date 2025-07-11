package com.shopify.sdk.model.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Represents a GraphQL request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphQLRequest {
    /**
     * The GraphQL query string
     */
    @JsonProperty("query")
    private String query;
    
    /**
     * Variables for the GraphQL query
     */
    @JsonProperty("variables")
    private Map<String, Object> variables;
    
    /**
     * The operation name (optional, used when query contains multiple operations)
     */
    @JsonProperty("operationName")
    private String operationName;
    
    /**
     * Create a simple query request without variables
     * @param query The GraphQL query
     * @return A new GraphQLRequest instance
     */
    public static GraphQLRequest of(String query) {
        return GraphQLRequest.builder()
                .query(query)
                .build();
    }
    
    /**
     * Create a query request with variables
     * @param query The GraphQL query
     * @param variables The query variables
     * @return A new GraphQLRequest instance
     */
    public static GraphQLRequest of(String query, Map<String, Object> variables) {
        return GraphQLRequest.builder()
                .query(query)
                .variables(variables)
                .build();
    }
}