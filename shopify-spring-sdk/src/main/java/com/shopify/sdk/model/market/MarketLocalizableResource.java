package com.shopify.sdk.model.market;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a resource that can be localized for a market.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketLocalizableResource implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The resource type.
     */
    @JsonProperty("resourceType")
    private String resourceType;
    
    /**
     * The localizations for this resource.
     */
    @JsonProperty("localizations")
    private MarketLocalizationConnection localizations;
}