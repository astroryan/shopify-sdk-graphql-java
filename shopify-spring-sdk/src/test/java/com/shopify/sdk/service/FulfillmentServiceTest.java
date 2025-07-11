package com.shopify.sdk.service;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.common.ID;
import com.shopify.sdk.model.fulfillment.*;
import com.shopify.sdk.model.graphql.GraphQLRequest;
import com.shopify.sdk.model.graphql.GraphQLResponse;
import com.shopify.sdk.model.order.LineItem;
import com.shopify.sdk.model.order.Order;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * FulfillmentService 테스트
 * 주문 이행(fulfillment) 관리 기능을 테스트합니다.
 */
@ExtendWith(MockitoExtension.class)
class FulfillmentServiceTest {
    
    @Mock
    private ShopifyGraphQLClient graphQLClient;
    
    private FulfillmentService fulfillmentService;
    private ShopifyAuthContext authContext;
    
    @BeforeEach
    void setUp() {
        fulfillmentService = new FulfillmentService(graphQLClient);
        authContext = ShopifyAuthContext.builder()
            .shopDomain("test-store.myshopify.com")
            .accessToken("test-token")
            .apiVersion("2025-07")
            .build();
    }
    
    @Test
    @DisplayName("주문 이행 생성 - 전체 주문")
    void testCreateFulfillment_FullOrder() {
        // Given
        String orderId = "gid://shopify/Order/123456";
        
        FulfillmentInput input = FulfillmentInput.builder()
            .orderId(orderId)
            .notifyCustomer(true)
            .trackingInfo(FulfillmentTrackingInfo.builder()
                .company("DHL")
                .number("1234567890")
                .url("https://tracking.dhl.com/1234567890")
                .build())
            .shipmentStatus(ShipmentStatus.READY_FOR_PICKUP)
            .build();
        
        Fulfillment expectedFulfillment = Fulfillment.builder()
            .id(new ID("gid://shopify/Fulfillment/999888"))
            .status(FulfillmentStatus.PENDING)
            .displayStatus(FulfillmentDisplayStatus.READY_FOR_PICKUP)
            .trackingCompany("DHL")
            .trackingNumbers(Arrays.asList("1234567890"))
            .trackingUrls(Arrays.asList("https://tracking.dhl.com/1234567890"))
            .createdAt(ZonedDateTime.now())
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockFulfillmentCreateResponse(expectedFulfillment);
        when(graphQLClient.execute(any(GraphQLRequest.class), any())).thenReturn(mockResponse);
        
        // When
        Fulfillment fulfillment = fulfillmentService.createFulfillment(authContext, input);
        
        // Then
        assertNotNull(fulfillment);
        assertEquals(FulfillmentStatus.PENDING, fulfillment.getStatus());
        assertEquals(FulfillmentDisplayStatus.READY_FOR_PICKUP, fulfillment.getDisplayStatus());
        assertEquals("DHL", fulfillment.getTrackingCompany());
        assertEquals("1234567890", fulfillment.getTrackingNumbers().get(0));
        
        // Verify GraphQL request
        ArgumentCaptor<GraphQLRequest> requestCaptor = ArgumentCaptor.forClass(GraphQLRequest.class);
        verify(graphQLClient).execute(requestCaptor.capture(), any());
        
        GraphQLRequest request = requestCaptor.getValue();
        assertTrue(request.getQuery().contains("fulfillmentCreateV2"));
        assertEquals(input, request.getVariables().get("fulfillment"));
    }
    
