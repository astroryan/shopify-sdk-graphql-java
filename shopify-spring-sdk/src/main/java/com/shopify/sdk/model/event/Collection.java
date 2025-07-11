package com.shopify.sdk.model.event;

import com.shopify.sdk.model.common.Node;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.time.Instant;

/**
 * Represents a collection in the context of events.
 * A collection is a grouping of products that merchants can create to make their stores easier to browse.
 * This simplified version is used for event tracking and webhook payloads.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Collection implements Node {
    
    @GraphQLQuery(name = "id", description = "A globally-unique ID")
    private String id;
    
    @GraphQLQuery(name = "title", description = "The collection's title")
    private String title;
    
    @GraphQLQuery(name = "handle", description = "A unique human-friendly string for the collection")
    private String handle;
    
    @GraphQLQuery(name = "description", description = "A description of the collection")
    private String description;
    
    @GraphQLQuery(name = "descriptionHtml", description = "The description of the collection, with HTML formatting")
    private String descriptionHtml;
    
    @GraphQLQuery(name = "updatedAt", description = "The date and time when the collection was last modified")
    private Instant updatedAt;
    
    @GraphQLQuery(name = "createdAt", description = "The date and time when the collection was created")
    private Instant createdAt;
    
    @GraphQLQuery(name = "publishedAt", description = "The date and time when the collection was published")
    private Instant publishedAt;
    
    @GraphQLQuery(name = "image", description = "The collection's image")
    private Image image;
    
    @GraphQLQuery(name = "sortOrder", description = "The order in which products in the collection appear")
    private String sortOrder;
    
    @GraphQLQuery(name = "templateSuffix", description = "The suffix of the liquid template being used")
    private String templateSuffix;
    
    /**
     * Simplified Image representation for collections
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Image {
        @GraphQLQuery(name = "id", description = "A unique ID for the image")
        private String id;
        
        @GraphQLQuery(name = "altText", description = "A word or phrase to describe the image")
        private String altText;
        
        @GraphQLQuery(name = "url", description = "The original image URL")
        private String url;
        
        @GraphQLQuery(name = "width", description = "The original width of the image")
        private Integer width;
        
        @GraphQLQuery(name = "height", description = "The original height of the image")
        private Integer height;
    }
}