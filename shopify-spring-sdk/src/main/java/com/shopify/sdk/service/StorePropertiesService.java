package com.shopify.sdk.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.shopify.sdk.model.graphql.GraphQLRequest;
import com.shopify.sdk.model.graphql.GraphQLResponse;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.common.UserError;
import com.shopify.sdk.model.shop.*;
import com.shopify.sdk.model.storefront.StorefrontAccessTokenInput;
import com.shopify.sdk.model.storefront.StorefrontAccessToken;
import com.shopify.sdk.model.store.StorefrontAccessTokenConnection;
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
public class StorePropertiesService {
    
    private final ShopifyGraphQLClient graphQLClient;
    
    // Query for shop details
    private static final String GET_SHOP_QUERY = """
        query shop {
            shop {
                id
                name
                email
                description
                contactEmail
                currencyCode
                currencyFormats {
                    moneyFormat
                    moneyInEmailsFormat
                    moneyWithCurrencyFormat
                    moneyWithCurrencyInEmailsFormat
                }
                customerAccounts
                enabledCurrencies
                enabledLocales {
                    locale
                    name
                    primary
                    published
                }
                features {
                    avalaraAvatax
                    branding
                    bundles
                    captcha
                    captchaExternalDomains
                    deliveryProfiles
                    dynamicRemarketing
                    eligibleForSubscriptionMigration
                    eligibleForSubscriptions
                    giftCards
                    harmonizedSystemCode
                    internationalDomains
                    internationalPriceOverrides
                    internationalPriceRules
                    legacySubscriptionGatewayEnabled
                    liveView
                    multiLocation
                    onboardingVisual
                    productPublishing
                    reports
                    sellsSubscriptions
                    showMetrics
                    storefront
                    usingShopifyBalance
                }
                ianaTimezone
                myshopifyDomain
                orderNumberFormatPrefix
                orderNumberFormatSuffix
                paymentSettings {
                    supportedDigitalWallets
                }
                plan {
                    displayName
                    partnerDevelopment
                    shopifyPlus
                }
                primaryDomain {
                    id
                    host
                    sslEnabled
                    url
                }
                publicationCount
                resourceLimits {
                    locationLimit
                    maxProductOptions
                    maxProductVariants
                    redirectLimitReached
                    skuResourceLimits {
                        available
                        quantityAvailable
                        quantityLimit
                        quantityUsed
                    }
                }
                richTextEditorUrl
                setupRequired
                shipsToCountries
                shopOwnerName
                taxShipping
                taxesIncluded
                timezone
                transactionalSmsDisabled
                unitSystem
                updatedAt
                url
                weightUnit
            }
        }
        """;
    
