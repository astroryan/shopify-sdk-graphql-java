package com.shopify.sdk.model.metaobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for updating a metaobject.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectUpdateInput {
    /**
     * The ID of the metaobject to update.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The handle of the metaobject.
     */
    @JsonProperty("handle")
    private String handle;
    
    /**
     * The fields to update.
     */
    @JsonProperty("fields")
    private List<MetaobjectFieldInput> fields;
    
    /**
     * SEO input.
     */
    @JsonProperty("seo")
    private String seo;
}