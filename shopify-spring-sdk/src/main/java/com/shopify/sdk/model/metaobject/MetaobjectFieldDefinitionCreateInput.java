package com.shopify.sdk.model.metaobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for creating a metaobject field definition.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectFieldDefinitionCreateInput {
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
     * The type of the field.
     */
    @JsonProperty("type")
    private String type;
    
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