    // Query for shop domains
    private static final String GET_DOMAINS_QUERY = """
        query domains($first: Int!, $after: String) {
            domains(first: $first, after: $after) {
                edges {
                    node {
                        id
                        host
                        localization {
                            alternateLocales
                            country
                            defaultLocale
                        }
                        sslEnabled
                        url
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
    
    // Query for shop policies
    private static final String GET_SHOP_POLICIES_QUERY = """
        query shopPolicies {
            shop {
                privacyPolicy {
                    id
                    body
                    createdAt
                    handle
                    title
                    type
                    updatedAt
                    url
                }
                refundPolicy {
                    id
                    body
                    createdAt
                    handle
                    title
                    type
                    updatedAt
                    url
                }
                shippingPolicy {
                    id
                    body
                    createdAt
                    handle
                    title
                    type
                    updatedAt
                    url
                }
                subscriptionPolicy {
                    id
                    body
                    createdAt
                    handle
                    title
                    type
                    updatedAt
                    url
                }
                termsOfService {
                    id
                    body
                    createdAt
                    handle
                    title
                    type
                    updatedAt
                    url
                }
            }
        }
        """;
    
    // Query for storefront access tokens
    private static final String GET_STOREFRONT_ACCESS_TOKENS_QUERY = """
        query storefrontAccessTokens($first: Int!, $after: String) {
            shop {
                storefrontAccessTokens(first: $first, after: $after) {
                    edges {
                        node {
                            id
                            accessScopes {
                                handle
                                description
                            }
                            accessToken
                            createdAt
                            title
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
        }
        """;
    
    // Query for shop localizations
    private static final String GET_SHOP_LOCALIZATIONS_QUERY = """
        query shopLocalizations($published: Boolean) {
            shopLocalizations(published: $published) {
                locale
                market {
                    id
                    currencySettings {
                        baseCurrency
                        localCurrency
                    }
                    enabled
                    name
                    primary
                    webPresence {
                        id
                        alternateLocales
                        defaultLocale
                        domain {
                            id
                            host
                            url
                        }
                        rootUrls {
                            locale
                            url
                        }
                        subfolderSuffix
                    }
                }
                name
                published
            }
        }
        """;
    
    // Query for available locales
    private static final String GET_AVAILABLE_LOCALES_QUERY = """
        query availableLocales {
            shopLocales {
                isoCode
                name
                primary
                published
            }
        }
        """;
    
    // Mutation to update shop
    private static final String UPDATE_SHOP_MUTATION = """
        mutation shopUpdate($input: StorePropertiesInput!) {
            shopUpdate(input: $input) {
                shop {
                    id
                    name
                    email
                    description
                    currencyCode
                    customerAccounts
                    ianaTimezone
                    unitSystem
                    weightUnit
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to create shop policy
    private static final String CREATE_SHOP_POLICY_MUTATION = """
        mutation shopPolicyCreate($input: ShopPolicyInput!) {
            shopPolicyCreate(input: $input) {
                shopPolicy {
                    id
                    body
                    handle
                    title
                    type
                    url
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to update shop policy
    private static final String UPDATE_SHOP_POLICY_MUTATION = """
        mutation shopPolicyUpdate($input: ShopPolicyUpdateInput!) {
            shopPolicyUpdate(input: $input) {
                shopPolicy {
                    id
                    body
                    handle
                    title
                    type
                    url
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to delete shop policy
    private static final String DELETE_SHOP_POLICY_MUTATION = """
        mutation shopPolicyDelete($id: ID!) {
            shopPolicyDelete(id: $id) {
                deletedShopPolicyId
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to create storefront access token
    private static final String CREATE_STOREFRONT_ACCESS_TOKEN_MUTATION = """
        mutation storefrontAccessTokenCreate($input: StorefrontAccessTokenInput!) {
            storefrontAccessTokenCreate(input: $input) {
                storefrontAccessToken {
                    id
                    accessScopes {
                        handle
                        description
                    }
                    accessToken
                    createdAt
                    title
                    updatedAt
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to delete storefront access token
    private static final String DELETE_STOREFRONT_ACCESS_TOKEN_MUTATION = """
        mutation storefrontAccessTokenDelete($id: ID!) {
            storefrontAccessTokenDelete(id: $id) {
                deletedStorefrontAccessTokenId
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to enable shop locale
    private static final String ENABLE_SHOP_LOCALE_MUTATION = """
        mutation shopLocaleEnable($locale: String!) {
            shopLocaleEnable(locale: $locale) {
                shopLocale {
                    locale
                    name
                    primary
                    published
                }
                userErrors {
                    field
                    message
                    locale
                }
            }
        }
        """;
    
    // Mutation to disable shop locale
    private static final String DISABLE_SHOP_LOCALE_MUTATION = """
        mutation shopLocaleDisable($locale: String!) {
            shopLocaleDisable(locale: $locale) {
                disabledLocale
                userErrors {
                    field
                    message
                    locale
                }
            }
        }
        """;
    
    // Mutation to update shop locale
    private static final String UPDATE_SHOP_LOCALE_MUTATION = """
        mutation shopLocaleUpdate($locale: String!, $published: Boolean!) {
            shopLocaleUpdate(locale: $locale, shopLocale: {published: $published}) {
                shopLocale {
                    locale
                    name
                    primary
                    published
                }
                userErrors {
                    field
                    message
                    locale
                }
            }
        }
        """;
    
    // Service methods
    public Shop getShop(ShopifyAuthContext context) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_SHOP_QUERY)
                .variables(new HashMap<>())
                .build();
        
        GraphQLResponse<ShopResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ShopResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get shop", response.getErrors());
        }
        
        return response.getData().shop;
    }
    
    public List<Domain> getDomains(ShopifyAuthContext context, int first, String after) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_DOMAINS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<DomainsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<DomainsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get domains", response.getErrors());
        }
        
        List<Domain> domains = new ArrayList<>();
        response.getData().domains.edges.forEach(edge -> 
            domains.add(edge.node)
        );
        
        return domains;
    }
    
    public ShopPolicies getShopPolicies(ShopifyAuthContext context) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_SHOP_POLICIES_QUERY)
                .variables(new HashMap<>())
                .build();
        
        GraphQLResponse<ShopPoliciesResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ShopPoliciesResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get shop policies", response.getErrors());
        }
        
        return ShopPolicies.builder()
                .privacyPolicy(response.getData().shop.privacyPolicy)
                .refundPolicy(response.getData().shop.refundPolicy)
                .shippingPolicy(response.getData().shop.shippingPolicy)
                .subscriptionPolicy(response.getData().shop.subscriptionPolicy)
                .termsOfService(response.getData().shop.termsOfService)
                .build();
    }
    
    public List<StorefrontAccessToken> getStorefrontAccessTokens(
            ShopifyAuthContext context,
            int first,
            String after) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_STOREFRONT_ACCESS_TOKENS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<StorefrontAccessTokensResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<StorefrontAccessTokensResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get storefront access tokens", response.getErrors());
        }
        
        List<StorefrontAccessToken> tokens = new ArrayList<>();
        response.getData().shop.storefrontAccessTokens.edges.forEach(edge -> 
            tokens.add(edge.node)
        );
        
        return tokens;
    }
    
    public List<ShopLocalization> getShopLocalizations(
            ShopifyAuthContext context,
            Boolean published) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        if (published != null) {
            variables.put("published", published);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_SHOP_LOCALIZATIONS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<ShopLocalizationsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ShopLocalizationsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get shop localizations", response.getErrors());
        }
        
        return response.getData().shopLocalizations;
    }
    
    public List<ShopLocale> getAvailableLocales(ShopifyAuthContext context) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_AVAILABLE_LOCALES_QUERY)
                .variables(new HashMap<>())
                .build();
        
        GraphQLResponse<AvailableLocalesResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<AvailableLocalesResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get available locales", response.getErrors());
        }
        
        return response.getData().shopLocales;
    }
    
    public Shop updateShop(ShopifyAuthContext context, StorePropertiesInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_SHOP_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<ShopUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ShopUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update shop", response.getErrors());
        }
        
        if (response.getData().shopUpdate.userErrors != null && 
            !response.getData().shopUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update shop",
                    response.getData().shopUpdate.userErrors
            );
        }
        
        return response.getData().shopUpdate.shop;
    }
    
    public ShopPolicy createShopPolicy(ShopifyAuthContext context, ShopPolicyInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_SHOP_POLICY_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<ShopPolicyCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ShopPolicyCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create shop policy", response.getErrors());
        }
        
        if (response.getData().shopPolicyCreate.userErrors != null && 
            !response.getData().shopPolicyCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create shop policy",
                    response.getData().shopPolicyCreate.userErrors
            );
        }
        
        return response.getData().shopPolicyCreate.shopPolicy;
    }
    
    public ShopPolicy updateShopPolicy(ShopifyAuthContext context, ShopPolicyUpdateInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_SHOP_POLICY_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<ShopPolicyUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ShopPolicyUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update shop policy", response.getErrors());
        }
        
        if (response.getData().shopPolicyUpdate.userErrors != null && 
            !response.getData().shopPolicyUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update shop policy",
                    response.getData().shopPolicyUpdate.userErrors
            );
        }
        
        return response.getData().shopPolicyUpdate.shopPolicy;
    }
    
    public String deleteShopPolicy(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_SHOP_POLICY_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<ShopPolicyDeleteResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ShopPolicyDeleteResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to delete shop policy", response.getErrors());
        }
        
        if (response.getData().shopPolicyDelete.userErrors != null && 
            !response.getData().shopPolicyDelete.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to delete shop policy",
                    response.getData().shopPolicyDelete.userErrors
            );
        }
        
        return response.getData().shopPolicyDelete.deletedShopPolicyId;
    }
    
    public StorefrontAccessToken createStorefrontAccessToken(
            ShopifyAuthContext context,
            StorefrontAccessTokenInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_STOREFRONT_ACCESS_TOKEN_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<StorefrontAccessTokenCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<StorefrontAccessTokenCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create storefront access token", response.getErrors());
        }
        
        if (response.getData().storefrontAccessTokenCreate.userErrors != null && 
            !response.getData().storefrontAccessTokenCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create storefront access token",
                    response.getData().storefrontAccessTokenCreate.userErrors
            );
        }
        
        return response.getData().storefrontAccessTokenCreate.storefrontAccessToken;
    }
    
    public String deleteStorefrontAccessToken(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_STOREFRONT_ACCESS_TOKEN_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<StorefrontAccessTokenDeleteResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<StorefrontAccessTokenDeleteResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to delete storefront access token", response.getErrors());
        }
        
        if (response.getData().storefrontAccessTokenDelete.userErrors != null && 
            !response.getData().storefrontAccessTokenDelete.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to delete storefront access token",
                    response.getData().storefrontAccessTokenDelete.userErrors
            );
        }
        
        return response.getData().storefrontAccessTokenDelete.deletedStorefrontAccessTokenId;
    }
    
    public ShopLocale enableShopLocale(ShopifyAuthContext context, String locale) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("locale", locale);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(ENABLE_SHOP_LOCALE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<ShopLocaleEnableResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ShopLocaleEnableResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to enable shop locale", response.getErrors());
        }
        
        if (response.getData().shopLocaleEnable.userErrors != null && 
            !response.getData().shopLocaleEnable.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to enable shop locale",
                    response.getData().shopLocaleEnable.userErrors
            );
        }
        
        return response.getData().shopLocaleEnable.shopLocale;
    }
    
    public String disableShopLocale(ShopifyAuthContext context, String locale) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("locale", locale);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DISABLE_SHOP_LOCALE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<ShopLocaleDisableResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ShopLocaleDisableResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to disable shop locale", response.getErrors());
        }
        
        if (response.getData().shopLocaleDisable.userErrors != null && 
            !response.getData().shopLocaleDisable.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to disable shop locale",
                    response.getData().shopLocaleDisable.userErrors
            );
        }
        
        return response.getData().shopLocaleDisable.disabledLocale;
    }
    
    public ShopLocale updateShopLocale(
            ShopifyAuthContext context,
            String locale,
            boolean published) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("locale", locale);
        variables.put("published", published);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_SHOP_LOCALE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<ShopLocaleUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ShopLocaleUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update shop locale", response.getErrors());
        }
        
        if (response.getData().shopLocaleUpdate.userErrors != null && 
            !response.getData().shopLocaleUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update shop locale",
                    response.getData().shopLocaleUpdate.userErrors
            );
        }
        
        return response.getData().shopLocaleUpdate.shopLocale;
    }
    
    // Response classes
    @Data
    private static class ShopResponse {
        private Shop shop;
    }
    
    @Data
    private static class DomainsResponse {
        private DomainConnection domains;
    }
    
    @Data
    private static class ShopPoliciesResponse {
        private ShopPoliciesData shop;
    }
    
    @Data
    private static class ShopPoliciesData {
        private ShopPolicy privacyPolicy;
        private ShopPolicy refundPolicy;
        private ShopPolicy shippingPolicy;
        private ShopPolicy subscriptionPolicy;
        private ShopPolicy termsOfService;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShopPolicies {
        private ShopPolicy privacyPolicy;
        private ShopPolicy refundPolicy;
        private ShopPolicy shippingPolicy;
        private ShopPolicy subscriptionPolicy;
        private ShopPolicy termsOfService;
    }
    
    @Data
    private static class StorefrontAccessTokensResponse {
        private StorefrontAccessTokensData shop;
    }
    
    @Data
    private static class StorefrontAccessTokensData {
        private StorefrontAccessTokenConnection storefrontAccessTokens;
    }
    
    @Data
    private static class ShopLocalizationsResponse {
        private List<ShopLocalization> shopLocalizations;
    }
    
    @Data
    private static class AvailableLocalesResponse {
        private List<ShopLocale> shopLocales;
    }
    
    @Data
    private static class ShopUpdateResponse {
        private ShopUpdateResult shopUpdate;
    }
    
    @Data
    private static class ShopUpdateResult {
        private Shop shop;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class ShopPolicyCreateResponse {
        private ShopPolicyCreateResult shopPolicyCreate;
    }
    
    @Data
    private static class ShopPolicyCreateResult {
        private ShopPolicy shopPolicy;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class ShopPolicyUpdateResponse {
        private ShopPolicyUpdateResult shopPolicyUpdate;
    }
    
    @Data
    private static class ShopPolicyUpdateResult {
        private ShopPolicy shopPolicy;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class ShopPolicyDeleteResponse {
        private ShopPolicyDeleteResult shopPolicyDelete;
    }
    
    @Data
    private static class ShopPolicyDeleteResult {
        private String deletedShopPolicyId;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class StorefrontAccessTokenCreateResponse {
        private StorefrontAccessTokenCreateResult storefrontAccessTokenCreate;
    }
    
    @Data
    private static class StorefrontAccessTokenCreateResult {
        private StorefrontAccessToken storefrontAccessToken;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class StorefrontAccessTokenDeleteResponse {
        private StorefrontAccessTokenDeleteResult storefrontAccessTokenDelete;
    }
    
    @Data
    private static class StorefrontAccessTokenDeleteResult {
        private String deletedStorefrontAccessTokenId;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class ShopLocaleEnableResponse {
        private ShopLocaleEnableResult shopLocaleEnable;
    }
    
    @Data
    private static class ShopLocaleEnableResult {
        private ShopLocale shopLocale;
        private List<ShopLocaleUserError> userErrors;
    }
    
    @Data
    private static class ShopLocaleDisableResponse {
        private ShopLocaleDisableResult shopLocaleDisable;
    }
    
    @Data
    private static class ShopLocaleDisableResult {
        private String disabledLocale;
        private List<ShopLocaleUserError> userErrors;
    }
    
    @Data
    private static class ShopLocaleUpdateResponse {
        private ShopLocaleUpdateResult shopLocaleUpdate;
    }
    
    @Data
    private static class ShopLocaleUpdateResult {
        private ShopLocale shopLocale;
        private List<ShopLocaleUserError> userErrors;
    }
    
    @Data
    private static class ShopLocaleUserError {
        private List<String> field;
        private String message;
        private String locale;
    }
}