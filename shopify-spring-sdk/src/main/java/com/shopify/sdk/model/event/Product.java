package com.shopify.sdk.model.event;

import com.shopify.sdk.model.common.Node;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.time.Instant;
import java.util.List;

/**
 * Represents a product in the context of events.
 * This simplified version is used for event tracking, webhooks, and change notifications.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Node {
    
    @GraphQLQuery(name = "id", description = "A globally-unique ID")
    private String id;
    
    @GraphQLQuery(name = "legacyResourceId", description = "The ID of the corresponding resource in the REST Admin API")
    private String legacyResourceId;
    
    @GraphQLQuery(name = "handle", description = "A unique human-friendly string of the product's title")
    private String handle;
    
    @GraphQLQuery(name = "title", description = "The product's title")
    private String title;
    
    @GraphQLQuery(name = "description", description = "A description of the product")
    private String description;
    
    @GraphQLQuery(name = "descriptionHtml", description = "The description of the product, with HTML formatting")
    private String descriptionHtml;
    
    @GraphQLQuery(name = "vendor", description = "The name of the product's vendor")
    private String vendor;
    
    @GraphQLQuery(name = "productType", description = "The product type")
    private String productType;
    
    @GraphQLQuery(name = "status", description = "The product status")
    private ProductStatus status;
    
    @GraphQLQuery(name = "tags", description = "A list of searchable keywords associated with the product")
    private List<String> tags;
    
    @GraphQLQuery(name = "createdAt", description = "The date and time when the product was created")
    private Instant createdAt;
    
    @GraphQLQuery(name = "updatedAt", description = "The date and time when the product was last updated")
    private Instant updatedAt;
    
    @GraphQLQuery(name = "publishedAt", description = "The date and time when the product was published to the online store")
    private Instant publishedAt;
    
    @GraphQLQuery(name = "templateSuffix", description = "The suffix of the liquid template being used")
    private String templateSuffix;
    
    @GraphQLQuery(name = "requiresSellingPlan", description = "Whether the product can only be purchased with a selling plan")
    private Boolean requiresSellingPlan;
    
    @GraphQLQuery(name = "totalInventory", description = "The sum of the quantities for all variants")
    private Integer totalInventory;
    
    @GraphQLQuery(name = "tracksInventory", description = "Whether inventory tracking has been enabled")
    private Boolean tracksInventory;
    
    @GraphQLQuery(name = "onlineStoreUrl", description = "The online store URL for the product")
    private String onlineStoreUrl;
    
    /**
     * Product status enum
     */
    public enum ProductStatus {
        ACTIVE,
        ARCHIVED,
        DRAFT
    }
}