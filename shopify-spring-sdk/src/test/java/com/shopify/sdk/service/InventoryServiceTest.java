package com.shopify.sdk.service;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.common.ID;
import com.shopify.sdk.model.graphql.GraphQLRequest;
import com.shopify.sdk.model.graphql.GraphQLResponse;
import com.shopify.sdk.model.inventory.*;
import com.shopify.sdk.model.product.ProductVariant;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * InventoryService 테스트
 * 재고 관리 기능을 테스트합니다.
 */
@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {
    
    @Mock
    private ShopifyGraphQLClient graphQLClient;
    
    private InventoryService inventoryService;
    private ShopifyAuthContext authContext;
    
    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService(graphQLClient);
        authContext = ShopifyAuthContext.builder()
            .shopDomain("test-store.myshopify.com")
            .accessToken("test-token")
            .apiVersion("2025-07")
            .build();
    }
    
    @Test
    @DisplayName("재고 아이템 조회 - 성공")
    void testGetInventoryItem_Success() {
        // Given
        String inventoryItemId = "gid://shopify/InventoryItem/123456";
        
        InventoryItem expectedItem = InventoryItem.builder()
            .id(new ID(inventoryItemId))
            .sku("SKU-001")
            .tracked(true)
            .requiresShipping(true)
            .cost(new BigDecimal("10.50"))
            .countryCodeOfOrigin("KR")
            .harmonizedSystemCode("6109.10.00")
            .provinceCodeOfOrigin("SEOUL")
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockInventoryItemResponse(expectedItem);
        when(graphQLClient.execute(any(GraphQLRequest.class), any())).thenReturn(mockResponse);
        
        // When
        InventoryItem item = inventoryService.getInventoryItem(authContext, inventoryItemId);
        
        // Then
        assertNotNull(item);
        assertEquals("SKU-001", item.getSku());
        assertTrue(item.isTracked());
        assertEquals(new BigDecimal("10.50"), item.getCost());
        assertEquals("KR", item.getCountryCodeOfOrigin());
        
        // Verify GraphQL request
        ArgumentCaptor<GraphQLRequest> requestCaptor = ArgumentCaptor.forClass(GraphQLRequest.class);
        verify(graphQLClient).execute(requestCaptor.capture(), any());
        
        GraphQLRequest request = requestCaptor.getValue();
        assertTrue(request.getQuery().contains("inventoryItem"));
        assertEquals(inventoryItemId, request.getVariables().get("id"));
    }
    
    @Test
    @DisplayName("재고 수준 조회 - 위치별")
    void testGetInventoryLevels_ByLocation() {
        // Given
        String locationId = "gid://shopify/Location/789012";
        
        List<InventoryLevel> expectedLevels = Arrays.asList(
            InventoryLevel.builder()
                .id(new ID("gid://shopify/InventoryLevel/1"))
                .available(100)
                .incoming(50)
                .inventoryItem(InventoryItem.builder()
                    .id(new ID("gid://shopify/InventoryItem/123"))
                    .sku("SKU-001")
                    .build())
                .location(Location.builder()
                    .id(new ID(locationId))
                    .name("Main Warehouse")
                    .build())
                .updatedAt(ZonedDateTime.now())
                .build(),
            InventoryLevel.builder()
                .id(new ID("gid://shopify/InventoryLevel/2"))
                .available(50)
                .incoming(0)
                .inventoryItem(InventoryItem.builder()
                    .id(new ID("gid://shopify/InventoryItem/456"))
                    .sku("SKU-002")
                    .build())
                .location(Location.builder()
                    .id(new ID(locationId))
                    .name("Main Warehouse")
                    .build())
                .build()
        );
        
        GraphQLResponse<Object> mockResponse = createMockInventoryLevelsResponse(expectedLevels);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        List<InventoryLevel> levels = inventoryService.getInventoryLevelsByLocation(
            authContext,
            locationId,
            20,
            null
        );
        
        // Then
        assertEquals(2, levels.size());
        assertEquals(100, levels.get(0).getAvailable());
        assertEquals(50, levels.get(0).getIncoming());
        assertEquals("SKU-001", levels.get(0).getInventoryItem().getSku());
    }
    
    @Test
    @DisplayName("재고 조정 - 성공")
    void testAdjustInventoryQuantity() {
        // Given
        String inventoryItemId = "gid://shopify/InventoryItem/123456";
        String locationId = "gid://shopify/Location/789012";
        int adjustmentQuantity = -5;
        String reason = "손상된 제품";
        
        InventoryAdjustQuantityInput input = InventoryAdjustQuantityInput.builder()
            .inventoryItemId(inventoryItemId)
            .locationId(locationId)
            .availableDelta(adjustmentQuantity)
            .reason(reason)
            .build();
        
        InventoryLevel adjustedLevel = InventoryLevel.builder()
            .id(new ID("gid://shopify/InventoryLevel/1"))
            .available(95) // 100 - 5
            .inventoryItem(InventoryItem.builder()
                .id(new ID(inventoryItemId))
                .build())
            .location(Location.builder()
                .id(new ID(locationId))
                .build())
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockInventoryAdjustResponse(adjustedLevel);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        InventoryLevel result = inventoryService.adjustInventoryQuantity(authContext, input);
        
        // Then
        assertNotNull(result);
        assertEquals(95, result.getAvailable());
    }
    
    @Test
    @DisplayName("재고 설정 - 절대값")
    void testSetInventoryQuantity() {
        // Given
        String inventoryItemId = "gid://shopify/InventoryItem/123456";
        String locationId = "gid://shopify/Location/789012";
        int newQuantity = 150;
        
        InventorySetQuantityInput input = InventorySetQuantityInput.builder()
            .inventoryItemId(inventoryItemId)
            .locationId(locationId)
            .quantity(newQuantity)
            .reason("재고 실사 결과 반영")
            .build();
        
        InventoryLevel updatedLevel = InventoryLevel.builder()
            .id(new ID("gid://shopify/InventoryLevel/1"))
            .available(150)
            .inventoryItem(InventoryItem.builder()
                .id(new ID(inventoryItemId))
                .build())
            .location(Location.builder()
                .id(new ID(locationId))
                .build())
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockInventorySetResponse(updatedLevel);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        InventoryLevel result = inventoryService.setInventoryQuantity(authContext, input);
        
        // Then
        assertNotNull(result);
        assertEquals(150, result.getAvailable());
    }
    
    @Test
    @DisplayName("재고 아이템 업데이트")
    void testUpdateInventoryItem() {
        // Given
        String inventoryItemId = "gid://shopify/InventoryItem/123456";
        
        InventoryItemUpdateInput updateInput = InventoryItemUpdateInput.builder()
            .id(inventoryItemId)
            .cost(new BigDecimal("12.00"))
            .countryCodeOfOrigin("US")
            .harmonizedSystemCode("6109.10.10")
            .tracked(true)
            .build();
        
        InventoryItem updatedItem = InventoryItem.builder()
            .id(new ID(inventoryItemId))
            .sku("SKU-001")
            .tracked(true)
            .cost(new BigDecimal("12.00"))
            .countryCodeOfOrigin("US")
            .harmonizedSystemCode("6109.10.10")
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockInventoryItemUpdateResponse(updatedItem);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        InventoryItem result = inventoryService.updateInventoryItem(authContext, updateInput);
        
        // Then
        assertNotNull(result);
        assertEquals(new BigDecimal("12.00"), result.getCost());
        assertEquals("US", result.getCountryCodeOfOrigin());
    }
    
    @Test
    @DisplayName("재고 이동 - 위치 간")
    void testMoveInventory() {
        // Given
        String inventoryItemId = "gid://shopify/InventoryItem/123456";
        String fromLocationId = "gid://shopify/Location/111";
        String toLocationId = "gid://shopify/Location/222";
        int quantity = 20;
        
        InventoryMoveInput moveInput = InventoryMoveInput.builder()
            .inventoryItemId(inventoryItemId)
            .fromLocationId(fromLocationId)
            .toLocationId(toLocationId)
            .quantity(quantity)
            .build();
        
        List<InventoryLevel> movedLevels = Arrays.asList(
            InventoryLevel.builder()
                .id(new ID("gid://shopify/InventoryLevel/1"))
                .available(80) // 100 - 20
                .location(Location.builder()
                    .id(new ID(fromLocationId))
                    .name("Warehouse A")
                    .build())
                .build(),
            InventoryLevel.builder()
                .id(new ID("gid://shopify/InventoryLevel/2"))
                .available(70) // 50 + 20
                .location(Location.builder()
                    .id(new ID(toLocationId))
                    .name("Warehouse B")
                    .build())
                .build()
        );
        
        GraphQLResponse<Object> mockResponse = createMockInventoryMoveResponse(movedLevels);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        List<InventoryLevel> result = inventoryService.moveInventory(authContext, moveInput);
        
        // Then
        assertEquals(2, result.size());
        assertEquals(80, result.get(0).getAvailable());
        assertEquals(70, result.get(1).getAvailable());
    }
    
    @Test
    @DisplayName("재고 활성화 - 새 위치")
    void testActivateInventory() {
        // Given
        String inventoryItemId = "gid://shopify/InventoryItem/123456";
        String locationId = "gid://shopify/Location/789012";
        
        InventoryActivateInput activateInput = InventoryActivateInput.builder()
            .inventoryItemId(inventoryItemId)
            .locationId(locationId)
            .available(0)
            .build();
        
        InventoryLevel activatedLevel = InventoryLevel.builder()
            .id(new ID("gid://shopify/InventoryLevel/999"))
            .available(0)
            .inventoryItem(InventoryItem.builder()
                .id(new ID(inventoryItemId))
                .build())
            .location(Location.builder()
                .id(new ID(locationId))
                .name("New Store Location")
                .build())
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockInventoryActivateResponse(activatedLevel);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        InventoryLevel result = inventoryService.activateInventory(authContext, activateInput);
        
        // Then
        assertNotNull(result);
        assertEquals(0, result.getAvailable());
        assertEquals("New Store Location", result.getLocation().getName());
    }
    
    @Test
    @DisplayName("재고 비활성화")
    void testDeactivateInventory() {
        // Given
        String inventoryItemId = "gid://shopify/InventoryItem/123456";
        String locationId = "gid://shopify/Location/789012";
        
        GraphQLResponse<Object> mockResponse = createMockInventoryDeactivateResponse();
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        boolean result = inventoryService.deactivateInventory(
            authContext,
            inventoryItemId,
            locationId
        );
        
        // Then
        assertTrue(result);
    }
    
    @Test
    @DisplayName("재고 조정 실패 - 부족한 재고")
    void testAdjustInventoryQuantity_InsufficientStock() {
        // Given
        String inventoryItemId = "gid://shopify/InventoryItem/123456";
        String locationId = "gid://shopify/Location/789012";
        int adjustmentQuantity = -200; // 재고보다 많은 수량
        
        InventoryAdjustQuantityInput input = InventoryAdjustQuantityInput.builder()
            .inventoryItemId(inventoryItemId)
            .locationId(locationId)
            .availableDelta(adjustmentQuantity)
            .build();
        
        GraphQLResponse<Object> errorResponse = createMockInventoryAdjustErrorResponse(
            "availableDelta",
            "Insufficient inventory quantity available"
        );
        when(graphQLClient.execute(any(), any())).thenReturn(errorResponse);
        
        // When & Then
        ShopifyApiException exception = assertThrows(ShopifyApiException.class, () -> {
            inventoryService.adjustInventoryQuantity(authContext, input);
        });
        
        assertTrue(exception.getMessage().contains("Insufficient inventory quantity"));
    }
    
    @Test
    @DisplayName("재고 일괄 조회 - 변형 상품별")
    void testGetInventoryItemsByVariants() {
        // Given
        List<String> variantIds = Arrays.asList(
            "gid://shopify/ProductVariant/111",
            "gid://shopify/ProductVariant/222"
        );
        
        List<InventoryItem> expectedItems = Arrays.asList(
            InventoryItem.builder()
                .id(new ID("gid://shopify/InventoryItem/123"))
                .sku("VAR-001")
                .tracked(true)
                .build(),
            InventoryItem.builder()
                .id(new ID("gid://shopify/InventoryItem/456"))
                .sku("VAR-002")
                .tracked(true)
                .build()
        );
        
        GraphQLResponse<Object> mockResponse = createMockInventoryItemsByVariantsResponse(expectedItems);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        List<InventoryItem> items = inventoryService.getInventoryItemsByVariants(
            authContext,
            variantIds
        );
        
        // Then
        assertEquals(2, items.size());
        assertEquals("VAR-001", items.get(0).getSku());
        assertEquals("VAR-002", items.get(1).getSku());
    }
    
    // Helper methods
    private GraphQLResponse<Object> createMockInventoryItemResponse(InventoryItem item) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of("inventoryItem", item));
        return response;
    }
    
    private GraphQLResponse<Object> createMockInventoryLevelsResponse(List<InventoryLevel> levels) {
        List<Map<String, Object>> edges = new ArrayList<>();
        for (InventoryLevel level : levels) {
            edges.add(Map.of("node", level));
        }
        
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "inventoryLevels", Map.of(
                "edges", edges,
                "pageInfo", Map.of("hasNextPage", false)
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockInventoryAdjustResponse(InventoryLevel level) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "inventoryAdjustQuantity", Map.of(
                "inventoryLevel", level,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockInventorySetResponse(InventoryLevel level) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "inventorySetQuantity", Map.of(
                "inventoryLevel", level,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockInventoryItemUpdateResponse(InventoryItem item) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "inventoryItemUpdate", Map.of(
                "inventoryItem", item,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockInventoryMoveResponse(List<InventoryLevel> levels) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "inventoryBulkToggleActivation", Map.of(
                "inventoryLevels", levels,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockInventoryActivateResponse(InventoryLevel level) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "inventoryActivate", Map.of(
                "inventoryLevel", level,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockInventoryDeactivateResponse() {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "inventoryDeactivate", Map.of(
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockInventoryAdjustErrorResponse(String field, String message) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "inventoryAdjustQuantity", Map.of(
                "inventoryLevel", null,
                "userErrors", Arrays.asList(
                    Map.of(
                        "field", Arrays.asList(field),
                        "message", message,
                        "code", "INVALID"
                    )
                )
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockInventoryItemsByVariantsResponse(List<InventoryItem> items) {
        List<Map<String, Object>> nodes = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            nodes.add(Map.of(
                "inventoryItem", items.get(i)
            ));
        }
        
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "productVariants", Map.of(
                "nodes", nodes
            )
        ));
        return response;
    }
}