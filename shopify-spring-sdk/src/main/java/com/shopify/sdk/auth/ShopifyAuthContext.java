package com.shopify.sdk.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShopifyAuthContext {
    private final String shopDomain;
    private final String accessToken;
    private final String apiVersion;
    
    public String getGraphQLEndpoint() {
        return String.format("https://%s/admin/api/%s/graphql.json", shopDomain, apiVersion);
    }
    
    public String getRestEndpoint(String resource) {
        return String.format("https://%s/admin/api/%s/%s", shopDomain, apiVersion, resource);
    }
}