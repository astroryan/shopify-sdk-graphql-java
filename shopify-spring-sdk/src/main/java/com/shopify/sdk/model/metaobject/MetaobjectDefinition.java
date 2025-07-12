package com.shopify.sdk.model.metaobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Represents a metaobject definition.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectDefinition implements Node {
    /**
     * The unique identifier for the metaobject definition.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The type of the metaobject definition.
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
     * The display name configuration.
     */
    @JsonProperty("displayNameKey")
    private String displayNameKey;
    
    /**
     * Whether this has SEO enabled.
     */
    @JsonProperty("hasSeoEnabled")
    private Boolean hasSeoEnabled;
    
    /**
     * The metaobjects count.
     */
    @JsonProperty("metaobjectsCount")
    private Integer metaobjectsCount;
    
    /**
     * The field definitions.
     */
    @JsonProperty("fieldDefinitions")
    private List<MetaobjectFieldDefinition> fieldDefinitions;
    
    /**
     * The access settings for admin.
     */
    @JsonProperty("adminAccess")
    private MetaobjectAdminAccess adminAccess;
    
    /**
     * The access settings for storefront.
     */
    @JsonProperty("storefrontAccess")
    private MetaobjectStorefrontAccess storefrontAccess;
    
    /**
     * Created by information.
     */
    @JsonProperty("createdBy")
    private MetaobjectDefinitionCreatedBy createdBy;
    
    /**
     * When the definition was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * When the definition was updated.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
}