package com.shopify.sdk.service;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.model.common.*;
import com.shopify.sdk.model.graphql.GraphQLRequest;
import com.shopify.sdk.model.graphql.GraphQLResponse;
import com.shopify.sdk.model.order.*;
import com.shopify.sdk.service.order.OrderService;
import okhttp3.*;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * SDK 사용자 관점에서의 OrderService 테스트
 * 실제 SDK 사용 시나리오를 기반으로 작성
 */
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    
    @Mock
    private ShopifyGraphQLClient graphQLClient;
    
    private OrderService orderService;
    private ShopifyAuthContext authContext;
    
    // Mock 서버를 사용한 실제 HTTP 통신 테스트
    private MockWebServer mockServer;
    
    @BeforeEach
    void setUp() {
        orderService = new OrderService(graphQLClient);
        authContext = ShopifyAuthContext.builder()
            .shopDomain("test-store.myshopify.com")
            .accessToken("test-token")
            .apiVersion("2025-07")
            .build();
    }
    
    @Test
    @DisplayName("주문 목록 조회 - SDK 사용자가 가장 많이 사용하는 시나리오")
    void testGetOrders_CommonUseCase() {
        // Given: 일반적인 주문 목록 응답
        List<Order> expectedOrders = Arrays.asList(
            createTestOrder("1001", "John Doe", "109.99", OrderFinancialStatus.PAID),
            createTestOrder("1002", "Jane Smith", "59.99", OrderFinancialStatus.PENDING)
        );
        
        GraphQLResponse<Object> mockResponse = createMockOrdersResponse(expectedOrders);
        when(graphQLClient.execute(any(GraphQLRequest.class), any())).thenReturn(mockResponse);
        
        // When: SDK 사용자가 주문을 조회
        List<Order> orders = orderService.getOrders(
            authContext,
            10,                           // 10개씩 페이징
            null,                         // 첫 페이지
            "created_at:>2024-01-01",    // 2024년 이후 주문만
            OrderSortKeys.CREATED_AT,     // 생성일 기준 정렬
            true                          // 최신순
        );
        
        // Then: 기대하는 결과 검증
        assertEquals(2, orders.size());
        assertEquals("John Doe", orders.get(0).getCustomer().getDisplayName());
        assertEquals(new BigDecimal("109.99"), orders.get(0).getTotalPrice().getAmount());
        assertEquals(OrderFinancialStatus.PAID, orders.get(0).getFinancialStatus());
    }
    
    @Test
    @DisplayName("주문 상세 조회 - 특정 주문의 모든 정보 가져오기")
    void testGetOrderDetails() {
        // Given: 상세 정보가 포함된 주문
        String orderId = "gid://shopify/Order/123456";
        Order expectedOrder = Order.builder()
            .id(new ID(orderId))
            .name("#1001")
            .email("customer@example.com")
            .totalPrice(Money.builder()
                .amount(new BigDecimal("199.99"))
                .currencyCode(CurrencyCode.USD)
                .build())
            .lineItems(createTestLineItems())
            .shippingAddress(createTestAddress())
            .fulfillmentStatus(OrderFulfillmentStatus.UNFULFILLED)
            .financialStatus(OrderFinancialStatus.PAID)
            .createdAt(ZonedDateTime.now())
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockOrderResponse(expectedOrder);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When: 주문 상세 조회
        Order order = orderService.getOrder(authContext, orderId);
        
        // Then: 모든 필드가 올바르게 매핑되었는지 확인
        assertNotNull(order);
        assertEquals("#1001", order.getName());
        assertEquals("customer@example.com", order.getEmail());
        assertEquals(2, order.getLineItems().size());
        assertNotNull(order.getShippingAddress());
        assertEquals("123 Main St", order.getShippingAddress().getAddress1());
    }
    
    @Test
    @DisplayName("주문 이행(Fulfillment) 생성 - 실제 배송 처리 시나리오")
    void testCreateFulfillment() {
        // Given: 이행 요청 데이터
        FulfillmentInput input = FulfillmentInput.builder()
            .orderId("gid://shopify/Order/123456")
            .lineItems(Arrays.asList(
                FulfillmentLineItemInput.builder()
                    .id("gid://shopify/LineItem/111")
                    .quantity(2)
                    .build()
            ))
            .trackingInfo(TrackingInfo.builder()
                .company("DHL")
                .number("1234567890")
                .url("https://tracking.dhl.com/1234567890")
                .build())
            .notifyCustomer(true)
            .build();
        
        Fulfillment expectedFulfillment = Fulfillment.builder()
            .id(new ID("gid://shopify/Fulfillment/999"))
            .status(FulfillmentStatus.SUCCESS)
            .trackingCompany("DHL")
            .trackingNumber("1234567890")
            .createdAt(ZonedDateTime.now())
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockFulfillmentResponse(expectedFulfillment);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When: 이행 생성
        Fulfillment fulfillment = orderService.createFulfillment(authContext, input);
        
        // Then: 이행이 성공적으로 생성되었는지 확인
        assertNotNull(fulfillment);
        assertEquals(FulfillmentStatus.SUCCESS, fulfillment.getStatus());
        assertEquals("DHL", fulfillment.getTrackingCompany());
        assertEquals("1234567890", fulfillment.getTrackingNumber());
    }
    
    @Test
    @DisplayName("에러 처리 - 잘못된 주문 ID로 조회 시")
    void testGetOrder_InvalidId() {
        // Given: GraphQL 에러 응답
        GraphQLResponse<Object> errorResponse = new GraphQLResponse<>();
        errorResponse.setErrors(Arrays.asList(
            GraphQLError.builder()
                .message("Order not found")
                .extensions(Map.of("code", "NOT_FOUND"))
                .build()
        ));
        
        when(graphQLClient.execute(any(), any())).thenReturn(errorResponse);
        
        // When & Then: 예외가 발생하는지 확인
        assertThrows(ShopifyApiException.class, () -> {
            orderService.getOrder(authContext, "invalid-id");
        });
    }
    
    @Test
    @DisplayName("페이징 처리 - 대량 주문 조회 시나리오")
    void testPaginatedOrderRetrieval() {
        // Given: 페이징 정보가 포함된 응답
        PageInfo pageInfo = PageInfo.builder()
            .hasNextPage(true)
            .endCursor("eyJsYXN0X2lkIjoxMDAxfQ==")
            .build();
        
        GraphQLResponse<Object> firstPageResponse = createMockOrdersResponseWithPaging(
            Arrays.asList(createTestOrder("1001", "Customer 1", "100.00", OrderFinancialStatus.PAID)),
            pageInfo
        );
        
        when(graphQLClient.execute(any(), any())).thenReturn(firstPageResponse);
        
        // When: 첫 페이지 조회
        OrderConnection connection = orderService.getOrdersWithPaging(authContext, 1, null);
        
        // Then: 페이징 정보 확인
        assertNotNull(connection);
        assertTrue(connection.getPageInfo().isHasNextPage());
        assertNotNull(connection.getPageInfo().getEndCursor());
        assertEquals(1, connection.getEdges().size());
    }
    
    @Test
    @DisplayName("Rate Limit 처리 - API 제한 초과 시나리오")
    void testRateLimitHandling() {
        // Given: Rate limit 에러
        when(graphQLClient.execute(any(), any()))
            .thenThrow(new ShopifyRateLimitException("Rate limit exceeded", 2.0));
        
        // When & Then: Rate limit 예외 처리
        ShopifyRateLimitException exception = assertThrows(
            ShopifyRateLimitException.class,
            () -> orderService.getOrders(authContext, 10, null, null, null, false)
        );
        
        assertEquals(2.0, exception.getRetryAfter());
    }
    
    // 테스트 헬퍼 메서드들
    private Order createTestOrder(String orderNumber, String customerName, String totalPrice, OrderFinancialStatus status) {
        return Order.builder()
            .id(new ID("gid://shopify/Order/" + orderNumber))
            .name("#" + orderNumber)
            .customer(Customer.builder()
                .displayName(customerName)
                .email(customerName.toLowerCase().replace(" ", ".") + "@example.com")
                .build())
            .totalPrice(Money.builder()
                .amount(new BigDecimal(totalPrice))
                .currencyCode(CurrencyCode.USD)
                .build())
            .financialStatus(status)
            .fulfillmentStatus(OrderFulfillmentStatus.UNFULFILLED)
            .createdAt(ZonedDateTime.now())
            .build();
    }
    
    private List<LineItem> createTestLineItems() {
        return Arrays.asList(
            LineItem.builder()
                .id(new ID("gid://shopify/LineItem/111"))
                .title("Product 1")
                .quantity(2)
                .price(Money.builder()
                    .amount(new BigDecimal("49.99"))
                    .currencyCode(CurrencyCode.USD)
                    .build())
                .build(),
            LineItem.builder()
                .id(new ID("gid://shopify/LineItem/222"))
                .title("Product 2")
                .quantity(1)
                .price(Money.builder()
                    .amount(new BigDecimal("99.99"))
                    .currencyCode(CurrencyCode.USD)
                    .build())
                .build()
        );
    }
    
    private Address createTestAddress() {
        return Address.builder()
            .address1("123 Main St")
            .city("New York")
            .province("NY")
            .country("US")
            .zip("10001")
            .build();
    }
    
    private GraphQLResponse<Object> createMockOrdersResponse(List<Order> orders) {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> ordersData = new HashMap<>();
        List<Map<String, Object>> edges = new ArrayList<>();
        
        for (Order order : orders) {
            edges.add(Map.of("node", order, "cursor", "cursor_" + order.getId().getValue()));
        }
        
        ordersData.put("edges", edges);
        ordersData.put("pageInfo", Map.of("hasNextPage", false));
        data.put("orders", ordersData);
        
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(data);
        return response;
    }
    
    private GraphQLResponse<Object> createMockOrderResponse(Order order) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of("order", order));
        return response;
    }
    
    private GraphQLResponse<Object> createMockFulfillmentResponse(Fulfillment fulfillment) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "fulfillmentCreate", Map.of(
                "fulfillment", fulfillment,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockOrdersResponseWithPaging(List<Order> orders, PageInfo pageInfo) {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> ordersData = new HashMap<>();
        List<Map<String, Object>> edges = new ArrayList<>();
        
        for (Order order : orders) {
            edges.add(Map.of("node", order, "cursor", "cursor_" + order.getId().getValue()));
        }
        
        ordersData.put("edges", edges);
        ordersData.put("pageInfo", pageInfo);
        data.put("orders", ordersData);
        
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(data);
        return response;
    }
}