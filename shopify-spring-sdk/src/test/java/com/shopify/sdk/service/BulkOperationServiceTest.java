package com.shopify.sdk.service;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.bulk.*;
import com.shopify.sdk.model.common.ID;
import com.shopify.sdk.model.graphql.GraphQLRequest;
import com.shopify.sdk.model.graphql.GraphQLResponse;
import com.shopify.sdk.service.bulk.BulkOperationService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * BulkOperationService 테스트
 * 대량 데이터 처리 기능을 테스트합니다.
 */
@ExtendWith(MockitoExtension.class)
class BulkOperationServiceTest {
    
    @Mock
    private ShopifyGraphQLClient graphQLClient;
    
    private BulkOperationService bulkOperationService;
    private ShopifyAuthContext authContext;
    
    @BeforeEach
    void setUp() {
        bulkOperationService = new BulkOperationService(graphQLClient);
        authContext = ShopifyAuthContext.builder()
            .shopDomain("test-store.myshopify.com")
            .accessToken("test-token")
            .apiVersion("2025-07")
            .build();
    }
    
    @Test
    @DisplayName("대량 쿼리 작업 생성 - 성공")
    void testCreateBulkQuery_Success() {
        // Given
        String bulkQuery = """
            {
                products {
                    edges {
                        node {
                            id
                            title
                            variants {
                                edges {
                                    node {
                                        id
                                        sku
                                        price
                                    }
                                }
                            }
                        }
                    }
                }
            }
            """;
        
        BulkOperation expectedOperation = BulkOperation.builder()
            .id(new ID("gid://shopify/BulkOperation/123456"))
            .status(BulkOperationStatus.CREATED)
            .type(BulkOperationType.QUERY)
            .query(bulkQuery)
            .createdAt(ZonedDateTime.now())
            .objectCount(0L)
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockBulkQueryResponse(expectedOperation);
        when(graphQLClient.execute(any(GraphQLRequest.class), any())).thenReturn(mockResponse);
        
        // When
        BulkOperation operation = bulkOperationService.createBulkQuery(authContext, bulkQuery);
        
        // Then
        assertNotNull(operation);
        assertEquals(BulkOperationStatus.CREATED, operation.getStatus());
        assertEquals(BulkOperationType.QUERY, operation.getType());
        
        // Verify GraphQL request
        ArgumentCaptor<GraphQLRequest> requestCaptor = ArgumentCaptor.forClass(GraphQLRequest.class);
        verify(graphQLClient).execute(requestCaptor.capture(), any());
        
        GraphQLRequest request = requestCaptor.getValue();
        assertTrue(request.getQuery().contains("bulkOperationRunQuery"));
        assertEquals(bulkQuery, request.getVariables().get("query"));
    }
    
    @Test
    @DisplayName("대량 뮤테이션 작업 생성")
    void testCreateBulkMutation() {
        // Given
        String bulkMutation = """
            mutation {
                productUpdate(input: {id: "gid://shopify/Product/123", title: "Updated"}) {
                    product { id }
                }
            }
            """;
        
        BulkOperation expectedOperation = BulkOperation.builder()
            .id(new ID("gid://shopify/BulkOperation/789012"))
            .status(BulkOperationStatus.CREATED)
            .type(BulkOperationType.MUTATION)
            .query(bulkMutation)
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockBulkMutationResponse(expectedOperation);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        BulkOperation operation = bulkOperationService.createBulkMutation(authContext, bulkMutation);
        
        // Then
        assertNotNull(operation);
        assertEquals(BulkOperationType.MUTATION, operation.getType());
    }
    
    @Test
    @DisplayName("대량 작업 상태 확인")
    void testGetBulkOperationStatus() {
        // Given
        String operationId = "gid://shopify/BulkOperation/123456";
        
        BulkOperation runningOperation = BulkOperation.builder()
            .id(new ID(operationId))
            .status(BulkOperationStatus.RUNNING)
            .type(BulkOperationType.QUERY)
            .objectCount(5000L)
            .completedAt(null)
            .url(null)
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockBulkOperationStatusResponse(runningOperation);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        BulkOperation operation = bulkOperationService.getBulkOperationStatus(authContext, operationId);
        
        // Then
        assertNotNull(operation);
        assertEquals(BulkOperationStatus.RUNNING, operation.getStatus());
        assertEquals(5000L, operation.getObjectCount());
        assertNull(operation.getUrl());
    }
    
    @Test
    @DisplayName("대량 작업 완료 대기 - 성공")
    void testWaitForCompletion_Success() throws TimeoutException, InterruptedException {
        // Given
        String operationId = "gid://shopify/BulkOperation/123456";
        String resultUrl = "https://storage.googleapis.com/shopify-bulk-results/123456.jsonl";
        
        // 첫 번째 호출: RUNNING
        BulkOperation runningOp = BulkOperation.builder()
            .id(new ID(operationId))
            .status(BulkOperationStatus.RUNNING)
            .build();
        
        // 두 번째 호출: COMPLETED
        BulkOperation completedOp = BulkOperation.builder()
            .id(new ID(operationId))
            .status(BulkOperationStatus.COMPLETED)
            .completedAt(ZonedDateTime.now())
            .url(resultUrl)
            .objectCount(10000L)
            .build();
        
        when(graphQLClient.execute(any(), any()))
            .thenReturn(createMockBulkOperationStatusResponse(runningOp))
            .thenReturn(createMockBulkOperationStatusResponse(completedOp));
        
        // When
        BulkOperation result = bulkOperationService.waitForCompletion(
            authContext,
            operationId,
            30000 // 30초 타임아웃
        );
        
        // Then
        assertNotNull(result);
        assertEquals(BulkOperationStatus.COMPLETED, result.getStatus());
        assertEquals(resultUrl, result.getUrl());
        assertEquals(10000L, result.getObjectCount());
        
        // Verify polling occurred
        verify(graphQLClient, times(2)).execute(any(), any());
    }
    
