package com.shopify.sdk.model.metaobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for updating a metaobject field definition.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectFieldDefinitionUpdateInput {
    /**
     * The key of the field.
     */
    @JsonProperty("key")
    private String key;
    
    /**
     * The name of the field.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The description of the field.
     */
    @JsonProperty("description")
    private String description;
    
    /**
     * Whether the field is required.
     */
    @JsonProperty("required")
    private Boolean required;
    
    /**
     * Validations for the field.
     */
    @JsonProperty("validations")
    private List<MetafieldDefinitionValidation> validations;
}