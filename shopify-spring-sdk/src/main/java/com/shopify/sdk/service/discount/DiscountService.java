package com.shopify.sdk.service.discount;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.GraphQLClient;
import com.shopify.sdk.model.discount.*;
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
public class DiscountService {
    
    private final GraphQLClient graphQLClient;
    
    private static final String GET_DISCOUNT_NODE_QUERY = """
        query getDiscountNode($id: ID!) {
            discountNode(id: $id) {
                id
                discountClass
                metafield(namespace: $namespace, key: $key) {
                    id
                    value
                    type
                }
                metafields(first: 20) {
                    edges {
                        node {
                            id
                            namespace
                            key
                            value
                            type
                        }
                    }
                }
                discount {
                    __typename
                    ... on DiscountAutomaticBasic {
                        title
                        status
                        summary
                        startsAt
                        endsAt
                        asyncUsageCount
                        combinesWith {
                            orderDiscounts
                            productDiscounts
                            shippingDiscounts
                        }
                        customerGets {
                            appliesOnOneTimePurchase
                            appliesOnSubscription
                            value {
                                __typename
                                ... on DiscountAmount {
                                    amount {
                                        amount
                                        currencyCode
                                    }
                                    appliesOnEachItem
                                }
                                ... on DiscountPercentage {
                                    percentage
                                }
                            }
                        }
                        minimumRequirement {
                            __typename
                            ... on DiscountMinimumQuantity {
                                greaterThanOrEqualToQuantity
                            }
                            ... on DiscountMinimumSubtotal {
                                greaterThanOrEqualToSubtotal {
                                    amount
                                    currencyCode
                                }
                            }
                        }
                    }
                    ... on DiscountCodeBasic {
                        title
                        status
                        summary
                        startsAt
                        endsAt
                        usageLimit
                        asyncUsageCount
                        appliesOncePerCustomer
                        codes(first: 10) {
                            edges {
                                node {
                                    id
                                    code
                                    asyncUsageCount
                                }
                            }
                        }
                        combinesWith {
                            orderDiscounts
                            productDiscounts
                            shippingDiscounts
                        }
                        customerGets {
                            appliesOnOneTimePurchase
                            appliesOnSubscription
                            value {
                                __typename
                                ... on DiscountAmount {
                                    amount {
                                        amount
                                        currencyCode
                                    }
                                    appliesOnEachItem
                                }
                                ... on DiscountPercentage {
                                    percentage
                                }
                            }
                        }
                        minimumRequirement {
                            __typename
                            ... on DiscountMinimumQuantity {
                                greaterThanOrEqualToQuantity
                            }
                            ... on DiscountMinimumSubtotal {
                                greaterThanOrEqualToSubtotal {
                                    amount
                                    currencyCode
                                }
                            }
                        }
                    }
                }
            }
        }
        """;
    
