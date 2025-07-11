package com.shopify.sdk.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.shopify.sdk.client.GraphQLRequest;
import com.shopify.sdk.client.GraphQLResponse;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.config.ShopifyAuthContext;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.common.UserError;
import com.shopify.sdk.model.marketplace.*;
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
public class MarketplaceService {
    
    private final ShopifyGraphQLClient graphQLClient;
    
    // Query for channels
    private static final String GET_CHANNELS_QUERY = """
        query channels($first: Int!, $after: String) {
            channels(first: $first, after: $after) {
                edges {
                    node {
                        id
                        app {
                            id
                        }
                        handle
                        hasCollection
                        name
                        navigationItems {
                            id
                            title
                            url
                        }
                        overviewPath
                        productsCount {
                            count
                            precision
                        }
                        supportsFuturePublishing
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
    
    // Query for single channel
    private static final String GET_CHANNEL_QUERY = """
        query node($id: ID!) {
            node(id: $id) {
                ... on Channel {
                    id
                    app {
                        id
                    }
                    handle
                    hasCollection
                    name
                    navigationItems {
                        id
                        title
                        url
                    }
                    overviewPath
                    productsCount {
                        count
                        precision
                    }
                    supportsFuturePublishing
                }
            }
        }
        """;
    
    // Query for markets
    private static final String GET_MARKETS_QUERY = """
        query markets($first: Int!, $after: String) {
            markets(first: $first, after: $after) {
                edges {
                    node {
                        id
                        catalogsCount {
                            count
                            precision
                        }
                        currencySettings(first: 10) {
                            edges {
                                node {
                                    baseCurrency {
                                        currencyCode
                                        currencyName
                                        enabled
                                    }
                                    localCurrencies
                                }
                            }
                        }
                        enabled
                        handle
                        name
                        primary
                        regions(first: 50) {
                            edges {
                                node {
                                    id
                                    name
                                    ... on MarketRegionCountry {
                                        code
                                        currency {
                                            currencyCode
                                            currencyName
                                            enabled
                                        }
                                    }
                                }
                            }
                        }
                        webPresence {
                            id
                            alternateLocales
                            defaultLocale
                            domain {
                                host
                                sslEnabled
                                url
                            }
                            subfolderSuffix
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
    
    // Query for single market
    private static final String GET_MARKET_QUERY = """
        query node($id: ID!) {
            node(id: $id) {
                ... on Market {
                    id
                    catalogsCount {
                        count
                        precision
                    }
                    currencySettings(first: 10) {
                        edges {
                            node {
                                baseCurrency {
                                    currencyCode
                                    currencyName
                                    enabled
                                }
                                localCurrencies
                            }
                        }
                    }
                    enabled
                    handle
                    name
                    primary
                    regions(first: 50) {
                        edges {
                            node {
                                id
                                name
                                ... on MarketRegionCountry {
                                    code
                                    currency {
                                        currencyCode
                                        currencyName
                                        enabled
                                    }
                                }
                            }
                        }
                    }
                    webPresence {
                        id
                        alternateLocales
                        defaultLocale
                        domain {
                            host
                            sslEnabled
                            url
                        }
                        subfolderSuffix
                    }
                }
            }
        }
        """;
    
    // Query for catalogs
    private static final String GET_CATALOGS_QUERY = """
        query catalogs($first: Int!, $after: String, $query: String, $status: CatalogStatus) {
            catalogs(first: $first, after: $after, query: $query, status: $status) {
                edges {
                    node {
                        id
                        apps(first: 10) {
                            edges {
                                node {
                                    id
                                }
                            }
                        }
                        priceList {
                            id
                            currency
                            name
                            fixedPricesCount
                        }
                        publication {
                            id
                            name
                            supportsFuturePublishing
                        }
                        status
                        title
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
    
    // Query for price lists
    private static final String GET_PRICE_LISTS_QUERY = """
        query priceLists($first: Int!, $after: String, $sortKey: PriceListSortKeys, $reverse: Boolean) {
            priceLists(first: $first, after: $after, sortKey: $sortKey, reverse: $reverse) {
                edges {
                    node {
                        id
                        catalog {
                            id
                            title
                        }
                        contextRule {
                            countries
                            market {
                                id
                                name
                            }
                        }
                        currency
                        fixedPricesCount
                        name
                        parent {
                            adjustment {
                                type
                                value
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
    
    // Mutation to create market
    private static final String CREATE_MARKET_MUTATION = """
        mutation marketCreate($input: MarketCreateInput!) {
            marketCreate(input: $input) {
                market {
                    id
                    name
                    handle
                    enabled
                    primary
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    // Mutation to update market
    private static final String UPDATE_MARKET_MUTATION = """
        mutation marketUpdate($id: ID!, $input: MarketUpdateInput!) {
            marketUpdate(id: $id, input: $input) {
                market {
                    id
                    name
                    handle
                    enabled
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    // Mutation to delete market
    private static final String DELETE_MARKET_MUTATION = """
        mutation marketDelete($id: ID!) {
            marketDelete(id: $id) {
                deletedMarketId
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    // Mutation to create catalog
    private static final String CREATE_CATALOG_MUTATION = """
        mutation catalogCreate($input: CatalogCreateInput!) {
            catalogCreate(input: $input) {
                catalog {
                    id
                    title
                    status
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    // Mutation to update catalog
    private static final String UPDATE_CATALOG_MUTATION = """
        mutation catalogUpdate($id: ID!, $input: CatalogUpdateInput!) {
            catalogUpdate(id: $id, input: $input) {
                catalog {
                    id
                    title
                    status
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    // Mutation to delete catalog
    private static final String DELETE_CATALOG_MUTATION = """
        mutation catalogDelete($id: ID!) {
            catalogDelete(id: $id) {
                deletedCatalogId
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    // Service methods
    public List<Channel> getChannels(ShopifyAuthContext context, int first, String after) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_CHANNELS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<ChannelsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ChannelsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get channels", response.getErrors());
        }
        
        List<Channel> channels = new ArrayList<>();
        response.getData().channels.edges.forEach(edge -> 
            channels.add(edge.node)
        );
        
        return channels;
    }
    
    public Channel getChannel(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_CHANNEL_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<NodeResponse<Channel>> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<NodeResponse<Channel>>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get channel", response.getErrors());
        }
        
        return response.getData().node;
    }
    
    public List<Market> getMarkets(ShopifyAuthContext context, int first, String after) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_MARKETS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<MarketsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MarketsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get markets", response.getErrors());
        }
        
        List<Market> markets = new ArrayList<>();
        response.getData().markets.edges.forEach(edge -> 
            markets.add(edge.node)
        );
        
        return markets;
    }
    
    public Market getMarket(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_MARKET_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<NodeResponse<Market>> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<NodeResponse<Market>>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get market", response.getErrors());
        }
        
        return response.getData().node;
    }
    
    public List<Catalog> getCatalogs(
            ShopifyAuthContext context,
            int first,
            String after,
            String query,
            CatalogStatus status) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        if (query != null) {
            variables.put("query", query);
        }
        if (status != null) {
            variables.put("status", status);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_CATALOGS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<CatalogsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<CatalogsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get catalogs", response.getErrors());
        }
        
        List<Catalog> catalogs = new ArrayList<>();
        response.getData().catalogs.edges.forEach(edge -> 
            catalogs.add(edge.node)
        );
        
        return catalogs;
    }
    
    public List<PriceList> getPriceLists(
            ShopifyAuthContext context,
            int first,
            String after,
            PriceListSortKeys sortKey,
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
                .query(GET_PRICE_LISTS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<PriceListsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<PriceListsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get price lists", response.getErrors());
        }
        
        List<PriceList> priceLists = new ArrayList<>();
        response.getData().priceLists.edges.forEach(edge -> 
            priceLists.add(edge.node)
        );
        
        return priceLists;
    }
    
    public Market createMarket(ShopifyAuthContext context, MarketCreateInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_MARKET_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MarketCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MarketCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create market", response.getErrors());
        }
        
        if (response.getData().marketCreate.userErrors != null && 
            !response.getData().marketCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create market",
                    response.getData().marketCreate.userErrors
            );
        }
        
        return response.getData().marketCreate.market;
    }
    
    public Market updateMarket(ShopifyAuthContext context, String id, MarketUpdateInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_MARKET_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MarketUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MarketUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update market", response.getErrors());
        }
        
        if (response.getData().marketUpdate.userErrors != null && 
            !response.getData().marketUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update market",
                    response.getData().marketUpdate.userErrors
            );
        }
        
        return response.getData().marketUpdate.market;
    }
    
    public String deleteMarket(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_MARKET_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MarketDeleteResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MarketDeleteResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to delete market", response.getErrors());
        }
        
        if (response.getData().marketDelete.userErrors != null && 
            !response.getData().marketDelete.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to delete market",
                    response.getData().marketDelete.userErrors
            );
        }
        
        return response.getData().marketDelete.deletedMarketId;
    }
    
    public Catalog createCatalog(ShopifyAuthContext context, CatalogCreateInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_CATALOG_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CatalogCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<CatalogCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create catalog", response.getErrors());
        }
        
        if (response.getData().catalogCreate.userErrors != null && 
            !response.getData().catalogCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create catalog",
                    response.getData().catalogCreate.userErrors
            );
        }
        
        return response.getData().catalogCreate.catalog;
    }
    
    public Catalog updateCatalog(ShopifyAuthContext context, String id, CatalogUpdateInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_CATALOG_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CatalogUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<CatalogUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update catalog", response.getErrors());
        }
        
        if (response.getData().catalogUpdate.userErrors != null && 
            !response.getData().catalogUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update catalog",
                    response.getData().catalogUpdate.userErrors
            );
        }
        
        return response.getData().catalogUpdate.catalog;
    }
    
    public String deleteCatalog(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_CATALOG_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<CatalogDeleteResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<CatalogDeleteResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to delete catalog", response.getErrors());
        }
        
        if (response.getData().catalogDelete.userErrors != null && 
            !response.getData().catalogDelete.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to delete catalog",
                    response.getData().catalogDelete.userErrors
            );
        }
        
        return response.getData().catalogDelete.deletedCatalogId;
    }
    
