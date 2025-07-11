package com.shopify.sdk.model.common;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

/**
 * Represents a publication (sales channel) in Shopify.
 * Publications are used to control where products and collections are visible.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Publication implements Node {
    
    @GraphQLQuery(name = "id", description = "A globally-unique ID")
    private String id;
    
    @GraphQLQuery(name = "name", description = "The name of the publication")
    private String name;
    
    @GraphQLQuery(name = "app", description = "The app associated with the publication")
    private App app;
    
    @GraphQLQuery(name = "supportsFuturePublishing", description = "Whether the publication supports future publishing")
    private Boolean supportsFuturePublishing;
    
    /**
     * Simplified App representation for publications
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class App {
        @GraphQLQuery(name = "id", description = "A globally-unique ID")
        private String id;
        
        @GraphQLQuery(name = "title", description = "The name of the app")
        private String title;
        
        @GraphQLQuery(name = "handle", description = "The unique handle of the app")
        private String handle;
    }
}