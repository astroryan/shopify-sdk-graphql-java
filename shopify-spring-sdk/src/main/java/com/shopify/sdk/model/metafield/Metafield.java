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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class HasMetafields {
    
    @JsonProperty("__typename")
    private String typename;
    
    @JsonProperty("id")
    private ID id;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MetafieldReference {
    
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
class MetafieldReferenceEdge {
    
    @JsonProperty("node")
    private MetafieldReference node;
    
    @JsonProperty("cursor")
    private String cursor;
}

public enum MetafieldOwnerType {
    ARTICLE,
    BLOG,
    COLLECTION,
    COMPANY,
    COMPANY_LOCATION,
    CUSTOMER,
    DELIVERY_CUSTOMIZATION,
    DISCOUNT_NODE,
    DRAFTORDER,
    GENERIC_FILE,
    LOCATION,
    MARKET,
    MEDIA_IMAGE,
    ORDER,
    PAGE,
    PAYMENT_CUSTOMIZATION,
    PRODUCT,
    PRODUCTIMAGE,
    PRODUCTVARIANT,
    SHOP,
    VALIDATION
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldConnection {
    
    @JsonProperty("edges")
    private List<MetafieldEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MetafieldEdge {
    
    @JsonProperty("node")
    private Metafield node;
    
    @JsonProperty("cursor")
    private String cursor;
}