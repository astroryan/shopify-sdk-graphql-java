package com.shopify.sdk.client.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Represents a GraphQL response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphQLResponse {
    
    @JsonProperty("data")
    private JsonNode data;
    
    @JsonProperty("errors")
    private List<GraphQLError> errors;
    
    @JsonProperty("extensions")
    private Map<String, Object> extensions;
    
    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }
    
    public boolean hasData() {
        return data != null && !data.isNull();
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GraphQLError {
        
        @JsonProperty("message")
        private String message;
        
        @JsonProperty("locations")
        private List<Location> locations;
        
        @JsonProperty("path")
        private List<String> path;
        
        @JsonProperty("extensions")
        private Map<String, Object> extensions;
        
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Location {
            
            @JsonProperty("line")
            private int line;
            
            @JsonProperty("column")
            private int column;
        }
    }
}