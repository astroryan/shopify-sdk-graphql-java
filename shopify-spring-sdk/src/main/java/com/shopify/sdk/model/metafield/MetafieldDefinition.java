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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MetafieldAccess {
    
    @JsonProperty("admin")
    private MetafieldAdminAccess admin;
    
    @JsonProperty("storefront")
    private MetafieldStorefrontAccess storefront;
}

public enum MetafieldAdminAccess {
    MERCHANT_READ,
    MERCHANT_READ_WRITE,
    PRIVATE
}

public enum MetafieldStorefrontAccess {
    NONE,
    PUBLIC_READ
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class StandardMetafieldDefinitionTemplate {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("namespace")
    private String namespace;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("ownerTypes")
    private List<MetafieldOwnerType> ownerTypes;
    
    @JsonProperty("type")
    private MetafieldDefinitionType type;
    
    @JsonProperty("validations")
    private List<MetafieldDefinitionValidation> validations;
    
    @JsonProperty("visibleToStorefrontApi")
    private Boolean visibleToStorefrontApi;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MetafieldDefinitionType {
    
    @JsonProperty("category")
    private String category;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("supportedValidations")
    private List<MetafieldDefinitionSupportedValidation> supportedValidations;
    
    @JsonProperty("supportsDefinitionMigrations")
    private Boolean supportsDefinitionMigrations;
}

public enum MetafieldDefinitionValidationStatus {
    ALL_VALID,
    IN_PROGRESS,
    SOME_INVALID
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MetafieldDefinitionValidation {
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("value")
    private String value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MetafieldDefinitionSupportedValidation {
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("type")
    private String type;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldDefinitionConnection {
    
    @JsonProperty("edges")
    private List<MetafieldDefinitionEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MetafieldDefinitionEdge {
    
    @JsonProperty("node")
    private MetafieldDefinition node;
    
    @JsonProperty("cursor")
    private String cursor;
}