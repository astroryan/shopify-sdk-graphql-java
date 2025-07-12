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
 * Represents a metaobject.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Metaobject implements Node {
    /**
     * The unique identifier for the metaobject.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The type of the metaobject.
     */
    @JsonProperty("type")
    private String type;
    
    /**
     * The handle of the metaobject.
     */
    @JsonProperty("handle")
    private String handle;
    
    /**
     * The display name of the metaobject.
     */
    @JsonProperty("displayName")
    private String displayName;
    
    /**
     * The fields of the metaobject.
     */
    @JsonProperty("fields")
    private String fields;
    
    /**
     * The SEO information.
     */
    @JsonProperty("seo")
    private String seo;
    
    /**
     * When the metaobject was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * When the metaobject was updated.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
    
    /**
     * The definition of this metaobject.
     */
    @JsonProperty("definition")
    private MetaobjectDefinition definition;
}