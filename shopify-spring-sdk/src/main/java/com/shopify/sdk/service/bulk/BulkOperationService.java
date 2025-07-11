package com.shopify.sdk.service.bulk;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.GraphQLClient;
import com.shopify.sdk.model.bulk.BulkOperation;
import com.shopify.sdk.model.bulk.BulkOperationStatus;
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
public class BulkOperationService {
    
    private final GraphQLClient graphQLClient;
    
    private static final String BULK_OPERATION_RUN_QUERY_MUTATION = """
        mutation bulkOperationRunQuery($query: String!) {
            bulkOperationRunQuery(query: $query) {
                bulkOperation {
                    id
                    status
                    errorCode
                    createdAt
                    completedAt
                    objectCount
                    fileSize
                    url
                    partialDataUrl
                    query
                    rootObjectCount
                    type
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    private static final String BULK_OPERATION_RUN_MUTATION = """
        mutation bulkOperationRunMutation($mutation: String!, $stagedUploadPath: String!) {
            bulkOperationRunMutation(
                mutation: $mutation,
                stagedUploadPath: $stagedUploadPath
            ) {
                bulkOperation {
                    id
                    status
                    errorCode
                    createdAt
                    completedAt
                    objectCount
                    fileSize
                    url
                    partialDataUrl
                    query
                    rootObjectCount
                    type
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    private static final String BULK_OPERATION_CANCEL_MUTATION = """
        mutation bulkOperationCancel($id: ID!) {
            bulkOperationCancel(id: $id) {
                bulkOperation {
                    id
                    status
                    errorCode
                    createdAt
                    completedAt
                    objectCount
                    fileSize
                    url
                    partialDataUrl
                    query
                    rootObjectCount
                    type
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    private static final String CURRENT_BULK_OPERATION_QUERY = """
        query currentBulkOperation($type: BulkOperationType) {
            currentBulkOperation(type: $type) {
                id
                status
                errorCode
                createdAt
                completedAt
                objectCount
                fileSize
                url
                partialDataUrl
                query
                rootObjectCount
                type
            }
        }
        """;
    
    private static final String BULK_OPERATION_BY_ID_QUERY = """
        query bulkOperation($id: ID!) {
            node(id: $id) {
                ... on BulkOperation {
                    id
                    status
                    errorCode
                    createdAt
                    completedAt
                    objectCount
                    fileSize
                    url
                    partialDataUrl
                    query
                    rootObjectCount
                    type
                }
            }
        }
        """;
    
    public BulkOperation runQuery(ShopifyAuthContext context, String query) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("query", query);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(BULK_OPERATION_RUN_QUERY_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<BulkOperationRunQueryData> response = graphQLClient.execute(
                request,
                BulkOperationRunQueryData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to run bulk operation query: {}", response.getErrors());
            throw new RuntimeException("Failed to run bulk operation query");
        }
        
        BulkOperationRunQueryResponse runResponse = response.getData().getBulkOperationRunQuery();
        if (runResponse.getUserErrors() != null && !runResponse.getUserErrors().isEmpty()) {
            log.error("User errors running bulk operation: {}", runResponse.getUserErrors());
            throw new RuntimeException("Failed to run bulk operation: " + runResponse.getUserErrors());
        }
        
        return runResponse.getBulkOperation();
    }
    
    public BulkOperation runMutation(ShopifyAuthContext context, String mutation, String stagedUploadPath) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("mutation", mutation);
        variables.put("stagedUploadPath", stagedUploadPath);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(BULK_OPERATION_RUN_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<BulkOperationRunMutationData> response = graphQLClient.execute(
                request,
                BulkOperationRunMutationData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to run bulk operation mutation: {}", response.getErrors());
            throw new RuntimeException("Failed to run bulk operation mutation");
        }
        
        BulkOperationRunMutationResponse runResponse = response.getData().getBulkOperationRunMutation();
        if (runResponse.getUserErrors() != null && !runResponse.getUserErrors().isEmpty()) {
            log.error("User errors running bulk mutation: {}", runResponse.getUserErrors());
            throw new RuntimeException("Failed to run bulk mutation: " + runResponse.getUserErrors());
        }
        
        return runResponse.getBulkOperation();
    }
    
    public BulkOperation cancel(ShopifyAuthContext context, String operationId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", operationId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(BULK_OPERATION_CANCEL_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<BulkOperationCancelData> response = graphQLClient.execute(
                request,
                BulkOperationCancelData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to cancel bulk operation: {}", response.getErrors());
            throw new RuntimeException("Failed to cancel bulk operation");
        }
        
        BulkOperationCancelResponse cancelResponse = response.getData().getBulkOperationCancel();
        if (cancelResponse.getUserErrors() != null && !cancelResponse.getUserErrors().isEmpty()) {
            log.error("User errors canceling bulk operation: {}", cancelResponse.getUserErrors());
            throw new RuntimeException("Failed to cancel bulk operation: " + cancelResponse.getUserErrors());
        }
        
        return cancelResponse.getBulkOperation();
    }
    
    public BulkOperation getCurrentBulkOperation(ShopifyAuthContext context, String type) {
        Map<String, Object> variables = new HashMap<>();
        if (type != null) {
            variables.put("type", type);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CURRENT_BULK_OPERATION_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<CurrentBulkOperationData> response = graphQLClient.execute(
                request,
                CurrentBulkOperationData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to get current bulk operation: {}", response.getErrors());
            throw new RuntimeException("Failed to get current bulk operation");
        }
        
        return response.getData().getCurrentBulkOperation();
    }
    
    public BulkOperation getBulkOperation(ShopifyAuthContext context, String operationId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", operationId);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(BULK_OPERATION_BY_ID_QUERY)
                .variables(variables)
                .build();
        
        GraphQLResponse<NodeData> response = graphQLClient.execute(
                request,
                NodeData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to get bulk operation: {}", response.getErrors());
            throw new RuntimeException("Failed to get bulk operation");
        }
        
        return response.getData().getNode();
    }
    
    @Data
    private static class BulkOperationRunQueryData {
        private BulkOperationRunQueryResponse bulkOperationRunQuery;
    }
    
    @Data
    private static class BulkOperationRunMutationData {
        private BulkOperationRunMutationResponse bulkOperationRunMutation;
    }
    
    @Data
    private static class BulkOperationCancelData {
        private BulkOperationCancelResponse bulkOperationCancel;
    }
    
    @Data
    private static class CurrentBulkOperationData {
        private BulkOperation currentBulkOperation;
    }
    
    @Data
    private static class NodeData {
        private BulkOperation node;
    }
    
    @Data
    public static class BulkOperationRunQueryResponse {
        private BulkOperation bulkOperation;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class BulkOperationRunMutationResponse {
        private BulkOperation bulkOperation;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class BulkOperationCancelResponse {
        private BulkOperation bulkOperation;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class UserError {
        private List<String> field;
        private String message;
    }
}