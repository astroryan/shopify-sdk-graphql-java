package com.shopify.sdk.model.metaobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for creating a metaobject definition.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectDefinitionCreateInput {
    /**
     * The type identifier for the metaobject definition.
     */
    @JsonProperty("type")
    private String type;
    
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
     * The field definitions.
     */
    @JsonProperty("fieldDefinitions")
    private List<MetaobjectFieldDefinitionCreateInput> fieldDefinitions;
    
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