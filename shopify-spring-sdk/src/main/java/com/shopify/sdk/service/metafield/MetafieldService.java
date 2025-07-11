package com.shopify.sdk.service.metafield;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.GraphQLClient;
import com.shopify.sdk.model.metafield.*;
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
public class MetafieldService {
    
    private final GraphQLClient graphQLClient;
    
    private static final String GET_METAFIELD_QUERY = """
        query getMetafield($id: ID!) {
            metafield(id: $id) {
                id
                createdAt
                definition {
                    id
                    key
                    namespace
                    name
                    description
                    type {
                        name
                        category
                    }
                    ownerType
                    validationStatus
                    visibleToStorefrontApi
                }
                description
                key
                legacyResourceId
                namespace
                owner {
                    __typename
                    ... on Node {
                        id
                    }
                }
                ownerType
                type
                updatedAt
                value
            }
        }
        """;
    
    private static final String LIST_METAFIELDS_QUERY = """
        query listMetafields($first: Int!, $after: String, $namespace: String, $ownerType: MetafieldOwnerType, $key: String) {
            metafields(first: $first, after: $after, namespace: $namespace, ownerType: $ownerType, key: $key) {
                edges {
                    node {
                        id
                        createdAt
                        key
                        namespace
                        ownerType
                        type
                        updatedAt
                        value
                        definition {
                            id
                            name
                            description
                        }
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
    
    private static final String LIST_METAFIELD_DEFINITIONS_QUERY = """
        query listMetafieldDefinitions($first: Int!, $after: String, $ownerType: MetafieldOwnerType, $namespace: String, $pinnedStatus: MetafieldDefinitionPinnedStatus) {
            metafieldDefinitions(first: $first, after: $after, ownerType: $ownerType, namespace: $namespace, pinnedStatus: $pinnedStatus) {
                edges {
                    node {
                        id
                        key
                        namespace
                        name
                        description
                        ownerType
                        pinnedPosition
                        type {
                            name
                            category
                        }
                        validationStatus
                        visibleToStorefrontApi
                        useAsCollectionCondition
                        validations {
                            name
                            type
                            value
                        }
                        metafieldsCount
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
    
    private static final String CREATE_METAFIELD_MUTATION = """
        mutation metafieldsSet($metafields: [MetafieldsSetInput!]!) {
            metafieldsSet(metafields: $metafields) {
                metafields {
                    id
                    createdAt
                    key
                    namespace
                    ownerType
                    type
                    updatedAt
                    value
                    definition {
                        id
                        name
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
    
    private static final String DELETE_METAFIELD_MUTATION = """
        mutation metafieldDelete($input: MetafieldDeleteInput!) {
            metafieldDelete(input: $input) {
                deletedId
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    private static final String CREATE_METAFIELD_DEFINITION_MUTATION = """
        mutation metafieldDefinitionCreate($definition: MetafieldDefinitionInput!) {
            metafieldDefinitionCreate(definition: $definition) {
                createdDefinition {
                    id
                    key
                    namespace
                    name
                    description
                    ownerType
                    type {
                        name
                        category
                    }
                    validationStatus
                    visibleToStorefrontApi
                    pinnedPosition
                    validations {
                        name
                        type
                        value
                    }
                }
                userErrors {
                    field
                    message
                    code
                    elementIndex
                }
            }
        }
        """;
    
    private static final String UPDATE_METAFIELD_DEFINITION_MUTATION = """
        mutation metafieldDefinitionUpdate($definition: MetafieldDefinitionUpdateInput!) {
            metafieldDefinitionUpdate(definition: $definition) {
                updatedDefinition {
                    id
                    key
                    namespace
                    name
                    description
                    ownerType
                    type {
                        name
                        category
                    }
                    validationStatus
                    visibleToStorefrontApi
                    pinnedPosition
                    validations {
                        name
                        type
                        value
                    }
                }
                userErrors {
                    field
                    message
                    code
                    elementIndex
                }
            }
        }
        """;
    
    private static final String DELETE_METAFIELD_DEFINITION_MUTATION = """
        mutation metafieldDefinitionDelete($id: ID!, $deleteAllAssociatedMetafields: Boolean) {
            metafieldDefinitionDelete(id: $id, deleteAllAssociatedMetafields: $deleteAllAssociatedMetafields) {
                deletedDefinitionId
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    private static final String PIN_METAFIELD_DEFINITION_MUTATION = """
        mutation metafieldDefinitionPin($definitionId: ID!) {
            metafieldDefinitionPin(definitionId: $definitionId) {
                pinnedDefinition {
                    id
                    pinnedPosition
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    private static final String UNPIN_METAFIELD_DEFINITION_MUTATION = """
        mutation metafieldDefinitionUnpin($definitionId: ID!) {
            metafieldDefinitionUnpin(definitionId: $definitionId) {
                unpinnedDefinition {
                    id
                    pinnedPosition
                }
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    public Metafield getMetafield(ShopifyAuthContext context, String metafieldId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", metafieldId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_METAFIELD_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetafieldData> response = graphQLClient.execute(
                request,
                MetafieldData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to get metafield: {}", response.getErrors());
            throw new RuntimeException("Failed to get metafield");
        }
        
        return response.getData().getMetafield();
    }
    
    public MetafieldConnection listMetafields(
            ShopifyAuthContext context,
            int first,
            String after,
            String namespace,
            MetafieldOwnerType ownerType,
            String key) {
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) variables.put("after", after);
        if (namespace != null) variables.put("namespace", namespace);
        if (ownerType != null) variables.put("ownerType", ownerType);
        if (key != null) variables.put("key", key);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(LIST_METAFIELDS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetafieldsData> response = graphQLClient.execute(
                request,
                MetafieldsData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to list metafields: {}", response.getErrors());
            throw new RuntimeException("Failed to list metafields");
        }
        
        return response.getData().getMetafields();
    }
    
    public MetafieldDefinitionConnection listMetafieldDefinitions(
            ShopifyAuthContext context,
            int first,
            String after,
            MetafieldOwnerType ownerType,
            String namespace,
            String pinnedStatus) {
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) variables.put("after", after);
        if (ownerType != null) variables.put("ownerType", ownerType);
        if (namespace != null) variables.put("namespace", namespace);
        if (pinnedStatus != null) variables.put("pinnedStatus", pinnedStatus);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(LIST_METAFIELD_DEFINITIONS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetafieldDefinitionsData> response = graphQLClient.execute(
                request,
                MetafieldDefinitionsData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to list metafield definitions: {}", response.getErrors());
            throw new RuntimeException("Failed to list metafield definitions");
        }
        
        return response.getData().getMetafieldDefinitions();
    }
    
    public List<Metafield> setMetafields(ShopifyAuthContext context, List<MetafieldsSetInput> metafields) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("metafields", metafields);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_METAFIELD_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetafieldsSetData> response = graphQLClient.execute(
                request,
                MetafieldsSetData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to set metafields: {}", response.getErrors());
            throw new RuntimeException("Failed to set metafields");
        }
        
        MetafieldsSetResponse setResponse = response.getData().getMetafieldsSet();
        if (setResponse.getUserErrors() != null && !setResponse.getUserErrors().isEmpty()) {
            log.error("User errors setting metafields: {}", setResponse.getUserErrors());
            throw new RuntimeException("Failed to set metafields: " + setResponse.getUserErrors());
        }
        
        return setResponse.getMetafields();
    }
    
    public String deleteMetafield(ShopifyAuthContext context, MetafieldDeleteInput input) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_METAFIELD_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetafieldDeleteData> response = graphQLClient.execute(
                request,
                MetafieldDeleteData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to delete metafield: {}", response.getErrors());
            throw new RuntimeException("Failed to delete metafield");
        }
        
        MetafieldDeleteResponse deleteResponse = response.getData().getMetafieldDelete();
        if (deleteResponse.getUserErrors() != null && !deleteResponse.getUserErrors().isEmpty()) {
            log.error("User errors deleting metafield: {}", deleteResponse.getUserErrors());
            throw new RuntimeException("Failed to delete metafield: " + deleteResponse.getUserErrors());
        }
        
        return deleteResponse.getDeletedId();
    }
    
    public MetafieldDefinition createMetafieldDefinition(ShopifyAuthContext context, MetafieldDefinitionInput definition) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("definition", definition);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_METAFIELD_DEFINITION_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetafieldDefinitionCreateData> response = graphQLClient.execute(
                request,
                MetafieldDefinitionCreateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to create metafield definition: {}", response.getErrors());
            throw new RuntimeException("Failed to create metafield definition");
        }
        
        MetafieldDefinitionCreateResponse createResponse = response.getData().getMetafieldDefinitionCreate();
        if (createResponse.getUserErrors() != null && !createResponse.getUserErrors().isEmpty()) {
            log.error("User errors creating metafield definition: {}", createResponse.getUserErrors());
            throw new RuntimeException("Failed to create metafield definition: " + createResponse.getUserErrors());
        }
        
        return createResponse.getCreatedDefinition();
    }
    
    public MetafieldDefinition updateMetafieldDefinition(ShopifyAuthContext context, MetafieldDefinitionUpdateInput definition) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("definition", definition);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_METAFIELD_DEFINITION_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetafieldDefinitionUpdateData> response = graphQLClient.execute(
                request,
                MetafieldDefinitionUpdateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to update metafield definition: {}", response.getErrors());
            throw new RuntimeException("Failed to update metafield definition");
        }
        
        MetafieldDefinitionUpdateResponse updateResponse = response.getData().getMetafieldDefinitionUpdate();
        if (updateResponse.getUserErrors() != null && !updateResponse.getUserErrors().isEmpty()) {
            log.error("User errors updating metafield definition: {}", updateResponse.getUserErrors());
            throw new RuntimeException("Failed to update metafield definition: " + updateResponse.getUserErrors());
        }
        
        return updateResponse.getUpdatedDefinition();
    }
    
    public String deleteMetafieldDefinition(ShopifyAuthContext context, String definitionId, Boolean deleteAllAssociatedMetafields) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", definitionId);
        if (deleteAllAssociatedMetafields != null) {
            variables.put("deleteAllAssociatedMetafields", deleteAllAssociatedMetafields);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_METAFIELD_DEFINITION_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetafieldDefinitionDeleteData> response = graphQLClient.execute(
                request,
                MetafieldDefinitionDeleteData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to delete metafield definition: {}", response.getErrors());
            throw new RuntimeException("Failed to delete metafield definition");
        }
        
        MetafieldDefinitionDeleteResponse deleteResponse = response.getData().getMetafieldDefinitionDelete();
        if (deleteResponse.getUserErrors() != null && !deleteResponse.getUserErrors().isEmpty()) {
            log.error("User errors deleting metafield definition: {}", deleteResponse.getUserErrors());
            throw new RuntimeException("Failed to delete metafield definition: " + deleteResponse.getUserErrors());
        }
        
        return deleteResponse.getDeletedDefinitionId();
    }
    
    public MetafieldDefinition pinMetafieldDefinition(ShopifyAuthContext context, String definitionId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("definitionId", definitionId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(PIN_METAFIELD_DEFINITION_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetafieldDefinitionPinData> response = graphQLClient.execute(
                request,
                MetafieldDefinitionPinData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to pin metafield definition: {}", response.getErrors());
            throw new RuntimeException("Failed to pin metafield definition");
        }
        
        MetafieldDefinitionPinResponse pinResponse = response.getData().getMetafieldDefinitionPin();
        if (pinResponse.getUserErrors() != null && !pinResponse.getUserErrors().isEmpty()) {
            log.error("User errors pinning metafield definition: {}", pinResponse.getUserErrors());
            throw new RuntimeException("Failed to pin metafield definition: " + pinResponse.getUserErrors());
        }
        
        return pinResponse.getPinnedDefinition();
    }
    
    public MetafieldDefinition unpinMetafieldDefinition(ShopifyAuthContext context, String definitionId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("definitionId", definitionId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UNPIN_METAFIELD_DEFINITION_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<MetafieldDefinitionUnpinData> response = graphQLClient.execute(
                request,
                MetafieldDefinitionUnpinData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to unpin metafield definition: {}", response.getErrors());
            throw new RuntimeException("Failed to unpin metafield definition");
        }
        
        MetafieldDefinitionUnpinResponse unpinResponse = response.getData().getMetafieldDefinitionUnpin();
        if (unpinResponse.getUserErrors() != null && !unpinResponse.getUserErrors().isEmpty()) {
            log.error("User errors unpinning metafield definition: {}", unpinResponse.getUserErrors());
            throw new RuntimeException("Failed to unpin metafield definition: " + unpinResponse.getUserErrors());
        }
        
        return unpinResponse.getUnpinnedDefinition();
    }
    
    @Data
    private static class MetafieldData {
        private Metafield metafield;
    }
    
    @Data
    private static class MetafieldsData {
        private MetafieldConnection metafields;
    }
    
    @Data
    private static class MetafieldDefinitionsData {
        private MetafieldDefinitionConnection metafieldDefinitions;
    }
    
    @Data
    private static class MetafieldsSetData {
        private MetafieldsSetResponse metafieldsSet;
    }
    
    @Data
    private static class MetafieldDeleteData {
        private MetafieldDeleteResponse metafieldDelete;
    }
    
    @Data
    private static class MetafieldDefinitionCreateData {
        private MetafieldDefinitionCreateResponse metafieldDefinitionCreate;
    }
    
    @Data
    private static class MetafieldDefinitionUpdateData {
        private MetafieldDefinitionUpdateResponse metafieldDefinitionUpdate;
    }
    
    @Data
    private static class MetafieldDefinitionDeleteData {
        private MetafieldDefinitionDeleteResponse metafieldDefinitionDelete;
    }
    
    @Data
    private static class MetafieldDefinitionPinData {
        private MetafieldDefinitionPinResponse metafieldDefinitionPin;
    }
    
    @Data
    private static class MetafieldDefinitionUnpinData {
        private MetafieldDefinitionUnpinResponse metafieldDefinitionUnpin;
    }
    
    @Data
    public static class MetafieldsSetResponse {
        private List<Metafield> metafields;
        private List<MetafieldUserError> userErrors;
    }
    
    @Data
    public static class MetafieldDeleteResponse {
        private String deletedId;
        private List<MetafieldUserError> userErrors;
    }
    
    @Data
    public static class MetafieldDefinitionCreateResponse {
        private MetafieldDefinition createdDefinition;
        private List<MetafieldDefinitionUserError> userErrors;
    }
    
    @Data
    public static class MetafieldDefinitionUpdateResponse {
        private MetafieldDefinition updatedDefinition;
        private List<MetafieldDefinitionUserError> userErrors;
    }
    
    @Data
    public static class MetafieldDefinitionDeleteResponse {
        private String deletedDefinitionId;
        private List<MetafieldDefinitionUserError> userErrors;
    }
    
    @Data
    public static class MetafieldDefinitionPinResponse {
        private MetafieldDefinition pinnedDefinition;
        private List<MetafieldDefinitionUserError> userErrors;
    }
    
    @Data
    public static class MetafieldDefinitionUnpinResponse {
        private MetafieldDefinition unpinnedDefinition;
        private List<MetafieldDefinitionUserError> userErrors;
    }
    
    @Data
    public static class MetafieldUserError {
        private List<String> field;
        private String message;
        private String code;
    }
    
    @Data
    public static class MetafieldDefinitionUserError {
        private List<String> field;
        private String message;
        private String code;
        private Integer elementIndex;
    }
    
    @Data
    public static class PageInfo {
        private boolean hasNextPage;
        private boolean hasPreviousPage;
        private String startCursor;
        private String endCursor;
    }
}