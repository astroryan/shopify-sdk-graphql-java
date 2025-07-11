package com.shopify.sdk.service.marketing;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.GraphQLClient;
import com.shopify.sdk.model.marketing.*;
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
public class MarketingService {
    
    private final GraphQLClient graphQLClient;
    
    private static final String GET_MARKETING_ACTIVITY_QUERY = """
        query getMarketingActivity($id: ID!) {
            marketingActivity(id: $id) {
                id
                activityTitle
                adSpend {
                    amount
                    currencyCode
                }
                app {
                    id
                    title
                }
                budget {
                    budgetType
                    total {
                        amount
                        currencyCode
                    }
                }
                createdAt
                engagementSummary {
                    adSpend {
                        amount
                        currencyCode
                    }
                    clicksCount
                    commentsCount
                    complaintsCount
                    failsCount
                    favoritesCount
                    fetchedAt
                    impressionsCount
                    isCumulative
                    sendsCount
                    sharesCount
                    uniqueClicksCount
                    uniqueViewsCount
                    unsubscribesCount
                    viewsCount
                }
                errors
                formData
                hierarchyLevel
                inMainWorkflow
                marketingChannel
                marketingChannelType
                marketingEvent {
                    id
                    app {
                        id
                        title
                    }
                    channel
                    description
                    endedAt
                    remoteId
                    startedAt
                    type
                    utmCampaign
                    utmMedium
                    utmSource
                }
                parentActivityId
                parentRemoteId
                sourceAndMedium
                status
                statusBadgeType
                statusBadgeTypeV2
                statusLabel
                statusTransitionedAt
                tactic
                targetStatus
                title
                updatedAt
                urlParameterValue
                utmParameters {
                    campaign
                    content
                    medium
                    source
                    term
                }
            }
        }
        """;
    
    private static final String LIST_MARKETING_ACTIVITIES_QUERY = """
        query listMarketingActivities($first: Int!, $after: String, $query: String, $sortKey: MarketingActivitySortKeys, $reverse: Boolean) {
            marketingActivities(first: $first, after: $after, query: $query, sortKey: $sortKey, reverse: $reverse) {
                edges {
                    node {
                        id
                        activityTitle
                        adSpend {
                            amount
                            currencyCode
                        }
                        app {
                            id
                            title
                        }
                        createdAt
                        hierarchyLevel
                        marketingChannel
                        marketingChannelType
                        status
                        statusBadgeType
                        statusLabel
                        tactic
                        title
                        updatedAt
                    }
                    cursor
                }
                pageInfo {
                    hasNextPage
                    hasPreviousPage
                    startCursor
                    endCursor
                }
            }
        }
        """;
    
