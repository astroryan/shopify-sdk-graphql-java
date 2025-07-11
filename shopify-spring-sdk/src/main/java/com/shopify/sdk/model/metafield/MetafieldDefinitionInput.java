package com.shopify.sdk.model.metafield;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldDefinitionInput {
    
    @JsonProperty("access")
    private MetafieldAccessInput access;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("namespace")
    private String namespace;
    
    @JsonProperty("ownerType")
    private MetafieldOwnerType ownerType;
    
    @JsonProperty("pin")
    private Boolean pin;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("useAsCollectionCondition")
    private Boolean useAsCollectionCondition;
    
    @JsonProperty("validations")
    private List<MetafieldDefinitionValidationInput> validations;
}