    @Test
    @DisplayName("주문 이행 생성 - 부분 이행")
    void testCreateFulfillment_PartialFulfillment() {
        // Given
        String orderId = "gid://shopify/Order/123456";
        
        List<FulfillmentLineItemInput> lineItems = Arrays.asList(
            FulfillmentLineItemInput.builder()
                .id("gid://shopify/LineItem/111")
                .quantity(2)
                .build(),
            FulfillmentLineItemInput.builder()
                .id("gid://shopify/LineItem/222")
                .quantity(1)
                .build()
        );
        
        FulfillmentInput input = FulfillmentInput.builder()
            .orderId(orderId)
            .lineItemsByFulfillmentOrder(lineItems)
            .notifyCustomer(true)
            .trackingInfo(FulfillmentTrackingInfo.builder()
                .company("FedEx")
                .number("9876543210")
                .build())
            .build();
        
        Fulfillment expectedFulfillment = Fulfillment.builder()
            .id(new ID("gid://shopify/Fulfillment/999889"))
            .status(FulfillmentStatus.SUCCESS)
            .displayStatus(FulfillmentDisplayStatus.FULFILLED)
            .trackingCompany("FedEx")
            .trackingNumbers(Arrays.asList("9876543210"))
            .fulfillmentLineItems(Arrays.asList(
                FulfillmentLineItem.builder()
                    .lineItem(LineItem.builder()
                        .id(new ID("gid://shopify/LineItem/111"))
                        .name("Product A")
                        .build())
                    .quantity(2)
                    .build(),
                FulfillmentLineItem.builder()
                    .lineItem(LineItem.builder()
                        .id(new ID("gid://shopify/LineItem/222"))
                        .name("Product B")
                        .build())
                    .quantity(1)
                    .build()
            ))
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockFulfillmentCreateResponse(expectedFulfillment);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        Fulfillment fulfillment = fulfillmentService.createFulfillment(authContext, input);
        
        // Then
        assertNotNull(fulfillment);
        assertEquals(2, fulfillment.getFulfillmentLineItems().size());
        assertEquals(2, fulfillment.getFulfillmentLineItems().get(0).getQuantity());
        assertEquals(1, fulfillment.getFulfillmentLineItems().get(1).getQuantity());
    }
    
    @Test
    @DisplayName("주문 이행 조회 - ID로")
    void testGetFulfillment_ById() {
        // Given
        String fulfillmentId = "gid://shopify/Fulfillment/123456";
        
        Fulfillment expectedFulfillment = Fulfillment.builder()
            .id(new ID(fulfillmentId))
            .status(FulfillmentStatus.SUCCESS)
            .displayStatus(FulfillmentDisplayStatus.FULFILLED)
            .trackingCompany("UPS")
            .trackingNumbers(Arrays.asList("1Z999AA10123456784"))
            .trackingUrls(Arrays.asList("https://www.ups.com/track?tracknum=1Z999AA10123456784"))
            .deliveredAt(ZonedDateTime.now().minusDays(1))
            .inTransitAt(ZonedDateTime.now().minusDays(3))
            .createdAt(ZonedDateTime.now().minusDays(5))
            .updatedAt(ZonedDateTime.now().minusDays(1))
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockFulfillmentGetResponse(expectedFulfillment);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        Fulfillment fulfillment = fulfillmentService.getFulfillment(authContext, fulfillmentId);
        
        // Then
        assertNotNull(fulfillment);
        assertEquals(fulfillmentId, fulfillment.getId().getValue());
        assertEquals(FulfillmentStatus.SUCCESS, fulfillment.getStatus());
        assertEquals("UPS", fulfillment.getTrackingCompany());
        assertNotNull(fulfillment.getDeliveredAt());
    }
    
    @Test
    @DisplayName("주문별 이행 목록 조회")
    void testListFulfillmentsByOrder() {
        // Given
        String orderId = "gid://shopify/Order/123456";
        
        List<Fulfillment> expectedFulfillments = Arrays.asList(
            createTestFulfillment("1", FulfillmentStatus.SUCCESS, "DHL", "1111111111"),
            createTestFulfillment("2", FulfillmentStatus.PENDING, "FedEx", "2222222222"),
            createTestFulfillment("3", FulfillmentStatus.OPEN, "UPS", "3333333333")
        );
        
        GraphQLResponse<Object> mockResponse = createMockFulfillmentsListResponse(expectedFulfillments);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        List<Fulfillment> fulfillments = fulfillmentService.listFulfillmentsByOrder(
            authContext,
            orderId,
            10,
            null
        );
        
        // Then
        assertEquals(3, fulfillments.size());
        assertEquals(FulfillmentStatus.SUCCESS, fulfillments.get(0).getStatus());
        assertEquals(FulfillmentStatus.PENDING, fulfillments.get(1).getStatus());
        assertEquals(FulfillmentStatus.OPEN, fulfillments.get(2).getStatus());
    }
    
