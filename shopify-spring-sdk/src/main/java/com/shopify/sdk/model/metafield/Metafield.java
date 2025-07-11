package com.shopify.sdk.model.metafield;

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
public class Metafield {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("definition")
    private MetafieldDefinition definition;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("legacyResourceId")
    private String legacyResourceId;
    
    @JsonProperty("namespace")
    private String namespace;
    
    @JsonProperty("owner")
    private HasMetafields owner;
    
    @JsonProperty("ownerType")
    private MetafieldOwnerType ownerType;
    
    @JsonProperty("reference")
    private MetafieldReference reference;
    
    @JsonProperty("references")
    private MetafieldReferenceConnection references;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    @JsonProperty("value")
    private String value;
}
