package com.shopify.sdk.service.access;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.GraphQLClient;
import com.shopify.sdk.model.access.AccessScope;
import com.shopify.sdk.model.access.AccessToken;
import com.shopify.sdk.model.common.DateTime;
import com.shopify.sdk.model.graphql.GraphQLRequest;
import com.shopify.sdk.model.graphql.GraphQLResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessTokenService {
    
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
                    published
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
                }
            }
        }
        """;
    
    private static final String ACCESS_TOKEN_QUERY = """
        query accessToken($id: ID!) {
            node(id: $id) {
                ... on AccessToken {
                    id
                    title
                    createdAt
                    updatedAt
                    scopes {
                        handle
                        description
                    }
                }
            }
        }
        """;
    
    public AppInstallationResponse getCurrentAppInstallation(ShopifyAuthContext context) {
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CURRENT_APP_INSTALLATION_QUERY)
                .build();
        
        GraphQLResponse<AppInstallationData> response = graphQLClient.execute(
                request, 
                AppInstallationData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to get current app installation: {}", response.getErrors());
            throw new RuntimeException("Failed to get current app installation");
        }
        
        return response.getData().getCurrentAppInstallation();
    }
    
    public AccessToken getAccessToken(ShopifyAuthContext context, String tokenId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", tokenId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(ACCESS_TOKEN_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<NodeResponse> response = graphQLClient.execute(
                request,
                NodeResponse.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to get access token: {}", response.getErrors());
            throw new RuntimeException("Failed to get access token");
        }
        
        return response.getData().getNode();
    }
    
    @Data
    private static class AppInstallationData {
        private AppInstallationResponse currentAppInstallation;
    }
    
    @Data
    public static class AppInstallationResponse {
        private String id;
        private App app;
        private List<AccessScope> accessScopes;
        private List<Subscription> activeSubscriptions;
    }
    
    @Data
    public static class App {
        private String id;
        private String title;
        private String description;
        private String developerName;
        private String developerType;
        private String privacyPolicyUrl;
        private boolean published;
    }
    
    @Data
    public static class Subscription {
        private String id;
        private String name;
        private String status;
        private DateTime createdAt;
        private DateTime currentPeriodEnd;
    }
    
    @Data
    private static class NodeResponse {
        private AccessToken node;
    }
}