    @Test
    @DisplayName("이행 상태 업데이트 - 배송 중")
    void testUpdateFulfillmentTracking() {
        // Given
        String fulfillmentId = "gid://shopify/Fulfillment/123456";
        
        FulfillmentTrackingInput trackingUpdate = FulfillmentTrackingInput.builder()
            .notifyCustomer(true)
            .trackingInfo(FulfillmentTrackingInfo.builder()
                .company("DHL")
                .number("1234567890-UPDATE")
                .url("https://tracking.dhl.com/1234567890-UPDATE")
                .build())
            .build();
        
        Fulfillment updatedFulfillment = Fulfillment.builder()
            .id(new ID(fulfillmentId))
            .status(FulfillmentStatus.IN_TRANSIT)
            .displayStatus(FulfillmentDisplayStatus.IN_TRANSIT)
            .trackingCompany("DHL")
            .trackingNumbers(Arrays.asList("1234567890-UPDATE"))
            .trackingUrls(Arrays.asList("https://tracking.dhl.com/1234567890-UPDATE"))
            .inTransitAt(ZonedDateTime.now())
            .updatedAt(ZonedDateTime.now())
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockFulfillmentUpdateTrackingResponse(updatedFulfillment);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        Fulfillment fulfillment = fulfillmentService.updateFulfillmentTracking(
            authContext,
            fulfillmentId,
            trackingUpdate
        );
        
        // Then
        assertNotNull(fulfillment);
        assertEquals(FulfillmentStatus.IN_TRANSIT, fulfillment.getStatus());
        assertEquals("1234567890-UPDATE", fulfillment.getTrackingNumbers().get(0));
        assertNotNull(fulfillment.getInTransitAt());
    }
    
    @Test
    @DisplayName("이행 취소")
    void testCancelFulfillment() {
        // Given
        String fulfillmentId = "gid://shopify/Fulfillment/123456";
        
        Fulfillment cancelledFulfillment = Fulfillment.builder()
            .id(new ID(fulfillmentId))
            .status(FulfillmentStatus.CANCELLED)
            .displayStatus(FulfillmentDisplayStatus.CANCELLED)
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockFulfillmentCancelResponse(cancelledFulfillment);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        Fulfillment fulfillment = fulfillmentService.cancelFulfillment(authContext, fulfillmentId);
        
        // Then
        assertNotNull(fulfillment);
        assertEquals(FulfillmentStatus.CANCELLED, fulfillment.getStatus());
        assertEquals(FulfillmentDisplayStatus.CANCELLED, fulfillment.getDisplayStatus());
    }
    
