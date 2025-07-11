package com.shopify.sdk.service.app;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.GraphQLClient;
import com.shopify.sdk.model.app.App;
import com.shopify.sdk.model.app.AppInstallation;
import com.shopify.sdk.model.graphql.GraphQLRequest;
import com.shopify.sdk.model.graphql.GraphQLResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppService {
    
    private final GraphQLClient graphQLClient;
    
    private static final String CURRENT_APP_INSTALLATION_QUERY = """
        query currentAppInstallation {
            currentAppInstallation {
                id
                app {
                    id
                    title
                    description
                    developerName
                    developerType
                    privacyPolicyUrl
                    apiKey
                    appStoreAppUrl
                    appStoreDeveloperUrl
                    embedded
                    features
                    handle
                    published
                    shopifyDeveloped
                    supportEmail
                    supportUrl
                    webhookApiVersion
                }
                accessScopes {
                    handle
                    description
                }
                activeSubscriptions {
                    id
                    name
                    status
                    createdAt
                    currentPeriodEnd
                    test
                    trialDays
                }
                createdAt
                launchUrl
                uninstallUrl
            }
        }
        """;
    
    private static final String APP_BY_HANDLE_QUERY = """
        query appByHandle($handle: String!) {
            appByHandle(handle: $handle) {
                id
                title
                description
                developerName
                developerType
                privacyPolicyUrl
                appStoreAppUrl
                appStoreDeveloperUrl
                banner {
                    id
                    altText
                    url
                }
                icon {
                    id
                    altText
                    url
                    width
                    height
                }
                embedded
                features
                handle
                installUrl
                pricingDetails
                pricingDetailsSummary
                published
                shopifyDeveloped
                supportEmail
                supportUrl
                webhookApiVersion
            }
        }
        """;
    
    private static final String APP_BY_KEY_QUERY = """
        query appByKey($apiKey: String!) {
            appByKey(apiKey: $apiKey) {
                id
                title
                description
                developerName
                developerType
                privacyPolicyUrl
                appStoreAppUrl
                appStoreDeveloperUrl
                embedded
                features
                handle
                published
                shopifyDeveloped
                supportEmail
                supportUrl
                webhookApiVersion
            }
        }
        """;
    
    public AppInstallation getCurrentAppInstallation(ShopifyAuthContext context) {
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CURRENT_APP_INSTALLATION_QUERY)
                .build();
        
        GraphQLResponse<CurrentAppInstallationData> response = graphQLClient.execute(
                request,
                CurrentAppInstallationData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to get current app installation: {}", response.getErrors());
            throw new RuntimeException("Failed to get current app installation");
        }
        
        return response.getData().getCurrentAppInstallation();
    }
    
    public App getAppByHandle(ShopifyAuthContext context, String handle) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("handle", handle);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(APP_BY_HANDLE_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<AppByHandleData> response = graphQLClient.execute(
                request,
                AppByHandleData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to get app by handle: {}", response.getErrors());
            throw new RuntimeException("Failed to get app by handle");
        }
        
        return response.getData().getAppByHandle();
    }
    
    public App getAppByKey(ShopifyAuthContext context, String apiKey) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("apiKey", apiKey);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(APP_BY_KEY_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<AppByKeyData> response = graphQLClient.execute(
                request,
                AppByKeyData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to get app by key: {}", response.getErrors());
            throw new RuntimeException("Failed to get app by key");
        }
        
        return response.getData().getAppByKey();
    }
    
    @Data
    private static class CurrentAppInstallationData {
        private AppInstallation currentAppInstallation;
    }
    
    @Data
    private static class AppByHandleData {
        private App appByHandle;
    }
    
    @Data
    private static class AppByKeyData {
        private App appByKey;
    }
}