package com.shopify.sdk.model.event;

import com.shopify.sdk.model.common.Publication;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.time.Instant;

/**
 * Represents a resource publication, which connects a publishable resource (Product or Collection) to a publication.
 * This is used to track when resources are published to various sales channels.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourcePublicationV2 {
    
    @GraphQLQuery(name = "isPublished", description = "Whether the resource publication is published. If true, then the resource publication is published to the publication")
    private Boolean isPublished;
    
    @GraphQLQuery(name = "publication", description = "The publication the resource publication is published to")
    private Publication publication;
    
    @GraphQLQuery(name = "publishDate", description = "The date that the resource publication was or is going to be published to the publication")
    private Instant publishDate;
    
    @GraphQLQuery(name = "publishable", description = "The resource published to the publication")
    private Publishable publishable;
    
    /**
     * Interface for publishable resources (Product or Collection)
     */
    public interface Publishable {
        String getId();
        String getResourceType();
    }
    
    /**
     * Wrapper for Product as Publishable
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PublishableProduct implements Publishable {
        private Product product;
        
        @Override
        public String getId() {
            return product != null ? product.getId() : null;
        }
        
        @Override
        public String getResourceType() {
            return "PRODUCT";
        }
    }
    
    /**
     * Wrapper for Collection as Publishable
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PublishableCollection implements Publishable {
        private Collection collection;
        
        @Override
        public String getId() {
            return collection != null ? collection.getId() : null;
        }
        
        @Override
        public String getResourceType() {
            return "COLLECTION";
        }
    }
}