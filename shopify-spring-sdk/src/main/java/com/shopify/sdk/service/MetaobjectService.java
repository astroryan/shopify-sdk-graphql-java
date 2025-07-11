package com.shopify.sdk.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.shopify.sdk.model.graphql.GraphQLRequest;
import com.shopify.sdk.model.graphql.GraphQLResponse;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.metaobject.*;
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
public class MetaobjectService {
    
    private final ShopifyGraphQLClient graphQLClient;
    
    // Query for metaobject definitions
    private static final String GET_METAOBJECT_DEFINITIONS_QUERY = """
        query metaobjectDefinitions($first: Int!, $after: String) {
            metaobjectDefinitions(first: $first, after: $after) {
                edges {
                    node {
                        id
                        access {
                            admin
                            storefront
                        }
                        capabilities {
                            onlineStore {
                                data {
                                    templateSuffix
                                }
                                enabled
                            }
                            publishable {
                                enabled
                            }
                            renderable {
                                data {
                                    metaTitleKey
                                    metaDescriptionKey
                                }
                                enabled
                            }
                            translatable {
                                enabled
                            }
                        }
                        createdAt
                        description
                        displayNameKey
                        fieldDefinitions {
                            description
                            key
                            name
                            required
                            type {
                                name
                                category
                            }
                            validations {
                                name
                                type
                                value
                            }
                        }
                        fieldDefinitionsCount
                        hasThumbnailField
                        metaobjectsCount
                        name
                        thumbnailFieldKey
                        type
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
    
    // Query for single metaobject definition
    private static final String GET_METAOBJECT_DEFINITION_QUERY = """
        query metaobjectDefinitionByType($type: String!) {
            metaobjectDefinitionByType(type: $type) {
                id
                access {
                    admin
                    storefront
                }
                capabilities {
                    onlineStore {
                        data {
                            templateSuffix
                        }
                        enabled
                    }
                    publishable {
                        enabled
                    }
                    renderable {
                        data {
                            metaTitleKey
                            metaDescriptionKey
                        }
                        enabled
                    }
                    translatable {
                        enabled
                    }
                }
                createdAt
                description
                displayNameKey
                fieldDefinitions {
                    description
                    key
                    name
                    required
                    type {
                        name
                        category
                    }
                    validations {
                        name
                        type
                        value
                    }
                }
                fieldDefinitionsCount
                hasThumbnailField
                metaobjectsCount
                name
                thumbnailFieldKey
                type
                updatedAt
            }
        }
        """;
    
    // Query for metaobjects
    private static final String GET_METAOBJECTS_QUERY = """
        query metaobjects($type: String!, $first: Int!, $after: String, $query: String, $sortKey: String, $reverse: Boolean) {
            metaobjects(
                type: $type,
                first: $first,
                after: $after,
                query: $query,
                sortKey: $sortKey,
                reverse: $reverse
            ) {
                edges {
                    node {
                        id
                        createdAt
                        displayName
                        handle
                        type
                        updatedAt
                        fields {
                            key
                            type
                            value
                        }
                        capabilities {
                            onlineStore {
                                enabled
                            }
                            publishable {
                                enabled
                            }
                            renderable {
                                enabled
                            }
                            translatable {
                                enabled
                            }
                        }
                        onlineStoreUrl
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
    
    // Query for single metaobject
    private static final String GET_METAOBJECT_QUERY = """
        query metaobject($id: ID!) {
            metaobject(id: $id) {
                id
                createdAt
                definition {
                    id
                    name
                    type
                }
                displayName
                handle
                type
                updatedAt
                fields {
                    definition {
                        key
                        name
                        description
                    }
                    key
                    type
                    value
                }
                capabilities {
                    onlineStore {
                        enabled
                    }
                    publishable {
                        enabled
                    }
                    renderable {
                        enabled
                    }
                    translatable {
                        enabled
                    }
                }
                onlineStoreUrl
                seo {
                    title {
                        key
                        value
                    }
                    description {
                        key
                        value
                    }
                }
            }
        }
        """;
    
    // Query for metaobject by handle
    private static final String GET_METAOBJECT_BY_HANDLE_QUERY = """
        query metaobjectByHandle($handle: MetaobjectHandleInput!) {
            metaobjectByHandle(handle: $handle) {
                id
                createdAt
                displayName
                handle
                type
                updatedAt
                fields {
                    key
                    type
                    value
                }
                capabilities {
                    onlineStore {
                        enabled
                    }
                    publishable {
                        enabled
                    }
                    renderable {
                        enabled
                    }
                    translatable {
                        enabled
                    }
                }
                onlineStoreUrl
            }
        }
        """;
    
    // Mutation to create metaobject definition
    private static final String CREATE_METAOBJECT_DEFINITION_MUTATION = """
        mutation metaobjectDefinitionCreate($definition: MetaobjectDefinitionCreateInput!) {
            metaobjectDefinitionCreate(definition: $definition) {
                metaobjectDefinition {
                    id
                    name
                    type
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    // Mutation to update metaobject definition
    private static final String UPDATE_METAOBJECT_DEFINITION_MUTATION = """
        mutation metaobjectDefinitionUpdate($id: ID!, $definition: MetaobjectDefinitionUpdateInput!) {
            metaobjectDefinitionUpdate(id: $id, definition: $definition) {
                metaobjectDefinition {
                    id
                    name
                    type
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    // Mutation to delete metaobject definition
    private static final String DELETE_METAOBJECT_DEFINITION_MUTATION = """
        mutation metaobjectDefinitionDelete($id: ID!) {
            metaobjectDefinitionDelete(id: $id) {
                deletedId
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    // Mutation to create metaobject
    private static final String CREATE_METAOBJECT_MUTATION = """
        mutation metaobjectCreate($metaobject: MetaobjectCreateInput!) {
            metaobjectCreate(metaobject: $metaobject) {
                metaobject {
                    id
                    handle
                    type
                    displayName
                }
                userErrors {
                    field
                    message
                    code
                    elementIndex
                    elementKey
                }
            }
        }
        """;
    
    // Mutation to update metaobject
    private static final String UPDATE_METAOBJECT_MUTATION = """
        mutation metaobjectUpdate($id: ID!, $metaobject: MetaobjectUpdateInput!) {
            metaobjectUpdate(id: $id, metaobject: $metaobject) {
                metaobject {
                    id
                    handle
                    type
                    displayName
                }
                userErrors {
                    field
                    message
                    code
                    elementIndex
                    elementKey
                }
            }
        }
        """;
    
    // Mutation to upsert metaobject
    private static final String UPSERT_METAOBJECT_MUTATION = """
        mutation metaobjectUpsert($handle: MetaobjectHandleInput!, $metaobject: MetaobjectUpsertInput!) {
            metaobjectUpsert(handle: $handle, metaobject: $metaobject) {
                metaobject {
                    id
                    handle
                    type
                    displayName
                }
                userErrors {
                    field
                    message
                    code
                    elementIndex
                    elementKey
                }
            }
        }
        """;
    
    // Mutation to delete metaobject
    private static final String DELETE_METAOBJECT_MUTATION = """
        mutation metaobjectDelete($id: ID!) {
            metaobjectDelete(id: $id) {
                deletedId
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    // Mutation to bulk delete metaobjects
    private static final String BULK_DELETE_METAOBJECTS_MUTATION = """
        mutation metaobjectBulkDelete($where: MetaobjectBulkDeleteWhereCondition!) {
            metaobjectBulkDelete(where: $where) {
                job {
                    id
                    done
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
    public List<MetaobjectDefinition> getMetaobjectDefinitions(
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
                .query(GET_METAOBJECT_DEFINITIONS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetaobjectDefinitionsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MetaobjectDefinitionsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get metaobject definitions", response.getErrors());
        }
        
        List<MetaobjectDefinition> definitions = new ArrayList<>();
        response.getData().metaobjectDefinitions.edges.forEach(edge -> 
            definitions.add(edge.node)
        );
        
        return definitions;
    }
    
    public MetaobjectDefinition getMetaobjectDefinitionByType(
            ShopifyAuthContext context,
            String type) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("type", type);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_METAOBJECT_DEFINITION_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetaobjectDefinitionByTypeResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MetaobjectDefinitionByTypeResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get metaobject definition", response.getErrors());
        }
        
        return response.getData().metaobjectDefinitionByType;
    }
    
    public List<Metaobject> getMetaobjects(
            ShopifyAuthContext context,
            String type,
            int first,
            String after,
            String query,
            String sortKey,
            Boolean reverse) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("type", type);
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
                .query(GET_METAOBJECTS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetaobjectsResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MetaobjectsResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get metaobjects", response.getErrors());
        }
        
        List<Metaobject> metaobjects = new ArrayList<>();
        response.getData().metaobjects.edges.forEach(edge -> 
            metaobjects.add(edge.node)
        );
        
        return metaobjects;
    }
    
    public Metaobject getMetaobject(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_METAOBJECT_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetaobjectResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MetaobjectResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get metaobject", response.getErrors());
        }
        
        return response.getData().metaobject;
    }
    
    public Metaobject getMetaobjectByHandle(
            ShopifyAuthContext context,
            MetaobjectHandleInput handle) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("handle", handle);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_METAOBJECT_BY_HANDLE_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetaobjectByHandleResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MetaobjectByHandleResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to get metaobject by handle", response.getErrors());
        }
        
        return response.getData().metaobjectByHandle;
    }
    
    public MetaobjectDefinition createMetaobjectDefinition(
            ShopifyAuthContext context,
            MetaobjectDefinitionCreateInput definition) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("definition", definition);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_METAOBJECT_DEFINITION_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetaobjectDefinitionCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MetaobjectDefinitionCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create metaobject definition", response.getErrors());
        }
        
        if (response.getData().metaobjectDefinitionCreate.userErrors != null && 
            !response.getData().metaobjectDefinitionCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create metaobject definition",
                    response.getData().metaobjectDefinitionCreate.userErrors
            );
        }
        
        return response.getData().metaobjectDefinitionCreate.metaobjectDefinition;
    }
    
    public MetaobjectDefinition updateMetaobjectDefinition(
            ShopifyAuthContext context,
            String id,
            MetaobjectDefinitionUpdateInput definition) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        variables.put("definition", definition);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_METAOBJECT_DEFINITION_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetaobjectDefinitionUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MetaobjectDefinitionUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update metaobject definition", response.getErrors());
        }
        
        if (response.getData().metaobjectDefinitionUpdate.userErrors != null && 
            !response.getData().metaobjectDefinitionUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update metaobject definition",
                    response.getData().metaobjectDefinitionUpdate.userErrors
            );
        }
        
        return response.getData().metaobjectDefinitionUpdate.metaobjectDefinition;
    }
    
    public String deleteMetaobjectDefinition(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_METAOBJECT_DEFINITION_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetaobjectDefinitionDeleteResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MetaobjectDefinitionDeleteResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to delete metaobject definition", response.getErrors());
        }
        
        if (response.getData().metaobjectDefinitionDelete.userErrors != null && 
            !response.getData().metaobjectDefinitionDelete.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to delete metaobject definition",
                    response.getData().metaobjectDefinitionDelete.userErrors
            );
        }
        
        return response.getData().metaobjectDefinitionDelete.deletedId;
    }
    
    public Metaobject createMetaobject(
            ShopifyAuthContext context,
            MetaobjectCreateInput metaobject) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("metaobject", metaobject);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_METAOBJECT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetaobjectCreateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MetaobjectCreateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to create metaobject", response.getErrors());
        }
        
        if (response.getData().metaobjectCreate.userErrors != null && 
            !response.getData().metaobjectCreate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to create metaobject",
                    response.getData().metaobjectCreate.userErrors
            );
        }
        
        return response.getData().metaobjectCreate.metaobject;
    }
    
    public Metaobject updateMetaobject(
            ShopifyAuthContext context,
            String id,
            MetaobjectUpdateInput metaobject) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        variables.put("metaobject", metaobject);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_METAOBJECT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetaobjectUpdateResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MetaobjectUpdateResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to update metaobject", response.getErrors());
        }
        
        if (response.getData().metaobjectUpdate.userErrors != null && 
            !response.getData().metaobjectUpdate.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to update metaobject",
                    response.getData().metaobjectUpdate.userErrors
            );
        }
        
        return response.getData().metaobjectUpdate.metaobject;
    }
    
    public Metaobject upsertMetaobject(
            ShopifyAuthContext context,
            MetaobjectHandleInput handle,
            MetaobjectUpsertInput metaobject) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("handle", handle);
        variables.put("metaobject", metaobject);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPSERT_METAOBJECT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetaobjectUpsertResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MetaobjectUpsertResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to upsert metaobject", response.getErrors());
        }
        
        if (response.getData().metaobjectUpsert.userErrors != null && 
            !response.getData().metaobjectUpsert.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to upsert metaobject",
                    response.getData().metaobjectUpsert.userErrors
            );
        }
        
        return response.getData().metaobjectUpsert.metaobject;
    }
    
    public String deleteMetaobject(ShopifyAuthContext context, String id) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_METAOBJECT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetaobjectDeleteResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MetaobjectDeleteResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to delete metaobject", response.getErrors());
        }
        
        if (response.getData().metaobjectDelete.userErrors != null && 
            !response.getData().metaobjectDelete.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to delete metaobject",
                    response.getData().metaobjectDelete.userErrors
            );
        }
        
        return response.getData().metaobjectDelete.deletedId;
    }
    
    public BulkOperationJob bulkDeleteMetaobjects(
            ShopifyAuthContext context,
            MetaobjectBulkDeleteWhereCondition where) {
        ShopifyGraphQLClient.setAuthContext(context);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("where", where);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(BULK_DELETE_METAOBJECTS_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetaobjectBulkDeleteResponse> response = graphQLClient.execute(
                request,
                new TypeReference<GraphQLResponse<MetaobjectBulkDeleteResponse>>() {}
        );
        
        if (response.hasErrors()) {
            throw new ShopifyApiException("Failed to bulk delete metaobjects", response.getErrors());
        }
        
        if (response.getData().metaobjectBulkDelete.userErrors != null && 
            !response.getData().metaobjectBulkDelete.userErrors.isEmpty()) {
            throw new ShopifyApiException(
                    "Failed to bulk delete metaobjects",
                    response.getData().metaobjectBulkDelete.userErrors
            );
        }
        
        return response.getData().metaobjectBulkDelete.job;
    }
    
    // Input classes
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MetaobjectHandleInput {
        private String handle;
        private String type;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MetaobjectBulkDeleteWhereCondition {
        private List<String> ids;
        private String type;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BulkOperationJob {
        private String id;
        private Boolean done;
    }
    
    // Response classes
    @Data
    private static class MetaobjectDefinitionsResponse {
        private MetaobjectDefinitionConnection metaobjectDefinitions;
    }
    
    @Data
    private static class MetaobjectDefinitionConnection {
        private List<MetaobjectDefinitionEdge> edges;
        private PageInfo pageInfo;
    }
    
    @Data
    private static class MetaobjectDefinitionEdge {
        private MetaobjectDefinition node;
        private String cursor;
    }
    
    @Data
    private static class PageInfo {
        private Boolean hasNextPage;
        private String endCursor;
    }
    
    @Data
    private static class MetaobjectDefinitionByTypeResponse {
        private MetaobjectDefinition metaobjectDefinitionByType;
    }
    
    @Data
    private static class MetaobjectsResponse {
        private MetaobjectConnection metaobjects;
    }
    
    @Data
    private static class MetaobjectResponse {
        private Metaobject metaobject;
    }
    
    @Data
    private static class MetaobjectByHandleResponse {
        private Metaobject metaobjectByHandle;
    }
    
    @Data
    private static class MetaobjectDefinitionCreateResponse {
        private MetaobjectDefinitionCreateResult metaobjectDefinitionCreate;
    }
    
    @Data
    private static class MetaobjectDefinitionCreateResult {
        private MetaobjectDefinition metaobjectDefinition;
        private List<MetaobjectUserError> userErrors;
    }
    
    @Data
    private static class MetaobjectDefinitionUpdateResponse {
        private MetaobjectDefinitionUpdateResult metaobjectDefinitionUpdate;
    }
    
    @Data
    private static class MetaobjectDefinitionUpdateResult {
        private MetaobjectDefinition metaobjectDefinition;
        private List<MetaobjectUserError> userErrors;
    }
    
    @Data
    private static class MetaobjectDefinitionDeleteResponse {
        private MetaobjectDefinitionDeleteResult metaobjectDefinitionDelete;
    }
    
    @Data
    private static class MetaobjectDefinitionDeleteResult {
        private String deletedId;
        private List<MetaobjectUserError> userErrors;
    }
    
    @Data
    private static class MetaobjectCreateResponse {
        private MetaobjectCreateResult metaobjectCreate;
    }
    
    @Data
    private static class MetaobjectCreateResult {
        private Metaobject metaobject;
        private List<MetaobjectUserError> userErrors;
    }
    
    @Data
    private static class MetaobjectUpdateResponse {
        private MetaobjectUpdateResult metaobjectUpdate;
    }
    
    @Data
    private static class MetaobjectUpdateResult {
        private Metaobject metaobject;
        private List<MetaobjectUserError> userErrors;
    }
    
    @Data
    private static class MetaobjectUpsertResponse {
        private MetaobjectUpsertResult metaobjectUpsert;
    }
    
    @Data
    private static class MetaobjectUpsertResult {
        private Metaobject metaobject;
        private List<MetaobjectUserError> userErrors;
    }
    
    @Data
    private static class MetaobjectDeleteResponse {
        private MetaobjectDeleteResult metaobjectDelete;
    }
    
    @Data
    private static class MetaobjectDeleteResult {
        private String deletedId;
        private List<MetaobjectUserError> userErrors;
    }
    
    @Data
    private static class MetaobjectBulkDeleteResponse {
        private MetaobjectBulkDeleteResult metaobjectBulkDelete;
    }
    
    @Data
    private static class MetaobjectBulkDeleteResult {
        private BulkOperationJob job;
        private List<MetaobjectUserError> userErrors;
    }
}