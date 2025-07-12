package com.shopify.sdk.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.shopify.sdk.model.graphql.GraphQLRequest;
import com.shopify.sdk.model.graphql.GraphQLResponse;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.common.UserError;
import com.shopify.sdk.model.common.Country;
import com.shopify.sdk.model.common.Language;
import com.shopify.sdk.model.localization.*;
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
public class LocalizationService {
    
    private final ShopifyGraphQLClient graphQLClient;
    
    // Query for shop locales
    private static final String GET_SHOP_LOCALES_QUERY = """
        query shopLocales {
            shopLocales {
                locale
                name
                primary
                published
            }
        }
        """;
    
    // Query for available locales
    private static final String GET_AVAILABLE_LOCALES_QUERY = """
        query localization {
            localization {
                availableCountries {
                    currency {
                        isoCode
                        name
                        symbol
                    }
                    isoCode
                    name
                    unitSystem
                }
                availableLanguages {
                    endonymName
                    isoCode
                    name
                }
                country {
                    currency {
                        isoCode
                        name
                        symbol
                    }
                    isoCode
                    name
                    unitSystem
                }
                language {
                    endonymName
                    isoCode
                    name
                }
            }
        }
        """;
    
    // Query for translatable resources
    private static final String GET_TRANSLATABLE_RESOURCES_QUERY = """
        query translatableResources($first: Int!, $after: String, $resourceType: TranslatableResourceType!) {
            translatableResources(first: $first, after: $after, resourceType: $resourceType) {
                edges {
                    node {
                        resourceId
                        translatableContent {
                            digest
                            key
                            locale
                            type
                            value
                        }
                        translations(locale: $locale) {
                            key
                            locale
                            outdated
                            updatedAt
                            value
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
    
    // Query for single translatable resource
    private static final String GET_TRANSLATABLE_RESOURCE_QUERY = """
        query translatableResource($resourceId: ID!) {
            translatableResource(resourceId: $resourceId) {
                resourceId
                translatableContent {
                    digest
                    key
                    locale
                    type
                    value
                }
                translations(locale: $locale) {
                    key
                    locale
                    outdated
                    updatedAt
                    value
                }
            }
        }
        """;
    
    // Query for translations by resource
    private static final String GET_TRANSLATIONS_QUERY = """
        query translatableResourcesByIds($resourceIds: [ID!]!, $locale: String!) {
            translatableResourcesByIds(resourceIds: $resourceIds, first: 50) {
                edges {
                    node {
                        resourceId
                        translations(locale: $locale) {
                            key
                            locale
                            outdated
                            updatedAt
                            value
                        }
                    }
                }
            }
        }
        """;
    
    // Mutation to enable locale
    private static final String ENABLE_LOCALE_MUTATION = """
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
                }
            }
        }
        """;
    
    // Mutation to disable locale
    private static final String DISABLE_LOCALE_MUTATION = """
        mutation shopLocaleDisable($locale: String!) {
            shopLocaleDisable(locale: $locale) {
                locale
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to update locale
    private static final String UPDATE_LOCALE_MUTATION = """
        mutation shopLocaleUpdate($locale: String!, $shopLocale: ShopLocaleInput!) {
            shopLocaleUpdate(locale: $locale, shopLocale: $shopLocale) {
                shopLocale {
                    locale
                    name
                    primary
                    published
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    // Mutation to register translations
    private static final String REGISTER_TRANSLATIONS_MUTATION = """
        mutation translationsRegister($resourceId: ID!, $translations: [TranslationInput!]!) {
            translationsRegister(resourceId: $resourceId, translations: $translations) {
                translations {
                    key
                    locale
                    outdated
                    updatedAt
                    value
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    // Mutation to remove translations
    private static final String REMOVE_TRANSLATIONS_MUTATION = """
        mutation translationsRemove($resourceId: ID!, $locales: [String!]!, $translationKeys: [String!]!) {
            translationsRemove(
                resourceId: $resourceId,
                locales: $locales,
                translationKeys: $translationKeys
            ) {
                translations {
                    key
                    locale
                    outdated
                    updatedAt
                    value
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
    public List<ShopLocale> getShopLocales(ShopifyAuthContext context) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_SHOP_LOCALES_QUERY)
                .variables(new HashMap<>())
                .build();
        
        GraphQLResponse<ShopLocalesResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ShopLocalesResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get shop locales", response.getErrors());
        }
        
        return response.getData().shopLocales;
    }
    
    public LocalizationContext getLocalizationContext(ShopifyAuthContext context) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_AVAILABLE_LOCALES_QUERY)
                .variables(new HashMap<>())
                .build();
        
        GraphQLResponse<LocalizationResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<LocalizationResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get localization context", response.getErrors());
        }
        
        return response.getData().localization;
    }
    
    public List<TranslatableResource> getTranslatableResources(
            ShopifyAuthContext context,
            TranslatableResourceType resourceType,
            String locale,
            int first,
            String after) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("resourceType", resourceType);
        variables.put("locale", locale);
        variables.put("first", first);
        if (after != null) {
            variables.put("after", after);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_TRANSLATABLE_RESOURCES_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<TranslatableResourcesResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<TranslatableResourcesResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get translatable resources", response.getErrors());
        }
        
        List<TranslatableResource> resources = new ArrayList<>();
        response.getData().translatableResources.edges.forEach(edge -> 
            resources.add(edge.node)
        );
        
        return resources;
    }
    
    public TranslatableResource getTranslatableResource(
            ShopifyAuthContext context,
            String resourceId,
            String locale) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("resourceId", resourceId);
        variables.put("locale", locale);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_TRANSLATABLE_RESOURCE_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<TranslatableResourceResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<TranslatableResourceResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get translatable resource", response.getErrors());
        }
        
        return response.getData().translatableResource;
    }
    
    public List<TranslatableResource> getTranslationsByResourceIds(
            ShopifyAuthContext context,
            List<String> resourceIds,
            String locale) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("resourceIds", resourceIds);
        variables.put("locale", locale);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_TRANSLATIONS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<TranslatableResourcesByIdsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<TranslatableResourcesByIdsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get translations", response.getErrors());
        }
        
        List<TranslatableResource> resources = new ArrayList<>();
        response.getData().translatableResourcesByIds.edges.forEach(edge -> 
            resources.add(edge.node)
        );
        
        return resources;
    }
    
    public ShopLocale enableLocale(ShopifyAuthContext context, String locale) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("locale", locale);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(ENABLE_LOCALE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<ShopLocaleEnableResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ShopLocaleEnableResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to enable locale", response.getErrors());
        }
        
        if (response.getData().shopLocaleEnable.userErrors != null && 
            !response.getData().shopLocaleEnable.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to enable locale",
                    response.getData().shopLocaleEnable.userErrors
            );
        }
        
        return response.getData().shopLocaleEnable.shopLocale;
    }
    
    public String disableLocale(ShopifyAuthContext context, String locale) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("locale", locale);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DISABLE_LOCALE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<ShopLocaleDisableResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ShopLocaleDisableResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to disable locale", response.getErrors());
        }
        
        if (response.getData().shopLocaleDisable.userErrors != null && 
            !response.getData().shopLocaleDisable.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to disable locale",
                    response.getData().shopLocaleDisable.userErrors
            );
        }
        
        return response.getData().shopLocaleDisable.locale;
    }
    
    public ShopLocale updateLocale(
            ShopifyAuthContext context,
            String locale,
            ShopLocaleInput input) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("locale", locale);
        variables.put("shopLocale", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_LOCALE_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<ShopLocaleUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<ShopLocaleUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update locale", response.getErrors());
        }
        
        if (response.getData().shopLocaleUpdate.userErrors != null && 
            !response.getData().shopLocaleUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update locale",
                    response.getData().shopLocaleUpdate.userErrors
            );
        }
        
        return response.getData().shopLocaleUpdate.shopLocale;
    }
    
    public List<Translation> registerTranslations(
            ShopifyAuthContext context,
            String resourceId,
            List<TranslationInput> translations) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("resourceId", resourceId);
        variables.put("translations", translations);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(REGISTER_TRANSLATIONS_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<TranslationsRegisterResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<TranslationsRegisterResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to register translations", response.getErrors());
        }
        
        if (response.getData().translationsRegister.userErrors != null && 
            !response.getData().translationsRegister.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to register translations",
                    response.getData().translationsRegister.userErrors
            );
        }
        
        return response.getData().translationsRegister.translations;
    }
    
    public List<Translation> removeTranslations(
            ShopifyAuthContext context,
            String resourceId,
            List<String> locales,
            List<String> translationKeys) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("resourceId", resourceId);
        variables.put("locales", locales);
        variables.put("translationKeys", translationKeys);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(REMOVE_TRANSLATIONS_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<TranslationsRemoveResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<TranslationsRemoveResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to remove translations", response.getErrors());
        }
        
        if (response.getData().translationsRemove.userErrors != null && 
            !response.getData().translationsRemove.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to remove translations",
                    response.getData().translationsRemove.userErrors
            );
        }
        
        return response.getData().translationsRemove.translations;
    }
    
    // Input classes
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShopLocaleInput {
        private Boolean published;
    }
    
    // Response classes
    @Data
    private static class ShopLocalesResponse {
        private List<ShopLocale> shopLocales;
    }
    
    @Data
    private static class LocalizationResponse {
        private LocalizationContext localization;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LocalizationContext {
        private List<Country> availableCountries;
        private List<Language> availableLanguages;
        private Country country;
        private Language language;
    }
    
    @Data
    private static class TranslatableResourcesResponse {
        private TranslatableResourceConnection translatableResources;
    }
    
    @Data
    private static class TranslatableResourceResponse {
        private TranslatableResource translatableResource;
    }
    
    @Data
    private static class TranslatableResourcesByIdsResponse {
        private TranslatableResourceConnection translatableResourcesByIds;
    }
    
    @Data
    private static class ShopLocaleEnableResponse {
        private ShopLocaleEnableResult shopLocaleEnable;
    }
    
    @Data
    private static class ShopLocaleEnableResult {
        private ShopLocale shopLocale;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class ShopLocaleDisableResponse {
        private ShopLocaleDisableResult shopLocaleDisable;
    }
    
    @Data
    private static class ShopLocaleDisableResult {
        private String locale;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class ShopLocaleUpdateResponse {
        private ShopLocaleUpdateResult shopLocaleUpdate;
    }
    
    @Data
    private static class ShopLocaleUpdateResult {
        private ShopLocale shopLocale;
        private List<UserError> userErrors;
    }
    
    @Data
    private static class TranslationsRegisterResponse {
        private TranslationsRegisterResult translationsRegister;
    }
    
    @Data
    private static class TranslationsRegisterResult {
        private List<Translation> translations;
        private List<TranslationUserError> userErrors;
    }
    
    @Data
    private static class TranslationsRemoveResponse {
        private TranslationsRemoveResult translationsRemove;
    }
    
    @Data
    private static class TranslationsRemoveResult {
        private List<Translation> translations;
        private List<TranslationUserError> userErrors;
    }
}