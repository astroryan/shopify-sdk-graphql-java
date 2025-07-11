package com.shopify.sdk.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.shopify.sdk.model.graphql.GraphQLRequest;
import com.shopify.sdk.model.graphql.GraphQLResponse;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.config.ShopifyAuthContext;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.common.PageInfo;
import com.shopify.sdk.model.common.UserError;
import com.shopify.sdk.model.webhook.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebhookService {
    
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private final ShopifyGraphQLClient graphQLClient;
    
    // Query for webhook subscriptions
    private static final String GET_WEBHOOK_SUBSCRIPTIONS_QUERY = """
        query webhookSubscriptions($first: Int!, $after: String, $topics: [WebhookSubscriptionTopic!]) {
            webhookSubscriptions(first: $first, after: $after, topics: $topics) {
                edges {
                    node {
                        id
                        apiVersion
                        callbackUrl
                        createdAt
                        endpoint {
                            __typename
                            ... on WebhookHttpEndpoint {
                                callbackUrl
                            }
                            ... on WebhookEventBridgeEndpoint {
                                arn
                            }
                            ... on WebhookPubSubEndpoint {
                                pubSubProject
                                pubSubTopic
                            }
                        }
                        format
                        includeFields
                        legacyResourceId
                        metafieldNamespaces
                        privateMetafieldNamespaces
                        topic
                        updatedAt
                    }
                    cursor
                }
                pageInfo {
                    hasNextPage
                    endCursor
                }
            }
        }
        """;
    
    // Query for single webhook subscription
    private static final String GET_WEBHOOK_SUBSCRIPTION_QUERY = """
        query node($id: ID!) {
            node(id: $id) {
                ... on WebhookSubscription {
                    id
                    apiVersion
                    callbackUrl
                    createdAt
                    endpoint {
                        __typename
                        ... on WebhookHttpEndpoint {
                            callbackUrl
                        }
                        ... on WebhookEventBridgeEndpoint {
                            arn
                        }
                        ... on WebhookPubSubEndpoint {
                            pubSubProject
                            pubSubTopic
                        }
                    }
                    format
                    includeFields
                    legacyResourceId
                    metafieldNamespaces
                    privateMetafieldNamespaces
                    topic
                    updatedAt
                }
            }
        }
        """;
    
    // Query for API versions
    private static final String GET_API_VERSIONS_QUERY = """
        query apiVersions {
            publicApiVersions {
                displayName
                handle
                supported
            }
        }
        """;
    
    // Mutation to create HTTP webhook subscription
    private static final String CREATE_WEBHOOK_SUBSCRIPTION_MUTATION = """
        mutation webhookSubscriptionCreate($topic: WebhookSubscriptionTopic!, $webhookSubscription: WebhookSubscriptionInput!) {
            webhookSubscriptionCreate(
                topic: $topic,
                webhookSubscription: $webhookSubscription
            ) {
                webhookSubscription {
                    id
                    topic
                    format
                    endpoint {
                        __typename
                        ... on WebhookHttpEndpoint {
                            callbackUrl
                        }
                    }
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to create EventBridge webhook subscription
    private static final String CREATE_EVENTBRIDGE_WEBHOOK_SUBSCRIPTION_MUTATION = """
        mutation eventBridgeWebhookSubscriptionCreate($topic: WebhookSubscriptionTopic!, $webhookSubscription: EventBridgeWebhookSubscriptionCreateInput!) {
            eventBridgeWebhookSubscriptionCreate(
                topic: $topic,
                webhookSubscription: $webhookSubscription
            ) {
                webhookSubscription {
                    id
                    topic
                    format
                    endpoint {
                        __typename
                        ... on WebhookEventBridgeEndpoint {
                            arn
                        }
                    }
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to create PubSub webhook subscription
    private static final String CREATE_PUBSUB_WEBHOOK_SUBSCRIPTION_MUTATION = """
        mutation pubSubWebhookSubscriptionCreate($topic: WebhookSubscriptionTopic!, $webhookSubscription: PubSubWebhookSubscriptionCreateInput!) {
            pubSubWebhookSubscriptionCreate(
                topic: $topic,
                webhookSubscription: $webhookSubscription
            ) {
                webhookSubscription {
                    id
                    topic
                    format
                    endpoint {
                        __typename
                        ... on WebhookPubSubEndpoint {
                            pubSubProject
                            pubSubTopic
                        }
                    }
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to update webhook subscription
    private static final String UPDATE_WEBHOOK_SUBSCRIPTION_MUTATION = """
        mutation webhookSubscriptionUpdate($id: ID!, $webhookSubscription: WebhookSubscriptionInput!) {
            webhookSubscriptionUpdate(
                id: $id,
                webhookSubscription: $webhookSubscription
            ) {
                webhookSubscription {
                    id
                    topic
                    format
                    callbackUrl
                    includeFields
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to delete webhook subscription
    private static final String DELETE_WEBHOOK_SUBSCRIPTION_MUTATION = """
        mutation webhookSubscriptionDelete($id: ID!) {
            webhookSubscriptionDelete(id: $id) {
                deletedWebhookSubscriptionId
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Service methods
    public List<WebhookSubscription> getWebhookSubscriptions(
            ShopifyAuthContext context,
            int first,
            String after,
            List<WebhookSubscriptionTopic> topics) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (topics != null && !topics.isEmpty()) {
            variables.put("topics", topics);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_WEBHOOK_SUBSCRIPTIONS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<WebhookSubscriptionsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<WebhookSubscriptionsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get webhook subscriptions", response.getErrors());
        }
        
        List<WebhookSubscription> subscriptions = new ArrayList<>();
        response.getData().webhookSubscriptions.edges.forEach(edge -> 
            subscriptions.add(edge.node)
        );
        
        return subscriptions;
    }
    
    public WebhookSubscription getWebhookSubscription(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_WEBHOOK_SUBSCRIPTION_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<NodeResponse<WebhookSubscription>> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<NodeResponse<WebhookSubscription>>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get webhook subscription", response.getErrors());
        }
        
        return response.getData().node;
    }
    
    public List<ApiVersion> getApiVersions(ShopifyAuthContext context) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_API_VERSIONS_QUERY)
                .variables(new HashMap<>())
                .build();
        
        GraphQLResponse<ApiVersionsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ApiVersionsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get API versions", response.getErrors());
        }
        
        return response.getData().publicApiVersions;
    }
    
    public WebhookSubscription createWebhookSubscription(
            ShopifyAuthContext context,
            WebhookSubscriptionTopic topic,
            WebhookSubscriptionInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("topic", topic);
        variables.put("webhookSubscription", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_WEBHOOK_SUBSCRIPTION_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<WebhookSubscriptionCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<WebhookSubscriptionCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create webhook subscription", response.getErrors());
        }
        
        if (response.getData().webhookSubscriptionCreate.userErrors != null && 
            !response.getData().webhookSubscriptionCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create webhook subscription",
                    response.getData().webhookSubscriptionCreate.userErrors
            );
        }
        
        return response.getData().webhookSubscriptionCreate.webhookSubscription;
    }
    
    public WebhookSubscription createEventBridgeWebhookSubscription(
            ShopifyAuthContext context,
            WebhookSubscriptionTopic topic,
            EventBridgeWebhookSubscriptionCreateInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("topic", topic);
        variables.put("webhookSubscription", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_EVENTBRIDGE_WEBHOOK_SUBSCRIPTION_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<EventBridgeWebhookSubscriptionCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<EventBridgeWebhookSubscriptionCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create EventBridge webhook subscription", response.getErrors());
        }
        
        if (response.getData().eventBridgeWebhookSubscriptionCreate.userErrors != null && 
            !response.getData().eventBridgeWebhookSubscriptionCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create EventBridge webhook subscription",
                    response.getData().eventBridgeWebhookSubscriptionCreate.userErrors
            );
        }
        
        return response.getData().eventBridgeWebhookSubscriptionCreate.webhookSubscription;
    }
    
    public WebhookSubscription createPubSubWebhookSubscription(
            ShopifyAuthContext context,
            WebhookSubscriptionTopic topic,
            PubSubWebhookSubscriptionCreateInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("topic", topic);
        variables.put("webhookSubscription", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_PUBSUB_WEBHOOK_SUBSCRIPTION_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<PubSubWebhookSubscriptionCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<PubSubWebhookSubscriptionCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create PubSub webhook subscription", response.getErrors());
        }
        
        if (response.getData().pubSubWebhookSubscriptionCreate.userErrors != null && 
            !response.getData().pubSubWebhookSubscriptionCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create PubSub webhook subscription",
                    response.getData().pubSubWebhookSubscriptionCreate.userErrors
            );
        }
        
        return response.getData().pubSubWebhookSubscriptionCreate.webhookSubscription;
    }
    
    public WebhookSubscription updateWebhookSubscription(
            ShopifyAuthContext context,
            String id,
            WebhookSubscriptionInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        variables.put("webhookSubscription", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_WEBHOOK_SUBSCRIPTION_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<WebhookSubscriptionUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<WebhookSubscriptionUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update webhook subscription", response.getErrors());
        }
        
        if (response.getData().webhookSubscriptionUpdate.userErrors != null && 
            !response.getData().webhookSubscriptionUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update webhook subscription",
                    response.getData().webhookSubscriptionUpdate.userErrors
            );
        }
        
        return response.getData().webhookSubscriptionUpdate.webhookSubscription;
    }
    
    public String deleteWebhookSubscription(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_WEBHOOK_SUBSCRIPTION_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<WebhookSubscriptionDeleteResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<WebhookSubscriptionDeleteResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to delete webhook subscription", response.getErrors());
        }
        
        if (response.getData().webhookSubscriptionDelete.userErrors != null && 
            !response.getData().webhookSubscriptionDelete.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to delete webhook subscription",
                    response.getData().webhookSubscriptionDelete.userErrors
            );
        }
        
        return response.getData().webhookSubscriptionDelete.deletedWebhookSubscriptionId;
    }
    
    // Webhook verification
    public boolean verifyWebhookHmac(String hmacHeader, String data, String secret) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(
                    secret.getBytes(StandardCharsets.UTF_8), 
                    HMAC_ALGORITHM
            );
            mac.init(secretKeySpec);
            
            byte[] hmacBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            String calculatedHmac = Base64.getEncoder().encodeToString(hmacBytes);
            
            return calculatedHmac.equals(hmacHeader);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("Error verifying webhook HMAC", e);
            return false;
        }
    }
    
    // Response classes
    @Data
    private static class WebhookSubscriptionsResponse {
        private WebhookSubscriptionConnection webhookSubscriptions;
    }
    
    @Data
    private static class NodeResponse<T> {
        private T node;
    }
    
    @Data
    private static class ApiVersionsResponse {
        private List<ApiVersion> publicApiVersions;
    }
    
    @Data
    private static class WebhookSubscriptionCreateResponse {
        private WebhookSubscriptionCreateResult webhookSubscriptionCreate;
    }
    
    @Data
    private static class WebhookSubscriptionCreateResult {
        private WebhookSubscription webhookSubscription;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class EventBridgeWebhookSubscriptionCreateResponse {
        private EventBridgeWebhookSubscriptionCreateResult eventBridgeWebhookSubscriptionCreate;
    }
    
    @Data
    private static class EventBridgeWebhookSubscriptionCreateResult {
        private WebhookSubscription webhookSubscription;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class PubSubWebhookSubscriptionCreateResponse {
        private PubSubWebhookSubscriptionCreateResult pubSubWebhookSubscriptionCreate;
    }
    
    @Data
    private static class PubSubWebhookSubscriptionCreateResult {
        private WebhookSubscription webhookSubscription;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class WebhookSubscriptionUpdateResponse {
        private WebhookSubscriptionUpdateResult webhookSubscriptionUpdate;
    }
    
    @Data
    private static class WebhookSubscriptionUpdateResult {
        private WebhookSubscription webhookSubscription;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class WebhookSubscriptionDeleteResponse {
        private WebhookSubscriptionDeleteResult webhookSubscriptionDelete;
    }
    
    @Data
    private static class WebhookSubscriptionDeleteResult {
        private String deletedWebhookSubscriptionId;
        private List<UserError> userErrors;
    }
}