    private static final String LIST_DISCOUNTS_QUERY = """
        query listDiscounts($first: Int!, $after: String, $query: String) {
            discountNodes(first: $first, after: $after, query: $query) {
                edges {
                    node {
                        id
                        discountClass
                        discount {
                            __typename
                            ... on DiscountAutomaticBasic {
                                title
                                status
                                summary
                                startsAt
                                endsAt
                                asyncUsageCount
                            }
                            ... on DiscountAutomaticBxgy {
                                title
                                status
                                summary
                                startsAt
                                endsAt
                                asyncUsageCount
                            }
                            ... on DiscountAutomaticFreeShipping {
                                title
                                status
                                summary
                                startsAt
                                endsAt
                                asyncUsageCount
                            }
                            ... on DiscountCodeBasic {
                                title
                                status
                                summary
                                startsAt
                                endsAt
                                codeCount
                                asyncUsageCount
                            }
                            ... on DiscountCodeBxgy {
                                title
                                status
                                summary
                                startsAt
                                endsAt
                                codeCount
                                asyncUsageCount
                            }
                            ... on DiscountCodeFreeShipping {
                                title
                                status
                                summary
                                startsAt
                                endsAt
                                codeCount
                                asyncUsageCount
                            }
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
    
    private static final String CREATE_BASIC_DISCOUNT_MUTATION = """
        mutation discountCodeBasicCreate($basicCodeDiscount: DiscountCodeBasicInput!) {
            discountCodeBasicCreate(basicCodeDiscount: $basicCodeDiscount) {
                codeDiscountNode {
                    id
                    codeDiscount {
                        __typename
                        ... on DiscountCodeBasic {
                            title
                            status
                            codes(first: 10) {
                                edges {
                                    node {
                                        id
                                        code
                                    }
                                }
                            }
                        }
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
    
    private static final String UPDATE_BASIC_DISCOUNT_MUTATION = """
        mutation discountCodeBasicUpdate($id: ID!, $basicCodeDiscount: DiscountCodeBasicInput!) {
            discountCodeBasicUpdate(id: $id, basicCodeDiscount: $basicCodeDiscount) {
                codeDiscountNode {
                    id
                    codeDiscount {
                        __typename
                        ... on DiscountCodeBasic {
                            title
                            status
                            codes(first: 10) {
                                edges {
                                    node {
                                        id
                                        code
                                    }
                                }
                            }
                        }
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
    
    private static final String DELETE_DISCOUNT_MUTATION = """
        mutation discountCodeDelete($id: ID!) {
            discountCodeDelete(id: $id) {
                deletedCodeDiscountId
                userErrors {
                    field
                    message
                    code
                }
            }
        }
        """;
    
    private static final String ACTIVATE_DISCOUNT_MUTATION = """
        mutation discountCodeActivate($id: ID!) {
            discountCodeActivate(id: $id) {
                codeDiscountNode {
                    id
                    codeDiscount {
                        __typename
                        ... on DiscountCodeBasic {
                            title
                            status
                        }
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
    
    private static final String DEACTIVATE_DISCOUNT_MUTATION = """
        mutation discountCodeDeactivate($id: ID!) {
            discountCodeDeactivate(id: $id) {
                codeDiscountNode {
                    id
                    codeDiscount {
                        __typename
                        ... on DiscountCodeBasic {
                            title
                            status
                        }
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
    
    public DiscountNode getDiscount(ShopifyAuthContext context, String discountId, String metafieldNamespace, String metafieldKey) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", discountId);
        if (metafieldNamespace != null) variables.put("namespace", metafieldNamespace);
        if (metafieldKey != null) variables.put("key", metafieldKey);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(GET_DISCOUNT_NODE_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<DiscountNodeData> response = graphQLClient.execute(
                request,
                DiscountNodeData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to get discount: {}", response.getErrors());
            throw new RuntimeException("Failed to get discount");
        }
        
        return response.getData().getDiscountNode();
    }
    
    public DiscountNodeConnection listDiscounts(ShopifyAuthContext context, int first, String after, String query) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("first", first);
        if (after != null) variables.put("after", after);
        if (query != null) variables.put("query", query);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(LIST_DISCOUNTS_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<DiscountNodesData> response = graphQLClient.execute(
                request,
                DiscountNodesData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to list discounts: {}", response.getErrors());
            throw new RuntimeException("Failed to list discounts");
        }
        
        return response.getData().getDiscountNodes();
    }
    
    public DiscountCodeNode createBasicDiscount(ShopifyAuthContext context, DiscountCodeBasicInput input) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("basicCodeDiscount", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_BASIC_DISCOUNT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<DiscountCodeBasicCreateData> response = graphQLClient.execute(
                request,
                DiscountCodeBasicCreateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to create basic discount: {}", response.getErrors());
            throw new RuntimeException("Failed to create basic discount");
        }
        
        DiscountCodeBasicCreateResponse createResponse = response.getData().getDiscountCodeBasicCreate();
        if (createResponse.getUserErrors() != null && !createResponse.getUserErrors().isEmpty()) {
            log.error("User errors creating basic discount: {}", createResponse.getUserErrors());
            throw new RuntimeException("Failed to create basic discount: " + createResponse.getUserErrors());
        }
        
        return createResponse.getCodeDiscountNode();
    }
    
    public DiscountCodeNode updateBasicDiscount(ShopifyAuthContext context, String discountId, DiscountCodeBasicInput input) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", discountId);
        variables.put("basicCodeDiscount", input);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(UPDATE_BASIC_DISCOUNT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<DiscountCodeBasicUpdateData> response = graphQLClient.execute(
                request,
                DiscountCodeBasicUpdateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to update basic discount: {}", response.getErrors());
            throw new RuntimeException("Failed to update basic discount");
        }
        
        DiscountCodeBasicUpdateResponse updateResponse = response.getData().getDiscountCodeBasicUpdate();
        if (updateResponse.getUserErrors() != null && !updateResponse.getUserErrors().isEmpty()) {
            log.error("User errors updating basic discount: {}", updateResponse.getUserErrors());
            throw new RuntimeException("Failed to update basic discount: " + updateResponse.getUserErrors());
        }
        
        return updateResponse.getCodeDiscountNode();
    }
    
    public String deleteDiscount(ShopifyAuthContext context, String discountId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", discountId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DELETE_DISCOUNT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<DiscountCodeDeleteData> response = graphQLClient.execute(
                request,
                DiscountCodeDeleteData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to delete discount: {}", response.getErrors());
            throw new RuntimeException("Failed to delete discount");
        }
        
        DiscountCodeDeleteResponse deleteResponse = response.getData().getDiscountCodeDelete();
        if (deleteResponse.getUserErrors() != null && !deleteResponse.getUserErrors().isEmpty()) {
            log.error("User errors deleting discount: {}", deleteResponse.getUserErrors());
            throw new RuntimeException("Failed to delete discount: " + deleteResponse.getUserErrors());
        }
        
        return deleteResponse.getDeletedCodeDiscountId();
    }
    
    public DiscountCodeNode activateDiscount(ShopifyAuthContext context, String discountId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", discountId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(ACTIVATE_DISCOUNT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<DiscountCodeActivateData> response = graphQLClient.execute(
                request,
                DiscountCodeActivateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to activate discount: {}", response.getErrors());
            throw new RuntimeException("Failed to activate discount");
        }
        
        DiscountCodeActivateResponse activateResponse = response.getData().getDiscountCodeActivate();
        if (activateResponse.getUserErrors() != null && !activateResponse.getUserErrors().isEmpty()) {
            log.error("User errors activating discount: {}", activateResponse.getUserErrors());
            throw new RuntimeException("Failed to activate discount: " + activateResponse.getUserErrors());
        }
        
        return activateResponse.getCodeDiscountNode();
    }
    
    public DiscountCodeNode deactivateDiscount(ShopifyAuthContext context, String discountId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", discountId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(DEACTIVATE_DISCOUNT_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<DiscountCodeDeactivateData> response = graphQLClient.execute(
                request,
                DiscountCodeDeactivateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to deactivate discount: {}", response.getErrors());
            throw new RuntimeException("Failed to deactivate discount");
        }
        
        DiscountCodeDeactivateResponse deactivateResponse = response.getData().getDiscountCodeDeactivate();
        if (deactivateResponse.getUserErrors() != null && !deactivateResponse.getUserErrors().isEmpty()) {
            log.error("User errors deactivating discount: {}", deactivateResponse.getUserErrors());
            throw new RuntimeException("Failed to deactivate discount: " + deactivateResponse.getUserErrors());
        }
        
        return deactivateResponse.getCodeDiscountNode();
    }
    
    @Data
    private static class DiscountNodeData {
        private DiscountNode discountNode;
    }
    
    @Data
    private static class DiscountNodesData {
        private DiscountNodeConnection discountNodes;
    }
    
    @Data
    private static class DiscountCodeBasicCreateData {
        private DiscountCodeBasicCreateResponse discountCodeBasicCreate;
    }
    
    @Data
    private static class DiscountCodeBasicUpdateData {
        private DiscountCodeBasicUpdateResponse discountCodeBasicUpdate;
    }
    
    @Data
    private static class DiscountCodeDeleteData {
        private DiscountCodeDeleteResponse discountCodeDelete;
    }
    
    @Data
    private static class DiscountCodeActivateData {
        private DiscountCodeActivateResponse discountCodeActivate;
    }
    
    @Data
    private static class DiscountCodeDeactivateData {
        private DiscountCodeDeactivateResponse discountCodeDeactivate;
    }
    
    @Data
    public static class DiscountCodeBasicCreateResponse {
        private DiscountCodeNode codeDiscountNode;
        private List<DiscountUserError> userErrors;
    }
    
    @Data
    public static class DiscountCodeBasicUpdateResponse {
        private DiscountCodeNode codeDiscountNode;
        private List<DiscountUserError> userErrors;
    }
    
    @Data
    public static class DiscountCodeDeleteResponse {
        private String deletedCodeDiscountId;
        private List<DiscountUserError> userErrors;
    }
    
    @Data
    public static class DiscountCodeActivateResponse {
        private DiscountCodeNode codeDiscountNode;
        private List<DiscountUserError> userErrors;
    }
    
    @Data
    public static class DiscountCodeDeactivateResponse {
        private DiscountCodeNode codeDiscountNode;
        private List<DiscountUserError> userErrors;
    }
    
    @Data
    public static class DiscountUserError {
        private List<String> field;
        private String message;
        private String code;
    }
    
    @Data
    public static class DiscountNodeConnection {
        private List<DiscountNodeEdge> edges;
        private PageInfo pageInfo;
    }
    
    @Data
    public static class DiscountNodeEdge {
        private DiscountNode node;
        private String cursor;
    }
    
    @Data
    public static class PageInfo {
        private boolean hasNextPage;
        private boolean hasPreviousPage;
        private String startCursor;
        private String endCursor;
    }
}