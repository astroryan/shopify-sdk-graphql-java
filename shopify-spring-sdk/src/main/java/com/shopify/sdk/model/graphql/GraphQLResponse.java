package com.shopify.sdk.model.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphQLResponse<T> {
    private T data;
    private List<GraphQLError> errors;
    private Map<String, Object> extensions;
    
    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }
    
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GraphQLError {
        private String message;
        private List<Location> locations;
        private List<String> path;
        private Map<String, Object> extensions;
        
        @Data
        public static class Location {
            private int line;
            private int column;
        }
    }
}