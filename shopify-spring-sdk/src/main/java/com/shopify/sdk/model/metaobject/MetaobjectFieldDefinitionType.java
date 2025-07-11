package com.shopify.sdk.model.metaobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the type definition for a metaobject field.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectFieldDefinitionType {
    
    /**
     * The name of the type.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The category of the type.
     */
    @JsonProperty("category")
    private String category;
    
    /**
     * Supported validations for this type.
     */
    @JsonProperty("supportedValidations")
    private Object supportedValidations;
    
    /**
     * Whether fields of this type can store lists of values.
     */
    @JsonProperty("supportsArray")
    private Boolean supportsArray;
}