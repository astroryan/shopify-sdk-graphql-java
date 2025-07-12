package com.shopify.sdk.model.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a carrier service for delivery.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryCarrierService implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The name of the carrier service.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * Whether the carrier service is active.
     */
    @JsonProperty("active")
    private Boolean active;
    
    /**
     * The callback URL for the carrier service.
     */
    @JsonProperty("callbackUrl")
    private String callbackUrl;
    
    /**
     * The service discovery setting.
     */
    @JsonProperty("serviceDiscovery")
    private Boolean serviceDiscovery;
    
    /**
     * The type of carrier service.
     */
    @JsonProperty("type")
    private DeliveryCarrierServiceType type;
}

/**
 * The type of carrier service.
 */
enum DeliveryCarrierServiceType {
    @JsonProperty("API")
    API,
    
    @JsonProperty("LEGACY")
    LEGACY
}