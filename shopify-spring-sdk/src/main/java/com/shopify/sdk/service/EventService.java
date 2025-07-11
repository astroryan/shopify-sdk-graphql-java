package com.shopify.sdk.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.shopify.sdk.client.GraphQLRequest;
import com.shopify.sdk.client.GraphQLResponse;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.config.ShopifyAuthContext;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.common.PageInfo;
import com.shopify.sdk.model.common.UserError;
import com.shopify.sdk.model.event.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {
    
    private final ShopifyGraphQLClient graphQLClient;
    
    // Query for shop events
    private static final String GET_EVENTS_QUERY = """
        query shop($first: Int!, $after: String, $query: String, $sortKey: EventSortKeys, $reverse: Boolean) {
            shop {
                events(
                    first: $first,
                    after: $after,
                    query: $query,
                    sortKey: $sortKey,
                    reverse: $reverse
                ) {
                    edges {
                        node {
                            id
                            action
                            appTitle
                            attributeToApp
                            attributeToUser
                            createdAt
                            criticalAlert
                            message
                            path
                            subjectId
                            subjectType
                            verb
                        }
                        cursor
                    }
                    pageInfo {
                        hasNextPage
                        endCursor
                    }
                }
            }
        }
        """;
    
    // Query for marketing events
    private static final String GET_MARKETING_EVENTS_QUERY = """
        query marketingEvents($first: Int!, $after: String, $sortKey: MarketingEventSortKeys, $reverse: Boolean) {
            marketingEvents(
                first: $first,
                after: $after,
                sortKey: $sortKey,
                reverse: $reverse
            ) {
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
                        budget {
                            budgetType
                            total {
                                amount
                                currencyCode
                            }
                        }
                        marketingActivity {
                            id
                            activityTitle
                            status
                            tactic
                        }
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
    
    // Query for marketing activities
    private static final String GET_MARKETING_ACTIVITIES_QUERY = """
        query marketingActivities($first: Int!, $after: String, $sortKey: MarketingActivitySortKeys, $reverse: Boolean) {
            marketingActivities(
                first: $first,
                after: $after,
                sortKey: $sortKey,
                reverse: $reverse
            ) {
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
                        appErrors {
                            hasErrors
                        }
                        budget {
                            budgetType
                            total {
                                amount
                                currencyCode
                            }
                        }
                        createdAt
                        formData
                        hierarchyLevel
                        inMainWorkflow
                        marketingChannel {
                            id
                        }
                        marketingEvent {
                            id
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
                    cursor
                }
                pageInfo {
                    hasNextPage
                    endCursor
                }
            }
        }
        """;
    
    // Query for single marketing activity
    private static final String GET_MARKETING_ACTIVITY_QUERY = """
        query node($id: ID!) {
            node(id: $id) {
                ... on MarketingActivity {
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
                    appErrors {
                        hasErrors
                    }
                    budget {
                        budgetType
                        total {
                            amount
                            currencyCode
                        }
                    }
                    createdAt
                    formData
                    hierarchyLevel
                    inMainWorkflow
                    marketingChannel {
                        id
                    }
                    marketingEvent {
                        id
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
        }
        """;
    
    // Mutation to create marketing activity
    private static final String CREATE_MARKETING_ACTIVITY_MUTATION = """
        mutation marketingActivityCreate($marketingActivityInput: MarketingActivityCreateInput!) {
            marketingActivityCreate(marketingActivityInput: $marketingActivityInput) {
                marketingActivity {
                    id
                    activityTitle
                    status
                    tactic
                    marketingChannel {
                        id
                    }
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    // Mutation to update marketing activity
    private static final String UPDATE_MARKETING_ACTIVITY_MUTATION = """
        mutation marketingActivityUpdate($input: MarketingActivityUpdateInput!) {
            marketingActivityUpdate(input: $input) {
                marketingActivity {
                    id
                    activityTitle
                    status
                    tactic
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    // Mutation to update marketing activity external ID
    private static final String UPDATE_MARKETING_ACTIVITY_EXTERNAL_ID_MUTATION = """
        mutation marketingActivityUpdateExternal($input: MarketingActivityUpdateExternalInput!) {
            marketingActivityUpdateExternal(input: $input) {
                marketingActivity {
                    id
                    activityTitle
                    parentRemoteId
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    // Service methods
    public List<Event> getEvents(
            ShopifyAuthContext context,
            int first,
            String after,
            String query,
            EventSortKeys sortKey,
            Boolean reverse) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (query != null) {
            variables.put("query", query);
        }
        if (sortKey != null) {
            variables.put("sortKey", sortKey);
        }
        if (reverse != null) {
            variables.put("reverse", reverse);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_EVENTS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<ShopEventsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ShopEventsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get events", response.getErrors());
        }
        
        List<Event> events = new ArrayList<>();
        response.getData().shop.events.edges.forEach(edge -> 
            events.add(edge.node)
        );
        
        return events;
    }
    
    public List<MarketingEvent> getMarketingEvents(
            ShopifyAuthContext context,
            int first,
            String after,
            MarketingEventSortKeys sortKey,
            Boolean reverse) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (sortKey != null) {
            variables.put("sortKey", sortKey);
        }
        if (reverse != null) {
            variables.put("reverse", reverse);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_MARKETING_EVENTS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<MarketingEventsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MarketingEventsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get marketing events", response.getErrors());
        }
        
        List<MarketingEvent> events = new ArrayList<>();
        response.getData().marketingEvents.edges.forEach(edge -> 
            events.add(edge.node)
        );
        
        return events;
    }
    
    public List<MarketingActivity> getMarketingActivities(
            ShopifyAuthContext context,
            int first,
            String after,
            MarketingActivitySortKeys sortKey,
            Boolean reverse) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (sortKey != null) {
            variables.put("sortKey", sortKey);
        }
        if (reverse != null) {
            variables.put("reverse", reverse);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_MARKETING_ACTIVITIES_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<MarketingActivitiesResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MarketingActivitiesResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get marketing activities", response.getErrors());
        }
        
        List<MarketingActivity> activities = new ArrayList<>();
        response.getData().marketingActivities.edges.forEach(edge -> 
            activities.add(edge.node)
        );
        
        return activities;
    }
    
    public MarketingActivity getMarketingActivity(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_MARKETING_ACTIVITY_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<NodeResponse<MarketingActivity>> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<NodeResponse<MarketingActivity>>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get marketing activity", response.getErrors());
        }
        
        return response.getData().node;
    }
    
    public MarketingActivity createMarketingActivity(
            ShopifyAuthContext context,
            MarketingActivityCreateInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("marketingActivityInput", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_MARKETING_ACTIVITY_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MarketingActivityCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MarketingActivityCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create marketing activity", response.getErrors());
        }
        
        if (response.getData().marketingActivityCreate.userErrors != null && 
            !response.getData().marketingActivityCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create marketing activity",
                    response.getData().marketingActivityCreate.userErrors
            );
        }
        
        return response.getData().marketingActivityCreate.marketingActivity;
    }
    
    public MarketingActivity updateMarketingActivity(
            ShopifyAuthContext context,
            MarketingActivityUpdateInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_MARKETING_ACTIVITY_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MarketingActivityUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MarketingActivityUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update marketing activity", response.getErrors());
        }
        
        if (response.getData().marketingActivityUpdate.userErrors != null && 
            !response.getData().marketingActivityUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update marketing activity",
                    response.getData().marketingActivityUpdate.userErrors
            );
        }
        
        return response.getData().marketingActivityUpdate.marketingActivity;
    }
    
    public MarketingActivity updateMarketingActivityExternalId(
            ShopifyAuthContext context,
            MarketingActivityUpdateExternalInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_MARKETING_ACTIVITY_EXTERNAL_ID_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MarketingActivityUpdateExternalResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MarketingActivityUpdateExternalResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update marketing activity external ID", response.getErrors());
        }
        
        if (response.getData().marketingActivityUpdateExternal.userErrors != null && 
            !response.getData().marketingActivityUpdateExternal.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update marketing activity external ID",
                    response.getData().marketingActivityUpdateExternal.userErrors
            );
        }
        
        return response.getData().marketingActivityUpdateExternal.marketingActivity;
    }
    
    // Input classes
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MarketingActivityCreateInput {
        private MarketingActivityCreateExternalInput marketingActivity;
        private MarketingActivityBudgetInput budget;
        private String context;
        private UTMInput utm;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MarketingActivityCreateExternalInput {
        private String title;
        private String formData;
        private MarketingChannel marketingChannelType;
        private String referringDomain;
        private String remoteId;
        private String remoteUrl;
        private MarketingActivityStatus status;
        private MarketingTactic tactic;
        private String parentActivityId;
        private String parentRemoteId;
        private String urlParameterValue;
        private String adSpend;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MarketingActivityBudgetInput {
        private MarketingBudgetBudgetType budgetType;
        private MoneyInput total;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MoneyInput {
        private String amount;
        private String currencyCode;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UTMInput {
        private String campaign;
        private String source;
        private String medium;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MarketingActivityUpdateInput {
        private String id;
        private MarketingActivityUpdateInput marketingActivity;
        private MarketingActivityStatus status;
        private MarketingActivityBudgetInput budget;
        private Boolean removeMarketingBudget;
        private String context;
        private UTMInput utm;
        private List<String> errors;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MarketingActivityUpdateExternalInput {
        private String remoteId;
        private String id;
        private String title;
        private MarketingActivityBudgetInput budget;
        private String adSpend;
        private Boolean removeMarketingBudget;
        private MarketingActivityStatus status;
        private String parentActivityId;
        private String parentRemoteId;
        private String urlParameterValue;
        private UTMInput utm;
    }
    
    // Response classes
    @Data
    private static class ShopEventsResponse {
        private Shop shop;
    }
    
    @Data
    private static class Shop {
        private EventConnection events;
    }
    
    @Data
    private static class MarketingEventsResponse {
        private MarketingEventConnection marketingEvents;
    }
    
    @Data
    private static class MarketingEventConnection {
        private List<MarketingEventEdge> edges;
        private PageInfo pageInfo;
    }
    
    @Data
    private static class MarketingEventEdge {
        private MarketingEvent node;
        private String cursor;
    }
    
    @Data
    private static class MarketingActivitiesResponse {
        private MarketingActivityConnection marketingActivities;
    }
    
    @Data
    private static class MarketingActivityConnection {
        private List<MarketingActivityEdge> edges;
        private PageInfo pageInfo;
    }
    
    @Data
    private static class MarketingActivityEdge {
        private MarketingActivity node;
        private String cursor;
    }
    
    @Data
    private static class NodeResponse<T> {
        private T node;
    }
    
    @Data
    private static class MarketingActivityCreateResponse {
        private MarketingActivityCreateResult marketingActivityCreate;
    }
    
    @Data
    private static class MarketingActivityCreateResult {
        private MarketingActivity marketingActivity;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class MarketingActivityUpdateResponse {
        private MarketingActivityUpdateResult marketingActivityUpdate;
    }
    
    @Data
    private static class MarketingActivityUpdateResult {
        private MarketingActivity marketingActivity;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class MarketingActivityUpdateExternalResponse {
        private MarketingActivityUpdateExternalResult marketingActivityUpdateExternal;
    }
    
    @Data
    private static class MarketingActivityUpdateExternalResult {
        private MarketingActivity marketingActivity;
        private List<UserError> userErrors;
    }
    
    // Enums
    public enum EventSortKeys {
        CREATED_AT,
        ID,
        RELEVANCE
    }
    
    public enum MarketingEventSortKeys {
        STARTED_AT,
        ID,
        RELEVANCE
    }
    
    public enum MarketingActivitySortKeys {
        CREATED_AT,
        ID,
        RELEVANCE,
        TITLE
    }
    
    public enum MarketingChannel {
        SEARCH,
        DISPLAY,
        SOCIAL,
        EMAIL,
        REFERRAL
    }
}