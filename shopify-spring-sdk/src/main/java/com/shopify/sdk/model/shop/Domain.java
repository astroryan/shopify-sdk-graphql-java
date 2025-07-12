package com.shopify.sdk.model.shop;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a domain for a shop.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Domain implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The host of the domain.
     */
    @JsonProperty("host")
    private String host;
    
    /**
     * Whether the domain is primary.
     */
    @JsonProperty("isPrimary")
    private Boolean isPrimary;
    
    /**
     * The SSL certificate for the domain.
     */
    @JsonProperty("sslEnabled")
    private Boolean sslEnabled;
    
    /**
     * The URL of the domain.
     */
    @JsonProperty("url")
    private String url;
}