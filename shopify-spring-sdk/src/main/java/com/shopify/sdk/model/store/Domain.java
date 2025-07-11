package com.shopify.sdk.model.store;

import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a domain.
 * A domain is a URL that customers can use to access a shop's storefront.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Domain implements Node {
    /**
     * A globally-unique identifier for the domain
     */
    private String id;
    
    /**
     * The host name of the domain (e.g., example.com)
     */
    private String host;
    
    /**
     * Whether SSL is enabled for this domain
     */
    private Boolean sslEnabled;
    
    /**
     * The URL of the domain (e.g., https://example.com)
     */
    private String url;
}