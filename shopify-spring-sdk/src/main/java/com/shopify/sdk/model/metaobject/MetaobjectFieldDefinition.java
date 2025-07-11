package com.shopify.sdk.model.metaobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectFieldDefinition {
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("required")
    private Boolean required;
    
    @JsonProperty("type")
    private MetaobjectFieldDefinitionType type;
    
    @JsonProperty("validations")
    private List<MetafieldDefinitionValidation> validations;
}
