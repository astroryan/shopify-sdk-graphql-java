package com.shopify.sdk.model.metaobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for updating a metaobject definition.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectDefinitionUpdateInput {
    /**
     * The ID of the metaobject definition to update.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The name of the metaobject definition.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The description of the metaobject definition.
     */
    @JsonProperty("description")
    private String description;
    
    /**
     * The display name key.
     */
    @JsonProperty("displayNameKey")
    private String displayNameKey;
    
    /**
     * Field definitions to update.
     */
    @JsonProperty("fieldDefinitions")
    private List<MetaobjectFieldDefinitionUpdateInput> fieldDefinitions;
    
    /**
     * Whether SEO is enabled.
     */
    @JsonProperty("hasSeoEnabled")
    private Boolean hasSeoEnabled;
    
    /**
     * Admin access settings.
     */
    @JsonProperty("adminAccess")
    private MetaobjectAdminAccess adminAccess;
    
    /**
     * Storefront access settings.
     */
    @JsonProperty("storefrontAccess")
    private MetaobjectStorefrontAccess storefrontAccess;
}