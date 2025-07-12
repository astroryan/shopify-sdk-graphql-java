package com.shopify.sdk.model.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a delivery profile that contains shipping rates and zones.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryProfile implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The name of the delivery profile.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * Whether this is the default profile.
     */
    @JsonProperty("default")
    private Boolean isDefault;
    
    /**
     * The location groups associated with the profile.
     */
    @JsonProperty("profileLocationGroups")
    private List<DeliveryProfileLocationGroup> profileLocationGroups;
    
    /**
     * The zones that have been created in this profile.
     */
    @JsonProperty("zoneCountryCount")
    private Integer zoneCountryCount;
    
    /**
     * The number of origin locations associated with this profile.
     */
    @JsonProperty("originLocationCount")
    private Integer originLocationCount;
    
    /**
     * The number of products associated with this profile.
     */
    @JsonProperty("productVariantsCount")
    private ProductVariantsCount productVariantsCount;
}

/**
 * A location group within a delivery profile.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryProfileLocationGroup {
    /**
     * The ID of the location group.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The locations in this group.
     */
    @JsonProperty("locations")
    private LocationConnection locations;
    
    /**
     * The zones in this location group.
     */
    @JsonProperty("locationGroupZones")
    private DeliveryLocationGroupZoneConnection locationGroupZones;
}

/**
 * Product variants count information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ProductVariantsCount {
    /**
     * The count of product variants.
     */
    @JsonProperty("count")
    private Integer count;
    
    /**
     * Whether the count is capped.
     */
    @JsonProperty("capped")
    private Boolean capped;
}

/**
 * A connection to locations.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class LocationConnection {
    /**
     * The total count of locations.
     */
    @JsonProperty("totalCount")
    private Integer totalCount;
}

/**
 * A connection to delivery location group zones.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryLocationGroupZoneConnection {
    /**
     * The edges in the connection.
     */
    @JsonProperty("edges")
    private List<DeliveryLocationGroupZoneEdge> edges;
    
    /**
     * The nodes in the connection.
     */
    @JsonProperty("nodes")
    private List<DeliveryLocationGroupZone> nodes;
}

/**
 * An edge in a delivery location group zone connection.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryLocationGroupZoneEdge {
    /**
     * The cursor for pagination.
     */
    @JsonProperty("cursor")
    private String cursor;
    
    /**
     * The zone node.
     */
    @JsonProperty("node")
    private DeliveryLocationGroupZone node;
}