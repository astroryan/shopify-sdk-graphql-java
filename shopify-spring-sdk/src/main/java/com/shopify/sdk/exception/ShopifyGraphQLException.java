package com.shopify.sdk.exception;

import lombok.Getter;

import java.util.List;

/**
 * Exception thrown when GraphQL queries return errors.
 */
@Getter
public class ShopifyGraphQLException extends ShopifyApiException {
    
    private final List<GraphQLError> errors;
    
    public ShopifyGraphQLException(String message, List<GraphQLError> errors) {
        super(message);
        this.errors = errors;
    }
    
    public ShopifyGraphQLException(List<GraphQLError> errors) {
        super("GraphQL request failed with " + errors.size() + " error(s)");
        this.errors = errors;
    }
    
    @Getter
    public static class GraphQLError {
        private final String message;
        private final List<Location> locations;
        private final List<String> path;
        private final String extensions;
        
        public GraphQLError(String message, List<Location> locations, List<String> path, String extensions) {
            this.message = message;
            this.locations = locations;
            this.path = path;
            this.extensions = extensions;
        }
        
        @Getter
        public static class Location {
            private final int line;
            private final int column;
            
            public Location(int line, int column) {
                this.line = line;
                this.column = column;
            }
        }
    }
}