    @Test
    @DisplayName("이행 주문 생성")
    void testCreateFulfillmentOrder() {
        // Given
        String orderId = "gid://shopify/Order/123456";
        
        FulfillmentOrderInput input = FulfillmentOrderInput.builder()
            .orderId(orderId)
            .fulfillmentOrderLineItems(Arrays.asList(
                FulfillmentOrderLineItemInput.builder()
                    .id("gid://shopify/LineItem/111")
                    .quantity(5)
                    .build()
            ))
            .build();
        
        FulfillmentOrder expectedFulfillmentOrder = FulfillmentOrder.builder()
            .id(new ID("gid://shopify/FulfillmentOrder/777888"))
            .status(FulfillmentOrderStatus.OPEN)
            .assignedLocation(Location.builder()
                .id(new ID("gid://shopify/Location/999"))
                .name("Main Warehouse")
                .build())
            .deliverBy(ZonedDateTime.now().plusDays(3))
            .createdAt(ZonedDateTime.now())
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockFulfillmentOrderCreateResponse(expectedFulfillmentOrder);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        FulfillmentOrder fulfillmentOrder = fulfillmentService.createFulfillmentOrder(
            authContext,
            input
        );
        
        // Then
        assertNotNull(fulfillmentOrder);
        assertEquals(FulfillmentOrderStatus.OPEN, fulfillmentOrder.getStatus());
        assertEquals("Main Warehouse", fulfillmentOrder.getAssignedLocation().getName());
        assertNotNull(fulfillmentOrder.getDeliverBy());
    }
    
    @Test
    @DisplayName("이행 서비스 목록 조회")
    void testListFulfillmentServices() {
        // Given
        List<FulfillmentService> expectedServices = Arrays.asList(
            FulfillmentService.builder()
                .id(new ID("gid://shopify/FulfillmentService/1"))
                .name("3PL Warehouse")
                .type(FulfillmentServiceType.THIRD_PARTY)
                .location(Location.builder()
                    .id(new ID("gid://shopify/Location/1"))
                    .name("3PL Warehouse Location")
                    .build())
                .active(true)
                .build(),
            FulfillmentService.builder()
                .id(new ID("gid://shopify/FulfillmentService/2"))
                .name("Manual Fulfillment")
                .type(FulfillmentServiceType.MANUAL)
                .active(true)
                .build()
        );
        
        GraphQLResponse<Object> mockResponse = createMockFulfillmentServicesListResponse(expectedServices);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        List<FulfillmentService> services = fulfillmentService.listFulfillmentServices(authContext);
        
        // Then
        assertEquals(2, services.size());
        assertEquals("3PL Warehouse", services.get(0).getName());
        assertEquals(FulfillmentServiceType.THIRD_PARTY, services.get(0).getType());
        assertEquals(FulfillmentServiceType.MANUAL, services.get(1).getType());
    }
    
    @Test
    @DisplayName("이행 이벤트 생성 - 배송 완료")
    void testCreateFulfillmentEvent() {
        // Given
        String fulfillmentId = "gid://shopify/Fulfillment/123456";
        
        FulfillmentEventInput eventInput = FulfillmentEventInput.builder()
            .fulfillmentId(fulfillmentId)
            .status(FulfillmentEventStatus.DELIVERED)
            .happenedAt(ZonedDateTime.now())
            .message("Package delivered to customer")
            .build();
        
        FulfillmentEvent expectedEvent = FulfillmentEvent.builder()
            .id(new ID("gid://shopify/FulfillmentEvent/999"))
            .status(FulfillmentEventStatus.DELIVERED)
            .happenedAt(ZonedDateTime.now())
            .message("Package delivered to customer")
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockFulfillmentEventCreateResponse(expectedEvent);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        FulfillmentEvent event = fulfillmentService.createFulfillmentEvent(authContext, eventInput);
        
        // Then
        assertNotNull(event);
        assertEquals(FulfillmentEventStatus.DELIVERED, event.getStatus());
        assertEquals("Package delivered to customer", event.getMessage());
    }
    
    @Test
    @DisplayName("이행 생성 실패 - 재고 부족")
    void testCreateFulfillment_InsufficientInventory() {
        // Given
        FulfillmentInput input = FulfillmentInput.builder()
            .orderId("gid://shopify/Order/123456")
            .notifyCustomer(true)
            .build();
        
        GraphQLResponse<Object> errorResponse = createMockFulfillmentCreateErrorResponse(
            "lineItems",
            "Insufficient inventory to fulfill order"
        );
        when(graphQLClient.execute(any(), any())).thenReturn(errorResponse);
        
        // When & Then
        ShopifyApiException exception = assertThrows(ShopifyApiException.class, () -> {
            fulfillmentService.createFulfillment(authContext, input);
        });
        
        assertTrue(exception.getMessage().contains("Insufficient inventory"));
    }
    
