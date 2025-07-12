package com.shopify.sdk.service.bulk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.model.bulk.BulkOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BulkOperationService {
    
    private final ShopifyGraphQLClient graphQLClient;
    private final ObjectMapper objectMapper;
    
    // GraphQL queries and mutations
    private static final String BULK_OPERATION_RUN_QUERY = """
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
                    rootObjectCount
                    type
                    query
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
            bulkOperationRunMutation(mutation: $mutation, stagedUploadPath: $stagedUploadPath) {
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
                    rootObjectCount
                    type
                    query
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    private static final String CURRENT_BULK_OPERATION_QUERY = """
        query {
            currentBulkOperation {
                id
                status
                errorCode
                createdAt
                completedAt
                objectCount
                fileSize
                url
                partialDataUrl
                rootObjectCount
                type
                query
            }
        }
        """;
    
    private static final String BULK_OPERATION_CANCEL = """
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
                    rootObjectCount
                    type
                    query
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    /**
     * Starts a bulk query operation.
     */
    public Mono<BulkOperation> runQuery(String shop, String accessToken, String query) {
        Map<String, Object> variables = Map.of("query", query);
        
        return graphQLClient.query(shop, accessToken, BULK_OPERATION_RUN_QUERY, variables)
            .map(response -> {
                try {
                    var data = response.getData().get("bulkOperationRunQuery");
                    var bulkOp = data.get("bulkOperation");
                    
                    // Check for user errors
                    var userErrors = data.get("userErrors");
                    if (userErrors.isArray() && userErrors.size() > 0) {
                        String errorMessage = userErrors.get(0).get("message").asText();
                        throw new RuntimeException("Bulk operation failed: " + errorMessage);
                    }
                    
                    return objectMapper.convertValue(bulkOp, BulkOperation.class);
                } catch (Exception e) {
                    log.error("Failed to parse bulk operation run query response", e);
                    throw new RuntimeException("Failed to parse bulk operation run query response", e);
                }
            });
    }
    
    /**
     * Starts a bulk mutation operation.
     */
    public Mono<BulkOperation> runMutation(String shop, String accessToken, String mutation, String stagedUploadPath) {
        Map<String, Object> variables = Map.of(
            "mutation", mutation,
            "stagedUploadPath", stagedUploadPath
        );
        
        return graphQLClient.query(shop, accessToken, BULK_OPERATION_RUN_MUTATION, variables)
            .map(response -> {
                try {
                    var data = response.getData().get("bulkOperationRunMutation");
                    var bulkOp = data.get("bulkOperation");
                    
                    // Check for user errors
                    var userErrors = data.get("userErrors");
                    if (userErrors.isArray() && userErrors.size() > 0) {
                        String errorMessage = userErrors.get(0).get("message").asText();
                        throw new RuntimeException("Bulk operation failed: " + errorMessage);
                    }
                    
                    return objectMapper.convertValue(bulkOp, BulkOperation.class);
                } catch (Exception e) {
                    log.error("Failed to parse bulk operation run mutation response", e);
                    throw new RuntimeException("Failed to parse bulk operation run mutation response", e);
                }
            });
    }
    
    /**
     * Gets the current running bulk operation.
     */
    public Mono<BulkOperation> getCurrentOperation(String shop, String accessToken) {
        return graphQLClient.query(shop, accessToken, CURRENT_BULK_OPERATION_QUERY)
            .map(response -> {
                try {
                    var data = response.getData().get("currentBulkOperation");
                    if (data == null || data.isNull()) {
                        return null;
                    }
                    return objectMapper.convertValue(data, BulkOperation.class);
                } catch (Exception e) {
                    log.error("Failed to parse current bulk operation response", e);
                    throw new RuntimeException("Failed to parse current bulk operation response", e);
                }
            });
    }
    
    /**
     * Cancels a running bulk operation.
     */
    public Mono<BulkOperation> cancelOperation(String shop, String accessToken, String operationId) {
        Map<String, Object> variables = Map.of("id", operationId);
        
        return graphQLClient.query(shop, accessToken, BULK_OPERATION_CANCEL, variables)
            .map(response -> {
                try {
                    var data = response.getData().get("bulkOperationCancel");
                    var bulkOp = data.get("bulkOperation");
                    
                    // Check for user errors
                    var userErrors = data.get("userErrors");
                    if (userErrors.isArray() && userErrors.size() > 0) {
                        String errorMessage = userErrors.get(0).get("message").asText();
                        throw new RuntimeException("Bulk operation cancel failed: " + errorMessage);
                    }
                    
                    return objectMapper.convertValue(bulkOp, BulkOperation.class);
                } catch (Exception e) {
                    log.error("Failed to parse bulk operation cancel response", e);
                    throw new RuntimeException("Failed to parse bulk operation cancel response", e);
                }
            });
    }
    
    /**
     * Polls a bulk operation until completion with configurable intervals.
     */
    public Mono<BulkOperation> pollUntilComplete(String shop, String accessToken, String operationId, 
                                               Duration pollInterval, Duration timeout) {
        return Mono.defer(() -> getCurrentOperation(shop, accessToken))
            .filter(operation -> operation != null && operationId.equals(operation.getId()))
            .flatMap(operation -> {
                if (operation.isCompleted() || operation.isFailed() || operation.isCanceled()) {
                    return Mono.just(operation);
                }
                
                log.debug("Bulk operation {} status: {}, waiting {} seconds...", 
                    operationId, operation.getStatus(), pollInterval.getSeconds());
                
                return Mono.delay(pollInterval)
                    .then(pollUntilComplete(shop, accessToken, operationId, pollInterval, timeout));
            })
            .timeout(timeout)
            .doOnError(ex -> log.error("Bulk operation polling failed", ex));
    }
    
    /**
     * Polls a bulk operation until completion with default intervals (5s polling, 30min timeout).
     */
    public Mono<BulkOperation> pollUntilComplete(String shop, String accessToken, String operationId) {
        return pollUntilComplete(shop, accessToken, operationId, 
            Duration.ofSeconds(5), Duration.ofMinutes(30));
    }
    
    /**
     * Convenience method to run a query and wait for completion.
     */
    public Mono<BulkOperation> runQueryAndWait(String shop, String accessToken, String query) {
        return runQuery(shop, accessToken, query)
            .flatMap(operation -> {
                if (operation.isFailed()) {
                    return Mono.error(new RuntimeException("Bulk operation failed: " + operation.getErrorCode()));
                }
                return pollUntilComplete(shop, accessToken, operation.getId());
            });
    }
    
    /**
     * Convenience method to run a mutation and wait for completion.
     */
    public Mono<BulkOperation> runMutationAndWait(String shop, String accessToken, String mutation, String stagedUploadPath) {
        return runMutation(shop, accessToken, mutation, stagedUploadPath)
            .flatMap(operation -> {
                if (operation.isFailed()) {
                    return Mono.error(new RuntimeException("Bulk operation failed: " + operation.getErrorCode()));
                }
                return pollUntilComplete(shop, accessToken, operation.getId());
            });
    }
    
    /**
     * Creates a bulk export query for products.
     */
    public String createProductExportQuery() {
        return """
            {
                products {
                    edges {
                        node {
                            id
                            title
                            handle
                            description
                            vendor
                            productType
                            createdAt
                            updatedAt
                            publishedAt
                            status
                            tags
                            variants {
                                edges {
                                    node {
                                        id
                                        title
                                        sku
                                        price
                                        compareAtPrice
                                        inventoryQuantity
                                        weight
                                        weightUnit
                                    }
                                }
                            }
                        }
                    }
                }
            }
            """;
    }
    
    /**
     * Creates a bulk export query for orders.
     */
    public String createOrderExportQuery() {
        return """
            {
                orders {
                    edges {
                        node {
                            id
                            name
                            email
                            createdAt
                            updatedAt
                            totalPrice
                            subtotalPrice
                            totalTax
                            financialStatus
                            fulfillmentStatus
                            customer {
                                id
                                email
                                firstName
                                lastName
                            }
                            lineItems {
                                edges {
                                    node {
                                        id
                                        title
                                        quantity
                                        price
                                        variant {
                                            id
                                            sku
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            """;
    }
    
    /**
     * Creates a bulk export query for customers.
     */
    public String createCustomerExportQuery() {
        return """
            {
                customers {
                    edges {
                        node {
                            id
                            email
                            firstName
                            lastName
                            phone
                            createdAt
                            updatedAt
                            acceptsMarketing
                            totalSpent
                            ordersCount
                            state
                            addresses {
                                id
                                firstName
                                lastName
                                company
                                address1
                                address2
                                city
                                province
                                country
                                zip
                                phone
                            }
                        }
                    }
                }
            }
            """;
    }
}