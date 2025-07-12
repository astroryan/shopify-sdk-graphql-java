package com.shopify.sdk.model.metaobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for a metaobject field.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectFieldInput {
    /**
     * The key of the field.
     */
    @JsonProperty("key")
    private String key;
    
    /**
     * The value of the field.
     */
    @JsonProperty("value")
    private String value;
}