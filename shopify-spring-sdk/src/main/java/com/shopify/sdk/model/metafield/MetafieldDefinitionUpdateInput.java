package com.shopify.sdk.model.metafield;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for updating a metafield definition.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldDefinitionUpdateInput {
    /**
     * The ID of the metafield definition to update.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The updated human-readable name of the metafield definition.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The updated description of the metafield definition.
     */
    @JsonProperty("description")
    private String description;
    
    /**
     * The updated validation rules for the metafield definition.
     */
    @JsonProperty("validations")
    private List<MetafieldDefinitionValidationInput> validations;
    
    /**
     * Whether metafields of this definition should be visible in the Storefront API.
     */
    @JsonProperty("visibleToStorefrontApi")
    private Boolean visibleToStorefrontApi;
    
    /**
     * Whether the metafield definition should be pinned.
     */
    @JsonProperty("pin")
    private Boolean pin;
    
    /**
     * Whether the metafield definition should be unpinned.
     */
    @JsonProperty("unpin")
    private Boolean unpin;
}