    @Test
    @DisplayName("대량 작업 완료 대기 - 타임아웃")
    void testWaitForCompletion_Timeout() {
        // Given
        String operationId = "gid://shopify/BulkOperation/123456";
        
        BulkOperation runningOp = BulkOperation.builder()
            .id(new ID(operationId))
            .status(BulkOperationStatus.RUNNING)
            .build();
        
        // 항상 RUNNING 상태 반환
        when(graphQLClient.execute(any(), any()))
            .thenReturn(createMockBulkOperationStatusResponse(runningOp));
        
        // When & Then
        assertThrows(TimeoutException.class, () -> {
            bulkOperationService.waitForCompletion(authContext, operationId, 1000); // 1초 타임아웃
        });
    }
    
    @Test
    @DisplayName("대량 작업 취소")
    void testCancelBulkOperation() {
        // Given
        String operationId = "gid://shopify/BulkOperation/123456";
        
        BulkOperation cancelledOp = BulkOperation.builder()
            .id(new ID(operationId))
            .status(BulkOperationStatus.CANCELED)
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockBulkOperationCancelResponse(cancelledOp);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        BulkOperation result = bulkOperationService.cancelBulkOperation(authContext, operationId);
        
        // Then
        assertNotNull(result);
        assertEquals(BulkOperationStatus.CANCELED, result.getStatus());
    }
    
    @Test
    @DisplayName("현재 실행 중인 대량 작업 조회")
    void testGetCurrentBulkOperation() {
        // Given
        BulkOperation currentOp = BulkOperation.builder()
            .id(new ID("gid://shopify/BulkOperation/current"))
            .status(BulkOperationStatus.RUNNING)
            .type(BulkOperationType.QUERY)
            .objectCount(2500L)
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockCurrentBulkOperationResponse(currentOp);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        Optional<BulkOperation> result = bulkOperationService.getCurrentBulkOperation(authContext);
        
        // Then
        assertTrue(result.isPresent());
        assertEquals(BulkOperationStatus.RUNNING, result.get().getStatus());
        assertEquals(2500L, result.get().getObjectCount());
    }
    
    @Test
    @DisplayName("대량 작업 에러 처리")
    void testBulkOperationError() {
        // Given
        String operationId = "gid://shopify/BulkOperation/123456";
        
        BulkOperation failedOp = BulkOperation.builder()
            .id(new ID(operationId))
            .status(BulkOperationStatus.FAILED)
            .errorCode(BulkOperationErrorCode.INTERNAL_SERVER_ERROR)
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockBulkOperationStatusResponse(failedOp);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        BulkOperation result = bulkOperationService.getBulkOperationStatus(authContext, operationId);
        
        // Then
        assertNotNull(result);
        assertEquals(BulkOperationStatus.FAILED, result.getStatus());
        assertEquals(BulkOperationErrorCode.INTERNAL_SERVER_ERROR, result.getErrorCode());
    }
    
    @Test
    @DisplayName("대량 작업 동시 실행 제한")
    void testConcurrentBulkOperationLimit() {
        // Given: 이미 실행 중인 작업이 있음
        GraphQLResponse<Object> errorResponse = new GraphQLResponse<>();
        errorResponse.setData(Map.of(
            "bulkOperationRunQuery", Map.of(
                "bulkOperation", null,
                "userErrors", Arrays.asList(
                    Map.of(
                        "field", Arrays.asList("query"),
                        "message", "A bulk operation is already in progress",
                        "code", "BULK_OPERATION_IN_PROGRESS"
                    )
                )
            )
        ));
        
        when(graphQLClient.execute(any(), any())).thenReturn(errorResponse);
        
        // When & Then
        ShopifyApiException exception = assertThrows(ShopifyApiException.class, () -> {
            bulkOperationService.createBulkQuery(authContext, "{ products { edges { node { id } } } }");
        });
        
        assertTrue(exception.getMessage().contains("bulk operation is already in progress"));
    }
    
    // Helper methods
    private GraphQLResponse<Object> createMockBulkQueryResponse(BulkOperation operation) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "bulkOperationRunQuery", Map.of(
                "bulkOperation", operation,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockBulkMutationResponse(BulkOperation operation) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "bulkOperationRunMutation", Map.of(
                "bulkOperation", operation,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockBulkOperationStatusResponse(BulkOperation operation) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "node", operation
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockBulkOperationCancelResponse(BulkOperation operation) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "bulkOperationCancel", Map.of(
                "bulkOperation", operation,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockCurrentBulkOperationResponse(BulkOperation operation) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "currentBulkOperation", operation
        ));
        return response;
    }
}