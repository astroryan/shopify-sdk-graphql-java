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
public class MetaobjectDefinition {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("access")
    private MetaobjectAccess access;
    
    @JsonProperty("capabilities")
    private MetaobjectCapabilities capabilities;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("createdByApp")
    private App createdByApp;
    
    @JsonProperty("createdByStaff")
    private StaffMember createdByStaff;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("displayNameKey")
    private String displayNameKey;
    
    @JsonProperty("fieldDefinitions")
    private List<MetaobjectFieldDefinition> fieldDefinitions;
    
    @JsonProperty("fieldDefinitionsCount")
    private Integer fieldDefinitionsCount;
    
    @JsonProperty("hasThumbnailField")
    private Boolean hasThumbnailField;
    
    @JsonProperty("metaobjects")
    private MetaobjectConnection metaobjects;
    
    @JsonProperty("metaobjectsCount")
    private Integer metaobjectsCount;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("thumbnailFieldKey")
    private String thumbnailFieldKey;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectAccess {
    
    @JsonProperty("admin")
    private MetaobjectAdminAccess admin;
    
    @JsonProperty("storefront")
    private MetaobjectStorefrontAccess storefront;
}

public enum MetaobjectAdminAccess {
    NONE,
    READ,
    READ_WRITE
}

public enum MetaobjectStorefrontAccess {
    NONE,
    PUBLIC_READ
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectCapabilities {
    
    @JsonProperty("onlineStore")
    private MetaobjectCapabilityOnlineStore onlineStore;
    
    @JsonProperty("publishable")
    private MetaobjectCapabilityPublishable publishable;
    
    @JsonProperty("renderable")
    private MetaobjectCapabilityRenderable renderable;
    
    @JsonProperty("translatable")
    private MetaobjectCapabilityTranslatable translatable;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectCapabilityOnlineStore {
    
    @JsonProperty("data")
    private OnlineStorePageData data;
    
    @JsonProperty("enabled")
    private Boolean enabled;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineStorePageData {
    
    @JsonProperty("templateSuffix")
    private String templateSuffix;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectCapabilityPublishable {
    
    @JsonProperty("enabled")
    private Boolean enabled;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectCapabilityRenderable {
    
    @JsonProperty("data")
    private MetaobjectCapabilityRenderableData data;
    
    @JsonProperty("enabled")
    private Boolean enabled;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectCapabilityRenderableData {
    
    @JsonProperty("metaTitleKey")
    private String metaTitleKey;
    
    @JsonProperty("metaDescriptionKey")
    private String metaDescriptionKey;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectCapabilityTranslatable {
    
    @JsonProperty("enabled")
    private Boolean enabled;
}

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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectFieldDefinitionType {
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("category")
    private String category;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldDefinitionValidation {
    
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
public class Metaobject {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("capabilities")
    private MetaobjectCapabilities capabilities;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("createdBy")
    private MetaobjectDefinitionCreatedBy createdBy;
    
    @JsonProperty("createdByApp")
    private App createdByApp;
    
    @JsonProperty("createdByStaff")
    private StaffMember createdByStaff;
    
    @JsonProperty("definition")
    private MetaobjectDefinition definition;
    
    @JsonProperty("displayName")
    private String displayName;
    
    @JsonProperty("field")
    private MetaobjectField field;
    
    @JsonProperty("fields")
    private List<MetaobjectField> fields;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("onlineStoreUrl")
    private String onlineStoreUrl;
    
    @JsonProperty("seo")
    private MetaobjectSeo seo;
    
    @JsonProperty("staffMember")
    private StaffMember staffMember;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    @JsonProperty("updatedBy")
    private MetaobjectDefinitionCreatedBy updatedBy;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectDefinitionCreatedBy {
    
    @JsonProperty("__typename")
    private String typename;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectField {
    
    @JsonProperty("definition")
    private MetaobjectFieldDefinition definition;
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("reference")
    private MetafieldReference reference;
    
    @JsonProperty("references")
    private MetafieldReferenceConnection references;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("value")
    private String value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldReference {
    
    @JsonProperty("__typename")
    private String typename;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldReferenceConnection {
    
    @JsonProperty("edges")
    private List<MetafieldReferenceEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldReferenceEdge {
    
    @JsonProperty("node")
    private MetafieldReference node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectSeo {
    
    @JsonProperty("title")
    private MetaobjectSeoField title;
    
    @JsonProperty("description")
    private MetaobjectSeoField description;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectSeoField {
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("value")
    private String value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectConnection {
    
    @JsonProperty("edges")
    private List<MetaobjectEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectEdge {
    
    @JsonProperty("node")
    private Metaobject node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class App {
    
    @JsonProperty("id")
    private ID id;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffMember {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("displayName")
    private String displayName;
}

// Input classes
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectDefinitionCreateInput {
    
    @JsonProperty("access")
    private MetaobjectAccessInput access;
    
    @JsonProperty("capabilities")
    private MetaobjectCapabilitiesInput capabilities;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("displayNameKey")
    private String displayNameKey;
    
    @JsonProperty("fieldDefinitions")
    private List<MetaobjectFieldDefinitionCreateInput> fieldDefinitions;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("type")
    private String type;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectAccessInput {
    
    @JsonProperty("admin")
    private MetaobjectAdminAccess admin;
    
    @JsonProperty("storefront")
    private MetaobjectStorefrontAccess storefront;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectCapabilitiesInput {
    
    @JsonProperty("onlineStore")
    private MetaobjectCapabilityOnlineStoreInput onlineStore;
    
    @JsonProperty("publishable")
    private MetaobjectCapabilityPublishableInput publishable;
    
    @JsonProperty("renderable")
    private MetaobjectCapabilityRenderableInput renderable;
    
    @JsonProperty("translatable")
    private MetaobjectCapabilityTranslatableInput translatable;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectCapabilityOnlineStoreInput {
    
    @JsonProperty("data")
    private OnlineStorePageDataInput data;
    
    @JsonProperty("enabled")
    private Boolean enabled;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineStorePageDataInput {
    
    @JsonProperty("templateSuffix")
    private String templateSuffix;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectCapabilityPublishableInput {
    
    @JsonProperty("enabled")
    private Boolean enabled;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectCapabilityRenderableInput {
    
    @JsonProperty("data")
    private MetaobjectCapabilityRenderableDataInput data;
    
    @JsonProperty("enabled")
    private Boolean enabled;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectCapabilityRenderableDataInput {
    
    @JsonProperty("metaTitleKey")
    private String metaTitleKey;
    
    @JsonProperty("metaDescriptionKey")
    private String metaDescriptionKey;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectCapabilityTranslatableInput {
    
    @JsonProperty("enabled")
    private Boolean enabled;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectFieldDefinitionCreateInput {
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("required")
    private Boolean required;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("validations")
    private List<MetafieldDefinitionValidationInput> validations;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldDefinitionValidationInput {
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("value")
    private String value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectDefinitionUpdateInput {
    
    @JsonProperty("access")
    private MetaobjectAccessInput access;
    
    @JsonProperty("capabilities")
    private MetaobjectCapabilitiesInput capabilities;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("displayNameKey")
    private String displayNameKey;
    
    @JsonProperty("fieldDefinitions")
    private List<MetaobjectFieldDefinitionUpdateInput> fieldDefinitions;
    
    @JsonProperty("name")
    private String name;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectFieldDefinitionUpdateInput {
    
    @JsonProperty("create")
    private MetaobjectFieldDefinitionCreateInput create;
    
    @JsonProperty("delete")
    private MetaobjectFieldDefinitionDeleteInput delete;
    
    @JsonProperty("update")
    private MetaobjectFieldDefinitionOperationInput update;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectFieldDefinitionDeleteInput {
    
    @JsonProperty("key")
    private String key;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectFieldDefinitionOperationInput {
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("required")
    private Boolean required;
    
    @JsonProperty("validations")
    private List<MetafieldDefinitionValidationInput> validations;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectCreateInput {
    
    @JsonProperty("capabilities")
    private MetaobjectCapabilityDataInput capabilities;
    
    @JsonProperty("fields")
    private List<MetaobjectFieldInput> fields;
    
    @JsonProperty("handle")
    private String handle;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectCapabilityDataInput {
    
    @JsonProperty("onlineStore")
    private MetaobjectCapabilityOnlineStoreInput onlineStore;
    
    @JsonProperty("publishable")
    private MetaobjectCapabilityPublishableInput publishable;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectFieldInput {
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("value")
    private String value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectUpdateInput {
    
    @JsonProperty("capabilities")
    private MetaobjectCapabilityUpdateInput capabilities;
    
    @JsonProperty("fields")
    private List<MetaobjectFieldInput> fields;
    
    @JsonProperty("handle")
    private String handle;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectCapabilityUpdateInput {
    
    @JsonProperty("onlineStore")
    private MetaobjectCapabilityOnlineStoreInput onlineStore;
    
    @JsonProperty("publishable")
    private MetaobjectCapabilityPublishableInput publishable;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectUpsertInput {
    
    @JsonProperty("capabilities")
    private MetaobjectCapabilityDataInput capabilities;
    
    @JsonProperty("fields")
    private List<MetaobjectFieldInput> fields;
    
    @JsonProperty("handle")
    private String handle;
}

// Error handling
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectUserError {
    
    @JsonProperty("code")
    private MetaobjectUserErrorCode code;
    
    @JsonProperty("elementIndex")
    private Integer elementIndex;
    
    @JsonProperty("elementKey")
    private String elementKey;
    
    @JsonProperty("field")
    private List<String> field;
    
    @JsonProperty("message")
    private String message;
}

public enum MetaobjectUserErrorCode {
    INVALID,
    TAKEN,
    TOO_MANY_ARGUMENTS,
    INVALID_TYPE,
    APP_NOT_AUTHORIZED,
    INVALID_CAPABILITY_SETUP,
    INVALID_FIELD_CONFIGURATION,
    RESERVED_KEYWORDS_USED,
    MAX_DEFINITIONS_EXCEEDED,
    INVALID_INPUT,
    INCLUSION,
    REQUIRED,
    TOO_LONG,
    BLANK,
    OPERATION_IN_PROGRESS,
    FORBIDDEN,
    CANNOT_CHANGE_FIELD_TYPE
}