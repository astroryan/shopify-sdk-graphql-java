package com.shopify.sdk.model.metafield;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Metafield {
    
    private String id;
    private String namespace;
    private String key;
    private String value;
    private String description;
    
    @JsonProperty("owner_id")
    private String ownerId;
    
    @JsonProperty("owner_resource")
    private String ownerResource;
    
    @JsonProperty("created_at")
    private Instant createdAt;
    
    @JsonProperty("updated_at")
    private Instant updatedAt;
    
    @JsonProperty("type")
    private MetafieldType type;
    
    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;
    
    public String getNamespaceKey() {
        return namespace + "." + key;
    }
    
    public boolean isProductMetafield() {
        return "product".equals(ownerResource);
    }
    
    public boolean isVariantMetafield() {
        return "variant".equals(ownerResource);
    }
    
    public boolean isOrderMetafield() {
        return "order".equals(ownerResource);
    }
    
    public boolean isCustomerMetafield() {
        return "customer".equals(ownerResource);
    }
    
    public boolean isShopMetafield() {
        return "shop".equals(ownerResource);
    }
    
    public boolean isCollectionMetafield() {
        return "collection".equals(ownerResource);
    }
    
    public boolean isPageMetafield() {
        return "page".equals(ownerResource);
    }
    
    public boolean isBlogMetafield() {
        return "blog".equals(ownerResource);
    }
    
    public boolean isArticleMetafield() {
        return "article".equals(ownerResource);
    }
    
    public boolean hasValue() {
        return value != null && !value.trim().isEmpty();
    }
}