package com.shopify.sdk.model.graphql;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class GraphQLRequest {
    private final String query;
    private final Map<String, Object> variables;
    private final String operationName;
}