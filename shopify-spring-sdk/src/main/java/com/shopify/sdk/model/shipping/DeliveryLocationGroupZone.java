package com.shopify.sdk.model.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a zone within a delivery location group.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryLocationGroupZone implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The name of the zone.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The countries in this zone.
     */
    @JsonProperty("countries")
    private List<DeliveryCountry> countries;
    
    /**
     * The method definitions in this zone.
     */
    @JsonProperty("methodDefinitions")
    private DeliveryMethodDefinitionConnection methodDefinitions;
}

/**
 * A delivery country.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryCountry {
    /**
     * The country code.
     */
    @JsonProperty("code")
    private String code;
    
    /**
     * The name of the country.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The provinces in the country.
     */
    @JsonProperty("provinces")
    private List<DeliveryProvince> provinces;
}

/**
 * A delivery province.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryProvince {
    /**
     * The province code.
     */
    @JsonProperty("code")
    private String code;
    
    /**
     * The name of the province.
     */
    @JsonProperty("name")
    private String name;
}

/**
 * A connection to delivery method definitions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryMethodDefinitionConnection {
    /**
     * The edges in the connection.
     */
    @JsonProperty("edges")
    private List<DeliveryMethodDefinitionEdge> edges;
    
    /**
     * The nodes in the connection.
     */
    @JsonProperty("nodes")
    private List<DeliveryMethodDefinition> nodes;
}

/**
 * An edge in a delivery method definition connection.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryMethodDefinitionEdge {
    /**
     * The cursor for pagination.
     */
    @JsonProperty("cursor")
    private String cursor;
    
    /**
     * The method definition node.
     */
    @JsonProperty("node")
    private DeliveryMethodDefinition node;
}