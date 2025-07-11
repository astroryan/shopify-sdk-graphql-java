package com.shopify.sdk.model.store;

import com.shopify.sdk.model.common.Location;
import com.shopify.sdk.model.common.Node;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

/**
 * Represents a fulfillment service.
 * A fulfillment service is a third-party provider that handles the logistics of fulfilling orders.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentService implements Node {
    
    @GraphQLQuery(name = "id", description = "The ID of the fulfillment service")
    private String id;
    
    @GraphQLQuery(name = "serviceName", description = "The name of the fulfillment service as seen by merchants")
    private String serviceName;
    
    @GraphQLQuery(name = "handle", description = "Human-readable unique identifier for this fulfillment service")
    private String handle;
    
    @GraphQLQuery(name = "callbackUrl", description = "The URL to send requests for the fulfillment service")
    private String callbackUrl;
    
    @GraphQLQuery(name = "type", description = "Type associated with the fulfillment service")
    private FulfillmentServiceType type;
    
    @GraphQLQuery(name = "inventoryManagement", description = "Whether the fulfillment service tracks product inventory and provides updates to Shopify")
    private Boolean inventoryManagement;
    
    @GraphQLQuery(name = "trackingSupport", description = "Whether the fulfillment service implemented the /fetch_tracking_numbers endpoint")
    private Boolean trackingSupport;
    
    @GraphQLQuery(name = "permitsSkuSharing", description = "Whether the fulfillment service can stock inventory alongside other locations")
    private Boolean permitsSkuSharing;
    
    @GraphQLQuery(name = "location", description = "Location associated with the fulfillment service")
    private Location location;
    
    @Deprecated
    @GraphQLQuery(name = "fulfillmentOrdersOptIn", description = "Whether the service uses the fulfillment order workflow (deprecated)")
    private Boolean fulfillmentOrdersOptIn;
    
    /**
     * The type of fulfillment service
     */
    public enum FulfillmentServiceType {
        /**
         * Manual fulfillment service
         */
        MANUAL,
        
        /**
         * Gift card fulfillment service
         */
        GIFT_CARD,
        
        /**
         * Third-party fulfillment service
         */
        THIRD_PARTY
    }
}