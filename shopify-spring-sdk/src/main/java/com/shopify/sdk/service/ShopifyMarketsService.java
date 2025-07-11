package com.shopify.sdk.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.shopify.sdk.client.GraphQLRequest;
import com.shopify.sdk.client.GraphQLResponse;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.config.ShopifyAuthContext;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.common.UserError;
import com.shopify.sdk.model.markets.*;
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
public class ShopifyMarketsService {
    
    private final ShopifyGraphQLClient graphQLClient;
    
    // Query for market localizable resources
    private static final String GET_MARKET_LOCALIZABLE_RESOURCES_QUERY = """
        query marketLocalizableResources(
            $marketId: ID!,
            $resourceType: MarketLocalizableResourceType!,
            $first: Int!,
            $after: String
        ) {
            market(id: $marketId) {
                localizableResources(
                    resourceType: $resourceType,
                    first: $first,
                    after: $after
                ) {
                    edges {
                        node {
                            marketId
                            marketLocalizations {
                                key
                                outdated
                                value
                            }
                            resourceId
                            resourceType
                            translatableContent {
                                key
                                value
                                digest
                                locale
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
        }
        """;
    
    // Query for market regional subdivisions
    private static final String GET_MARKET_REGIONAL_SUBDIVISIONS_QUERY = """
        query marketRegionalSubdivisions($countryCode: CountryCode!) {
            marketRegionalSubdivisions(countryCode: $countryCode) {
                code
                name
            }
        }
        """;
    