    @Test
    @DisplayName("이행 주문 이동 - 다른 위치로")
    void testMoveFulfillmentOrder() {
        // Given
        String fulfillmentOrderId = "gid://shopify/FulfillmentOrder/123456";
        String newLocationId = "gid://shopify/Location/999";
        
        FulfillmentOrderMoveInput moveInput = FulfillmentOrderMoveInput.builder()
            .fulfillmentOrderId(fulfillmentOrderId)
            .newLocationId(newLocationId)
            .build();
        
        FulfillmentOrder movedOrder = FulfillmentOrder.builder()
            .id(new ID(fulfillmentOrderId))
            .status(FulfillmentOrderStatus.OPEN)
            .assignedLocation(Location.builder()
                .id(new ID(newLocationId))
                .name("New Warehouse")
                .build())
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockFulfillmentOrderMoveResponse(movedOrder);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        FulfillmentOrder result = fulfillmentService.moveFulfillmentOrder(authContext, moveInput);
        
        // Then
        assertNotNull(result);
        assertEquals(newLocationId, result.getAssignedLocation().getId().getValue());
        assertEquals("New Warehouse", result.getAssignedLocation().getName());
    }
    
    // Helper methods
    private Fulfillment createTestFulfillment(String id, FulfillmentStatus status, 
                                             String carrier, String trackingNumber) {
        return Fulfillment.builder()
            .id(new ID("gid://shopify/Fulfillment/" + id))
            .status(status)
            .trackingCompany(carrier)
            .trackingNumbers(Arrays.asList(trackingNumber))
            .createdAt(ZonedDateTime.now())
            .build();
    }
    
    private GraphQLResponse<Object> createMockFulfillmentCreateResponse(Fulfillment fulfillment) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "fulfillmentCreateV2", Map.of(
                "fulfillment", fulfillment,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockFulfillmentGetResponse(Fulfillment fulfillment) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of("fulfillment", fulfillment));
        return response;
    }
    
    private GraphQLResponse<Object> createMockFulfillmentsListResponse(List<Fulfillment> fulfillments) {
        List<Map<String, Object>> edges = new ArrayList<>();
        for (Fulfillment fulfillment : fulfillments) {
            edges.add(Map.of("node", fulfillment));
        }
        
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "fulfillments", Map.of(
                "edges", edges,
                "pageInfo", Map.of("hasNextPage", false)
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockFulfillmentUpdateTrackingResponse(Fulfillment fulfillment) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "fulfillmentTrackingInfoUpdateV2", Map.of(
                "fulfillment", fulfillment,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockFulfillmentCancelResponse(Fulfillment fulfillment) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "fulfillmentCancel", Map.of(
                "fulfillment", fulfillment,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockFulfillmentOrderCreateResponse(FulfillmentOrder fulfillmentOrder) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "fulfillmentOrderCreate", Map.of(
                "fulfillmentOrder", fulfillmentOrder,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockFulfillmentServicesListResponse(List<FulfillmentService> services) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "fulfillmentServices", services
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockFulfillmentEventCreateResponse(FulfillmentEvent event) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "fulfillmentEventCreate", Map.of(
                "fulfillmentEvent", event,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockFulfillmentCreateErrorResponse(String field, String message) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "fulfillmentCreateV2", Map.of(
                "fulfillment", null,
                "userErrors", Arrays.asList(
                    Map.of(
                        "field", Arrays.asList(field),
                        "message", message,
                        "code", "INSUFFICIENT_INVENTORY"
                    )
                )
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockFulfillmentOrderMoveResponse(FulfillmentOrder fulfillmentOrder) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "fulfillmentOrderMove", Map.of(
                "fulfillmentOrder", fulfillmentOrder,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
}