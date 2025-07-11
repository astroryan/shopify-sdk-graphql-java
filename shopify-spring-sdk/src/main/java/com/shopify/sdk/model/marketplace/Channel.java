package com.shopify.sdk.model.marketplace;

import com.shopify.sdk.model.common.Node;
import com.shopify.sdk.model.common.NavigationItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a channel.
 * A channel represents an app where you can publish a resource, such as a product.
 * A channel can be a platform or marketplace such as Facebook or Pinterest, an online store,
 * or POS.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Channel implements Node {
    /**
     * A globally-unique identifier for the channel
     */
    private String id;
    
    /**
     * The app associated with the channel
     */
    private App app;
    
    /**
     * The handle of the channel
     */
    private String handle;
    
    /**
     * Whether the channel has a collection
     */
    private Boolean hasCollection;
    
    /**
     * The name of the channel
     */
    private String name;
    
    /**
     * The navigation items for the channel's dashboard
     */
    private List<NavigationItem> navigationItems;
    
    /**
     * The overview path for the channel's dashboard
     */
    private String overviewPath;
    
    /**
     * The count of products published to this channel
     */
    private ProductsCount productsCount;
    
    /**
     * Whether the channel supports future publishing
     */
    private Boolean supportsFuturePublishing;
}