    // Query for selling plan groups
    private static final String GET_SELLING_PLAN_GROUPS_QUERY = """
        query sellingPlanGroups($first: Int!, $after: String, $query: String, $sortKey: SellingPlanGroupSortKeys, $reverse: Boolean) {
            sellingPlanGroups(
                first: $first,
                after: $after,
                query: $query,
                sortKey: $sortKey,
                reverse: $reverse
            ) {
                edges {
                    node {
                        id
                        appId
                        appliesToProduct
                        appliesToProductVariant
                        appliesToProductVariants
                        createdAt
                        description
                        merchantCode
                        name
                        options
                        position
                        productCount
                        productVariantCount
                        summary
                        sellingPlans(first: 10) {
                            edges {
                                node {
                                    id
                                    name
                                    description
                                    options
                                    position
                                    category
                                }
                            }
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
    
    // Query for single selling plan group
    private static final String GET_SELLING_PLAN_GROUP_QUERY = """
        query sellingPlanGroup($id: ID!) {
            sellingPlanGroup(id: $id) {
                id
                appId
                appliesToProduct
                appliesToProductVariant
                appliesToProductVariants
                createdAt
                description
                merchantCode
                name
                options
                position
                productCount
                productVariantCount
                summary
                sellingPlans(first: 50) {
                    edges {
                        node {
                            id
                            billingPolicy {
                                ... on SellingPlanRecurringBillingPolicy {
                                    anchors {
                                        cutoffDay
                                        day
                                        month
                                        type
                                    }
                                    interval
                                    intervalCount
                                    maxCycles
                                    minCycles
                                }
                                ... on SellingPlanFixedBillingPolicy {
                                    checkoutCharge {
                                        type
                                        value {
                                            ... on SellingPlanCheckoutChargePercentageValue {
                                                percentage
                                            }
                                            ... on MoneyV2 {
                                                amount
                                                currencyCode
                                            }
                                        }
                                    }
                                    remainingBalanceChargeTrigger
                                }
                            }
                            category
                            createdAt
                            deliveryPolicy {
                                ... on SellingPlanRecurringDeliveryPolicy {
                                    anchors {
                                        cutoffDay
                                        day
                                        month
                                        type
                                    }
                                    cutoff
                                    intent
                                    interval
                                    intervalCount
                                }
                                ... on SellingPlanFixedDeliveryPolicy {
                                    anchors {
                                        cutoffDay
                                        day
                                        month
                                        type
                                    }
                                    cutoff
                                    fulfillmentExactTime
                                    fulfillmentTrigger
                                    intent
                                }
                            }
                            description
                            inventoryPolicy {
                                reserve
                            }
                            name
                            options
                            position
                            pricingPolicies {
                                ... on SellingPlanRecurringPricingPolicy {
                                    adjustmentType
                                    adjustmentValue {
                                        ... on SellingPlanPricingPolicyPercentageValue {
                                            percentage
                                        }
                                        ... on MoneyV2 {
                                            amount
                                            currencyCode
                                        }
                                    }
                                    afterCycle
                                }
                                ... on SellingPlanFixedPricingPolicy {
                                    adjustmentType
                                    adjustmentValue {
                                        ... on SellingPlanPricingPolicyPercentageValue {
                                            percentage
                                        }
                                        ... on MoneyV2 {
                                            amount
                                            currencyCode
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        """;
    
    // Mutation to register market localizations
    private static final String REGISTER_MARKET_LOCALIZATIONS_MUTATION = """
        mutation marketLocalizationsRegister($resourceId: ID!, $marketLocalizations: [MarketLocalizationRegisterInput!]!) {
            marketLocalizationsRegister(
                resourceId: $resourceId,
                marketLocalizations: $marketLocalizations
            ) {
                marketLocalizations {
                    key
                    value
                    outdated
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to remove market localizations
    private static final String REMOVE_MARKET_LOCALIZATIONS_MUTATION = """
        mutation marketLocalizationsRemove($marketLocalizations: [MarketLocalizationRemoveInput!]!) {
            marketLocalizationsRemove(marketLocalizations: $marketLocalizations) {
                marketLocalizations {
                    key
                    resourceId
                    marketId
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to create market region
    private static final String CREATE_MARKET_REGION_MUTATION = """
        mutation marketRegionCreate($input: MarketRegionCreateInput!) {
            marketRegionCreate(input: $input) {
                market {
                    id
                    name
                    regions(first: 10) {
                        edges {
                            node {
                                id
                                name
                            }
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
    
    // Mutation to update market region
    private static final String UPDATE_MARKET_REGION_MUTATION = """
        mutation marketRegionUpdate($input: MarketRegionUpdateInput!) {
            marketRegionUpdate(input: $input) {
                market {
                    id
                    name
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to delete market region
    private static final String DELETE_MARKET_REGION_MUTATION = """
        mutation marketRegionDelete($input: MarketRegionDeleteInput!) {
            marketRegionDelete(input: $input) {
                market {
                    id
                    name
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to create selling plan group
    private static final String CREATE_SELLING_PLAN_GROUP_MUTATION = """
        mutation sellingPlanGroupCreate($input: SellingPlanGroupInput!, $resources: SellingPlanGroupResourceInput) {
            sellingPlanGroupCreate(input: $input, resources: $resources) {
                sellingPlanGroup {
                    id
                    name
                    merchantCode
                    summary
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    // Mutation to update selling plan group
    private static final String UPDATE_SELLING_PLAN_GROUP_MUTATION = """
        mutation sellingPlanGroupUpdate($id: ID!, $input: SellingPlanGroupInput!) {
            sellingPlanGroupUpdate(id: $id, input: $input) {
                sellingPlanGroup {
                    id
                    name
                    merchantCode
                    summary
                }
                deletedSellingPlanIds
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    // Mutation to delete selling plan group
    private static final String DELETE_SELLING_PLAN_GROUP_MUTATION = """
        mutation sellingPlanGroupDelete($id: ID!) {
            sellingPlanGroupDelete(id: $id) {
                deletedSellingPlanGroupId
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    // Mutation to add products to selling plan group
    private static final String ADD_PRODUCTS_TO_SELLING_PLAN_GROUP_MUTATION = """
        mutation sellingPlanGroupAddProducts($id: ID!, $productIds: [ID!]!) {
            sellingPlanGroupAddProducts(id: $id, productIds: $productIds) {
                sellingPlanGroup {
                    id
                    productCount
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    // Mutation to remove products from selling plan group
    private static final String REMOVE_PRODUCTS_FROM_SELLING_PLAN_GROUP_MUTATION = """
        mutation sellingPlanGroupRemoveProducts($id: ID!, $productIds: [ID!]!) {
            sellingPlanGroupRemoveProducts(id: $id, productIds: $productIds) {
                removedProductIds
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    // Service methods
    public List<MarketLocalizableResource> getMarketLocalizableResources(
            ShopifyAuthContext context,
            String marketId,
            MarketLocalizableResourceType resourceType,
            int first,
            String after) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("marketId", marketId);
        variables.put("resourceType", resourceType);
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_MARKET_LOCALIZABLE_RESOURCES_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<MarketLocalizableResourcesResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MarketLocalizableResourcesResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get market localizable resources", response.getErrors());
        }
        
        List<MarketLocalizableResource> resources = new ArrayList<>();
        response.getData().market.localizableResources.edges.forEach(edge -> 
            resources.add(edge.node)
        );
        
        return resources;
    }
    
    public List<MarketRegionalSubdivision> getMarketRegionalSubdivisions(
            ShopifyAuthContext context,
            String countryCode) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("countryCode", countryCode);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_MARKET_REGIONAL_SUBDIVISIONS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<MarketRegionalSubdivisionsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MarketRegionalSubdivisionsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get market regional subdivisions", response.getErrors());
        }
        
        return response.getData().marketRegionalSubdivisions;
    }
    
    public List<SellingPlanGroup> getSellingPlanGroups(
            ShopifyAuthContext context,
            int first,
            String after,
            String query,
            SellingPlanGroupSortKeys sortKey,
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
                .query(GET_SELLING_PLAN_GROUPS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<SellingPlanGroupsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<SellingPlanGroupsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get selling plan groups", response.getErrors());
        }
        
        List<SellingPlanGroup> groups = new ArrayList<>();
        response.getData().sellingPlanGroups.edges.forEach(edge -> 
            groups.add(edge.node)
        );
        
        return groups;
    }
    
    public SellingPlanGroup getSellingPlanGroup(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_SELLING_PLAN_GROUP_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<SellingPlanGroupResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<SellingPlanGroupResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get selling plan group", response.getErrors());
        }
        
        return response.getData().sellingPlanGroup;
    }
    
    public List<MarketLocalization> registerMarketLocalizations(
            ShopifyAuthContext context,
            String resourceId,
            List<MarketLocalizationRegisterInput> marketLocalizations) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("resourceId", resourceId);
        variables.put("marketLocalizations", marketLocalizations);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(REGISTER_MARKET_LOCALIZATIONS_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MarketLocalizationsRegisterResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MarketLocalizationsRegisterResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to register market localizations", response.getErrors());
        }
        
        if (response.getData().marketLocalizationsRegister.userErrors != null && 
            !response.getData().marketLocalizationsRegister.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to register market localizations",
                    response.getData().marketLocalizationsRegister.userErrors
            );
        }
        
        return response.getData().marketLocalizationsRegister.marketLocalizations;
    }
    
    public void removeMarketLocalizations(
            ShopifyAuthContext context,
            List<MarketLocalizationRemoveInput> marketLocalizations) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("marketLocalizations", marketLocalizations);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(REMOVE_MARKET_LOCALIZATIONS_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MarketLocalizationsRemoveResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MarketLocalizationsRemoveResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to remove market localizations", response.getErrors());
        }
        
        if (response.getData().marketLocalizationsRemove.userErrors != null && 
            !response.getData().marketLocalizationsRemove.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to remove market localizations",
                    response.getData().marketLocalizationsRemove.userErrors
            );
        }
    }
    
    public Market createMarketRegion(ShopifyAuthContext context, MarketRegionCreateInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_MARKET_REGION_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MarketRegionCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MarketRegionCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create market region", response.getErrors());
        }
        
        if (response.getData().marketRegionCreate.userErrors != null && 
            !response.getData().marketRegionCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create market region",
                    response.getData().marketRegionCreate.userErrors
            );
        }
        
        return response.getData().marketRegionCreate.market;
    }
    
    public Market updateMarketRegion(ShopifyAuthContext context, MarketRegionUpdateInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_MARKET_REGION_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MarketRegionUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MarketRegionUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update market region", response.getErrors());
        }
        
        if (response.getData().marketRegionUpdate.userErrors != null && 
            !response.getData().marketRegionUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update market region",
                    response.getData().marketRegionUpdate.userErrors
            );
        }
        
        return response.getData().marketRegionUpdate.market;
    }
    
    public Market deleteMarketRegion(ShopifyAuthContext context, MarketRegionDeleteInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_MARKET_REGION_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MarketRegionDeleteResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MarketRegionDeleteResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to delete market region", response.getErrors());
        }
        
        if (response.getData().marketRegionDelete.userErrors != null && 
            !response.getData().marketRegionDelete.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to delete market region",
                    response.getData().marketRegionDelete.userErrors
            );
        }
        
        return response.getData().marketRegionDelete.market;
    }
    
    public SellingPlanGroup createSellingPlanGroup(
            ShopifyAuthContext context,
            SellingPlanGroupInput input,
            SellingPlanGroupResourceInput resources) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        if (resources != null) {
            variables.put("resources", resources);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_SELLING_PLAN_GROUP_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<SellingPlanGroupCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<SellingPlanGroupCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create selling plan group", response.getErrors());
        }
        
        if (response.getData().sellingPlanGroupCreate.userErrors != null && 
            !response.getData().sellingPlanGroupCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create selling plan group",
                    response.getData().sellingPlanGroupCreate.userErrors
            );
        }
        
        return response.getData().sellingPlanGroupCreate.sellingPlanGroup;
    }
    
    public SellingPlanGroupUpdateResult updateSellingPlanGroup(
            ShopifyAuthContext context,
            String id,
            SellingPlanGroupInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_SELLING_PLAN_GROUP_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<SellingPlanGroupUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<SellingPlanGroupUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update selling plan group", response.getErrors());
        }
        
        if (response.getData().sellingPlanGroupUpdate.userErrors != null && 
            !response.getData().sellingPlanGroupUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update selling plan group",
                    response.getData().sellingPlanGroupUpdate.userErrors
            );
        }
        
        return response.getData().sellingPlanGroupUpdate;
    }
    
    public String deleteSellingPlanGroup(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_SELLING_PLAN_GROUP_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<SellingPlanGroupDeleteResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<SellingPlanGroupDeleteResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to delete selling plan group", response.getErrors());
        }
        
        if (response.getData().sellingPlanGroupDelete.userErrors != null && 
            !response.getData().sellingPlanGroupDelete.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to delete selling plan group",
                    response.getData().sellingPlanGroupDelete.userErrors
            );
        }
        
        return response.getData().sellingPlanGroupDelete.deletedSellingPlanGroupId;
    }
    
    public SellingPlanGroup addProductsToSellingPlanGroup(
            ShopifyAuthContext context,
            String id,
            List<String> productIds) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        variables.put("productIds", productIds);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(ADD_PRODUCTS_TO_SELLING_PLAN_GROUP_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<SellingPlanGroupAddProductsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<SellingPlanGroupAddProductsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to add products to selling plan group", response.getErrors());
        }
        
        if (response.getData().sellingPlanGroupAddProducts.userErrors != null && 
            !response.getData().sellingPlanGroupAddProducts.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to add products to selling plan group",
                    response.getData().sellingPlanGroupAddProducts.userErrors
            );
        }
        
        return response.getData().sellingPlanGroupAddProducts.sellingPlanGroup;
    }
    
    public List<String> removeProductsFromSellingPlanGroup(
            ShopifyAuthContext context,
            String id,
            List<String> productIds) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        variables.put("productIds", productIds);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(REMOVE_PRODUCTS_FROM_SELLING_PLAN_GROUP_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<SellingPlanGroupRemoveProductsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<SellingPlanGroupRemoveProductsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to remove products from selling plan group", response.getErrors());
        }
        
        if (response.getData().sellingPlanGroupRemoveProducts.userErrors != null && 
            !response.getData().sellingPlanGroupRemoveProducts.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to remove products from selling plan group",
                    response.getData().sellingPlanGroupRemoveProducts.userErrors
            );
        }
        
        return response.getData().sellingPlanGroupRemoveProducts.removedProductIds;
    }
    
    // Response classes
    @Data
    private static class MarketLocalizableResourcesResponse {
        private Market market;
    }
    
    @Data
    private static class Market {
        private MarketLocalizableResourceConnection localizableResources;
        @JsonProperty("id")
        private ID id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("regions")
        private MarketRegionConnection regions;
    }
    
    @Data
    private static class MarketRegionConnection {
        private List<MarketRegionEdge> edges;
    }
    
    @Data
    private static class MarketRegionEdge {
        private MarketRegion node;
    }
    
    @Data
    private static class MarketRegion {
        @JsonProperty("id")
        private ID id;
        @JsonProperty("name")
        private String name;
    }
    
    @Data
    private static class MarketRegionalSubdivisionsResponse {
        private List<MarketRegionalSubdivision> marketRegionalSubdivisions;
    }
    
    @Data
    private static class SellingPlanGroupsResponse {
        private SellingPlanGroupConnection sellingPlanGroups;
    }
    
    @Data
    private static class SellingPlanGroupResponse {
        private SellingPlanGroup sellingPlanGroup;
    }
    
    @Data
    private static class MarketLocalizationsRegisterResponse {
        private MarketLocalizationsRegisterResult marketLocalizationsRegister;
    }
    
    @Data
    private static class MarketLocalizationsRegisterResult {
        private List<MarketLocalization> marketLocalizations;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class MarketLocalizationsRemoveResponse {
        private MarketLocalizationsRemoveResult marketLocalizationsRemove;
    }
    
    @Data
    private static class MarketLocalizationsRemoveResult {
        private List<MarketLocalizationKey> marketLocalizations;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class MarketLocalizationKey {
        private String key;
        private String resourceId;
        private String marketId;
    }
    
    @Data
    private static class MarketRegionCreateResponse {
        private MarketRegionCreateResult marketRegionCreate;
    }
    
    @Data
    private static class MarketRegionCreateResult {
        private Market market;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class MarketRegionUpdateResponse {
        private MarketRegionUpdateResult marketRegionUpdate;
    }
    
    @Data
    private static class MarketRegionUpdateResult {
        private Market market;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class MarketRegionDeleteResponse {
        private MarketRegionDeleteResult marketRegionDelete;
    }
    
    @Data
    private static class MarketRegionDeleteResult {
        private Market market;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class SellingPlanGroupCreateResponse {
        private SellingPlanGroupCreateResult sellingPlanGroupCreate;
    }
    
    @Data
    private static class SellingPlanGroupCreateResult {
        private SellingPlanGroup sellingPlanGroup;
        private List<SellingPlanGroupUserError> userErrors;
    }
    
    @Data
    private static class SellingPlanGroupUpdateResponse {
        private SellingPlanGroupUpdateResult sellingPlanGroupUpdate;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SellingPlanGroupUpdateResult {
        private SellingPlanGroup sellingPlanGroup;
        private List<ID> deletedSellingPlanIds;
        private List<SellingPlanGroupUserError> userErrors;
    }
    
    @Data
    private static class SellingPlanGroupDeleteResponse {
        private SellingPlanGroupDeleteResult sellingPlanGroupDelete;
    }
    
    @Data
    private static class SellingPlanGroupDeleteResult {
        private String deletedSellingPlanGroupId;
        private List<SellingPlanGroupUserError> userErrors;
    }
    
    @Data
    private static class SellingPlanGroupAddProductsResponse {
        private SellingPlanGroupAddProductsResult sellingPlanGroupAddProducts;
    }
    
    @Data
    private static class SellingPlanGroupAddProductsResult {
        private SellingPlanGroup sellingPlanGroup;
        private List<SellingPlanGroupUserError> userErrors;
    }
    
    @Data
    private static class SellingPlanGroupRemoveProductsResponse {
        private SellingPlanGroupRemoveProductsResult sellingPlanGroupRemoveProducts;
    }
    
    @Data
    private static class SellingPlanGroupRemoveProductsResult {
        private List<String> removedProductIds;
        private List<SellingPlanGroupUserError> userErrors;
    }
    
    @Data
    private static class SellingPlanGroupUserError {
        private List<String> field;
        private String message;
        private SellingPlanGroupUserErrorCode code;
    }
    
    // Enums
    public enum SellingPlanGroupSortKeys {
        CREATED_AT,
        ID,
        NAME,
        UPDATED_AT,
        RELEVANCE
    }
    
    public enum SellingPlanGroupUserErrorCode {
        BLANK,
        EQUAL_TO,
        GREATER_THAN,
        GREATER_THAN_OR_EQUAL_TO,
        INCLUSION,
        INVALID,
        LESS_THAN,
        LESS_THAN_OR_EQUAL_TO,
        NOT_A_NUMBER,
        NOT_FOUND,
        PRESENT,
        TAKEN,
        TOO_BIG,
        TOO_LONG,
        TOO_SHORT,
        WRONG_LENGTH,
        SELLING_PLAN_COUNT_UPPER_BOUND,
        SELLING_PLAN_COUNT_LOWER_BOUND,
        SELLING_PLAN_MAX_CYCLES_MUST_BE_GREATER_THAN_MIN_CYCLES,
        SELLING_PLAN_BILLING_AND_DELIVERY_POLICY_ANCHORS_MUST_BE_EQUAL,
        SELLING_PLAN_BILLING_CYCLE_MUST_BE_A_MULTIPLE_OF_DELIVERY_CYCLE,
        SELLING_PLAN_PRICING_POLICIES_MUST_CONTAIN_A_VALUE,
        SELLING_PLAN_MISSING_OPTION2_LABEL_ON_PARENT_GROUP,
        SELLING_PLAN_MISSING_OPTION3_LABEL_ON_PARENT_GROUP,
        SELLING_PLAN_OPTION2_REQUIRED_AS_DEFINED_ON_PARENT_GROUP,
        SELLING_PLAN_OPTION3_REQUIRED_AS_DEFINED_ON_PARENT_GROUP,
        RESOURCE_LIST_CONTAINS_INVALID_IDS,
        PRODUCT_VARIANT_DOES_NOT_EXIST,
        PRODUCT_DOES_NOT_EXIST,
        GROUP_DOES_NOT_EXIST,
        GROUP_COULD_NOT_BE_DELETED,
        PLAN_DOES_NOT_EXIST,
        PLAN_ID_MUST_BE_SPECIFIED_TO_UPDATE,
        ONLY_LOCATIONS_THAT_FULFILL_ONLINE_ORDERS,
        SELLING_PLAN_ANCHORS_NOT_ALLOWED,
        SELLING_PLAN_ANCHORS_REQUIRED
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SellingPlanGroupResourceInput {
        private List<ID> productIds;
        private List<ID> productVariantIds;
    }
}