    // Input classes
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MarketCreateInput {
        private String name;
        private String handle;
        private Boolean enabled;
        private List<MarketRegionCreateInput> regions;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MarketRegionCreateInput {
        private List<String> countries;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MarketUpdateInput {
        private String name;
        private String handle;
        private Boolean enabled;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CatalogCreateInput {
        private String title;
        private CatalogStatus status;
        private CatalogContextInput context;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CatalogContextInput {
        private List<String> companyLocationIds;
        private String publicationId;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CatalogUpdateInput {
        private String title;
        private CatalogStatus status;
    }
    
    // Response classes
    @Data
    private static class ChannelsResponse {
        private ChannelConnection channels;
    }
    
    @Data
    private static class ChannelConnection {
        private List<ChannelEdge> edges;
        private PageInfo pageInfo;
    }
    
    @Data
    private static class ChannelEdge {
        private Channel node;
        private String cursor;
    }
    
    @Data
    private static class PageInfo {
        private Boolean hasNextPage;
        private String endCursor;
    }
    
    @Data
    private static class NodeResponse<T> {
        private T node;
    }
    
    @Data
    private static class MarketsResponse {
        private MarketConnection markets;
    }
    
    @Data
    private static class CatalogsResponse {
        private CatalogConnection catalogs;
    }
    
    @Data
    private static class PriceListsResponse {
        private PriceListConnection priceLists;
    }
    
    @Data
    private static class PriceListConnection {
        private List<PriceListEdge> edges;
        private PageInfo pageInfo;
    }
    
    @Data
    private static class PriceListEdge {
        private PriceList node;
        private String cursor;
    }
    
    @Data
    private static class MarketCreateResponse {
        private MarketCreateResult marketCreate;
    }
    
    @Data
    private static class MarketCreateResult {
        private Market market;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class MarketUpdateResponse {
        private MarketUpdateResult marketUpdate;
    }
    
    @Data
    private static class MarketUpdateResult {
        private Market market;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class MarketDeleteResponse {
        private MarketDeleteResult marketDelete;
    }
    
    @Data
    private static class MarketDeleteResult {
        private String deletedMarketId;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class CatalogCreateResponse {
        private CatalogCreateResult catalogCreate;
    }
    
    @Data
    private static class CatalogCreateResult {
        private Catalog catalog;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class CatalogUpdateResponse {
        private CatalogUpdateResult catalogUpdate;
    }
    
    @Data
    private static class CatalogUpdateResult {
        private Catalog catalog;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class CatalogDeleteResponse {
        private CatalogDeleteResult catalogDelete;
    }
    
    @Data
    private static class CatalogDeleteResult {
        private String deletedCatalogId;
        private List<UserError> userErrors;
    }
    
    // Enums
    public enum PriceListSortKeys {
        ID,
        NAME,
        RELEVANCE
    }
}