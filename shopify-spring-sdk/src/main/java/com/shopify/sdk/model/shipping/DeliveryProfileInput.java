package com.shopify.sdk.model.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for creating or updating a delivery profile.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryProfileInput {
    /**
     * The name of the delivery profile.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The location groups to associate with the profile.
     */
    @JsonProperty("profileLocationGroups")
    private List<DeliveryProfileLocationGroupInput> profileLocationGroups;
    
    /**
     * The locations to use for this profile.
     */
    @JsonProperty("locationsToAssociate")
    private List<String> locationsToAssociate;
    
    /**
     * The locations to remove from this profile.
     */
    @JsonProperty("locationsToDisassociate")
    private List<String> locationsToDisassociate;
    
    /**
     * The variants to associate with this profile.
     */
    @JsonProperty("variantsToAssociate")
    private List<String> variantsToAssociate;
    
    /**
     * The variants to disassociate from this profile.
     */
    @JsonProperty("variantsToDisassociate")
    private List<String> variantsToDisassociate;
}

/**
 * Input for a delivery profile location group.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryProfileLocationGroupInput {
    /**
     * The ID of the location group.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The locations in this group.
     */
    @JsonProperty("locations")
    private List<String> locations;
}