    private static final String CREATE_MARKETING_ACTIVITY_MUTATION = """
        mutation marketingActivityCreate($input: MarketingActivityCreateInput!) {
            marketingActivityCreate(input: $input) {
                marketingActivity {
                    id
                    activityTitle
                    status
                    createdAt
                    marketingChannel
                    marketingChannelType
                    tactic
                }
                redirectPath
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    private static final String UPDATE_MARKETING_ACTIVITY_MUTATION = """
        mutation marketingActivityUpdate($input: MarketingActivityUpdateInput!) {
            marketingActivityUpdate(input: $input) {
                marketingActivity {
                    id
                    activityTitle
                    status
                    updatedAt
                }
                redirectPath
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    private static final String LIST_MARKETING_EVENTS_QUERY = """
        query listMarketingEvents($first: Int!, $after: String, $query: String, $sortKey: MarketingEventSortKeys, $reverse: Boolean) {
            marketingEvents(first: $first, after: $after, query: $query, sortKey: $sortKey, reverse: $reverse) {
                edges {
                    node {
                        id
                        app {
                            id
                            title
                        }
                        channel
                        description
                        endedAt
                        legacyResourceId
                        manageUrl
                        previewUrl
                        remoteId
                        scheduledToEndAt
                        sourceAndMedium
                        startedAt
                        targetTypeDisplayText
                        type
                        utmCampaign
                        utmMedium
                        utmSource
                    }
                    cursor
                }
                pageInfo {
                    hasNextPage
                    hasPreviousPage
                    startCursor
                    endCursor
                }
            }
        }
        """;
    
    private static final String GET_MARKETING_ENGAGEMENT_MUTATION = """
        mutation marketingEngagementCreate($marketingActivityId: ID!, $marketingEngagement: MarketingEngagementInput!) {
            marketingEngagementCreate(
                marketingActivityId: $marketingActivityId,
                marketingEngagement: $marketingEngagement
            ) {
                marketingEngagement {
                    adSpend {
                        amount
                        currencyCode
                    }
                    clicksCount
                    commentsCount
                    complaintsCount
                    failsCount
                    favoritesCount
                    fetchedAt
                    impressionsCount
                    isCumulative
                    occurredOn
                    sendsCount
                    sharesCount
                    uniqueClicksCount
                    uniqueViewsCount
                    unsubscribesCount
                    utcOffset
                    viewsCount
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    public MarketingActivity getMarketingActivity(ShopifyAuthContext context, String activityId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", activityId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_MARKETING_ACTIVITY_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<MarketingActivityData> response = graphQLClient.execute(
                request,
                MarketingActivityData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to get marketing activity: {}", response.getErrors());
            throw new RuntimeException("Failed to get marketing activity");
        }
        
        return response.getData().getMarketingActivity();
    }
    
    public MarketingActivityConnection listMarketingActivities(
            ShopifyAuthContext context,
            int first,
            String after,
            String query,
            String sortKey,
            Boolean reverse) {
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) variables.put("after", after);
        if (query != null) variables.put("query", query);
        if (sortKey != null) variables.put("sortKey", sortKey);
        if (reverse != null) variables.put("reverse", reverse);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(LIST_MARKETING_ACTIVITIES_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<MarketingActivitiesData> response = graphQLClient.execute(
                request,
                MarketingActivitiesData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to list marketing activities: {}", response.getErrors());
            throw new RuntimeException("Failed to list marketing activities");
        }
        
        return response.getData().getMarketingActivities();
    }
    
    public MarketingActivity createMarketingActivity(ShopifyAuthContext context, MarketingActivityCreateInput input) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_MARKETING_ACTIVITY_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MarketingActivityCreateData> response = graphQLClient.execute(
                request,
                MarketingActivityCreateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to create marketing activity: {}", response.getErrors());
            throw new RuntimeException("Failed to create marketing activity");
        }
        
        MarketingActivityCreateResponse createResponse = response.getData().getMarketingActivityCreate();
        if (createResponse.getUserErrors() != null && !createResponse.getUserErrors().isEmpty()) {
            log.error("User errors creating marketing activity: {}", createResponse.getUserErrors());
            throw new RuntimeException("Failed to create marketing activity: " + createResponse.getUserErrors());
        }
        
        return createResponse.getMarketingActivity();
    }
    
    public MarketingActivity updateMarketingActivity(ShopifyAuthContext context, MarketingActivityUpdateInput input) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_MARKETING_ACTIVITY_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MarketingActivityUpdateData> response = graphQLClient.execute(
                request,
                MarketingActivityUpdateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to update marketing activity: {}", response.getErrors());
            throw new RuntimeException("Failed to update marketing activity");
        }
        
        MarketingActivityUpdateResponse updateResponse = response.getData().getMarketingActivityUpdate();
        if (updateResponse.getUserErrors() != null && !updateResponse.getUserErrors().isEmpty()) {
            log.error("User errors updating marketing activity: {}", updateResponse.getUserErrors());
            throw new RuntimeException("Failed to update marketing activity: " + updateResponse.getUserErrors());
        }
        
        return updateResponse.getMarketingActivity();
    }
    
    public MarketingEventConnection listMarketingEvents(
            ShopifyAuthContext context,
            int first,
            String after,
            String query,
            String sortKey,
            Boolean reverse) {
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) variables.put("after", after);
        if (query != null) variables.put("query", query);
        if (sortKey != null) variables.put("sortKey", sortKey);
        if (reverse != null) variables.put("reverse", reverse);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(LIST_MARKETING_EVENTS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<MarketingEventsData> response = graphQLClient.execute(
                request,
                MarketingEventsData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to list marketing events: {}", response.getErrors());
            throw new RuntimeException("Failed to list marketing events");
        }
        
        return response.getData().getMarketingEvents();
    }
    
    public MarketingEngagement createMarketingEngagement(
            ShopifyAuthContext context,
            String marketingActivityId,
            MarketingEngagementInput engagementInput) {
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("marketingActivityId", marketingActivityId);
        variables.put("marketingEngagement", engagementInput);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_MARKETING_ENGAGEMENT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MarketingEngagementCreateData> response = graphQLClient.execute(
                request,
                MarketingEngagementCreateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to create marketing engagement: {}", response.getErrors());
            throw new RuntimeException("Failed to create marketing engagement");
        }
        
        MarketingEngagementCreateResponse createResponse = response.getData().getMarketingEngagementCreate();
        if (createResponse.getUserErrors() != null && !createResponse.getUserErrors().isEmpty()) {
            log.error("User errors creating marketing engagement: {}", createResponse.getUserErrors());
            throw new RuntimeException("Failed to create marketing engagement: " + createResponse.getUserErrors());
        }
        
        return createResponse.getMarketingEngagement();
    }
    
    @Data
    private static class MarketingActivityData {
        private MarketingActivity marketingActivity;
    }
    
    @Data
    private static class MarketingActivitiesData {
        private MarketingActivityConnection marketingActivities;
    }
    
    @Data
    private static class MarketingActivityCreateData {
        private MarketingActivityCreateResponse marketingActivityCreate;
    }
    
    @Data
    private static class MarketingActivityUpdateData {
        private MarketingActivityUpdateResponse marketingActivityUpdate;
    }
    
    @Data
    private static class MarketingEventsData {
        private MarketingEventConnection marketingEvents;
    }
    
    @Data
    private static class MarketingEngagementCreateData {
        private MarketingEngagementCreateResponse marketingEngagementCreate;
    }
    
    @Data
    public static class MarketingActivityCreateResponse {
        private MarketingActivity marketingActivity;
        private String redirectPath;
        private List<MarketingActivityUserError> userErrors;
    }
    
    @Data
    public static class MarketingActivityUpdateResponse {
        private MarketingActivity marketingActivity;
        private String redirectPath;
        private List<MarketingActivityUserError> userErrors;
    }
    
    @Data
    public static class MarketingEngagementCreateResponse {
        private MarketingEngagement marketingEngagement;
        private List<MarketingActivityUserError> userErrors;
    }
    
    @Data
    public static class MarketingActivityUserError {
        private List<String> field;
        private String message;
        private String code;
    }
}