package com.shopify.sdk.model.metaobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for creating a metaobject.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectCreateInput {
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
     * The fields for the metaobject.
     */
    @JsonProperty("fields")
    private List<MetaobjectFieldInput> fields;
    
    /**
     * SEO input.
     */
    @JsonProperty("seo")
    private String seo;
}