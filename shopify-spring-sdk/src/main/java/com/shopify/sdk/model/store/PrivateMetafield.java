package com.shopify.sdk.model.store;

import com.shopify.sdk.model.common.Node;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.time.Instant;

/**
 * Represents a private metafield.
 * Note: Private metafields are deprecated. It's recommended to migrate to metafields
 * created with a reserved namespace.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Deprecated
public class PrivateMetafield implements Node {
    
    @GraphQLQuery(name = "id", description = "The ID of the private metafield")
    private String id;
    
    @GraphQLQuery(name = "namespace", description = "The namespace of the private metafield")
    private String namespace;
    
    @GraphQLQuery(name = "key", description = "The key name of the private metafield")
    private String key;
    
    @GraphQLQuery(name = "value", description = "The value of a private metafield")
    private String value;
    
    @GraphQLQuery(name = "valueType", description = "Represents the private metafield value type")
    private PrivateMetafieldValueType valueType;
    
    @GraphQLQuery(name = "createdAt", description = "The date and time when the private metafield was created")
    private Instant createdAt;
    
    @GraphQLQuery(name = "updatedAt", description = "The date and time when the private metafield was updated")
    private Instant updatedAt;
    
    /**
     * The value type of a private metafield
     */
    public enum PrivateMetafieldValueType {
        /**
         * A string value type
         */
        STRING,
        
        /**
         * An integer value type
         */
        INTEGER,
        
        /**
         * A JSON string value type
         */
        JSON_STRING
    }
}