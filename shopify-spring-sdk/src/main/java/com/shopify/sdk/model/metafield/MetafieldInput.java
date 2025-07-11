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
public class MetafieldsSetInput {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("namespace")
    private String namespace;
    
    @JsonProperty("ownerId")
    private String ownerId;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("value")
    private String value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldDeleteInput {
    
    @JsonProperty("id")
    private String id;
}

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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldDefinitionUpdateInput {
    
    @JsonProperty("access")
    private MetafieldAccessInput access;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("id")
    private String id;
    
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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MetafieldAccessInput {
    
    @JsonProperty("admin")
    private MetafieldAdminAccess admin;
    
    @JsonProperty("storefront")
    private List<MetafieldGrantAccessInput> storefront;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MetafieldGrantAccessInput {
    
    @JsonProperty("access")
    private String access;
    
    @JsonProperty("grantee")
    private String grantee;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MetafieldDefinitionValidationInput {
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("value")
    private String value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldInput {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("value")
    private String value;
}
