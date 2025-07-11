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
public class MetafieldDefinition {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("access")
    private MetafieldAccess access;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("metafields")
    private MetafieldConnection metafields;
    
    @JsonProperty("metafieldsCount")
    private Integer metafieldsCount;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("namespace")
    private String namespace;
    
    @JsonProperty("ownerType")
    private MetafieldOwnerType ownerType;
    
    @JsonProperty("pinnedPosition")
    private Integer pinnedPosition;
    
    @JsonProperty("standardTemplate")
    private StandardMetafieldDefinitionTemplate standardTemplate;
    
    @JsonProperty("type")
    private MetafieldDefinitionType type;
    
    @JsonProperty("useAsCollectionCondition")
    private Boolean useAsCollectionCondition;
    
    @JsonProperty("validationStatus")
    private MetafieldDefinitionValidationStatus validationStatus;
    
    @JsonProperty("validations")
    private List<MetafieldDefinitionValidation> validations;
    
    @JsonProperty("visibleToStorefrontApi")
    private Boolean visibleToStorefrontApi;
}
