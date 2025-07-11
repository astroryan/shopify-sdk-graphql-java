package com.shopify.sdk.model.marketplace;

import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a publication.
 * A publication is a channel where products can be made available to customers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Publication implements Node {
    /**
     * A globally-unique identifier for the publication
     */
    private String id;
    
    /**
     * The name of the publication
     */
    private String name;
    
    /**
     * The channel associated with the publication
     */
    private Channel channel;
    
    /**
     * Whether the publication supports future publishing
     */
    private Boolean supportsFuturePublishing;
    
    /**
     * The products published to this publication
     */
    private ProductPublicationConnection productPublications;
}