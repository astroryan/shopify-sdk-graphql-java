package com.shopify.sdk.client.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Represents a GraphQL request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphQLRequest {
    
    @JsonProperty("query")
    private String query;
    
    @JsonProperty("variables")
    private Map<String, Object> variables;
    
    @JsonProperty("operationName")
    private String operationName;
    
    public static GraphQLRequest of(String query) {
        return new GraphQLRequest(query, null, null);
    }
    
    public static GraphQLRequest of(String query, Map<String, Object> variables) {
        return new GraphQLRequest(query, variables, null);
    }
    
    public static GraphQLRequest of(String query, Map<String, Object> variables, String operationName) {
        return new GraphQLRequest(query, variables, operationName);
    }
}