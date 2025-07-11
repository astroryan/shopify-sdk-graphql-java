package com.shopify.sdk.service;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.fixtures.ShopifyTestFixtures;
import com.shopify.sdk.model.common.*;
import com.shopify.sdk.model.customer.*;
import com.shopify.sdk.model.graphql.GraphQLRequest;
import com.shopify.sdk.model.graphql.GraphQLResponse;
import com.shopify.sdk.service.customer.CustomerService;
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
 * CustomerService 단위 테스트
 * 고객 관리 관련 모든 기능을 테스트합니다.
 */
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    
    @Mock
    private ShopifyGraphQLClient graphQLClient;
    
    private CustomerService customerService;
    private ShopifyAuthContext authContext;
    
    @BeforeEach
    void setUp() {
        customerService = new CustomerService(graphQLClient);
        authContext = ShopifyAuthContext.builder()
            .shopDomain("test-store.myshopify.com")
            .accessToken("test-token")
            .apiVersion("2025-07")
            .build();
    }
    
    @Test
    @DisplayName("고객 생성 - 성공 시나리오")
    void testCreateCustomer_Success() {
        // Given
        CustomerInput input = CustomerInput.builder()
            .email("new.customer@example.com")
            .firstName("Jane")
            .lastName("Smith")
            .phone("+1-555-987-6543")
            .acceptsMarketing(true)
            .tags(Arrays.asList("new", "potential"))
            .note("Created via SDK test")
            .build();
        
        Customer expectedCustomer = Customer.builder()
            .id(new ID("gid://shopify/Customer/9876543210"))
            .email("new.customer@example.com")
            .firstName("Jane")
            .lastName("Smith")
            .displayName("Jane Smith")
            .phone("+1-555-987-6543")
            .acceptsMarketing(true)
            .state(CustomerState.ENABLED)
            .verifiedEmail(false)
            .createdAt(ZonedDateTime.now())
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockCustomerCreateResponse(expectedCustomer);
        when(graphQLClient.execute(any(GraphQLRequest.class), any())).thenReturn(mockResponse);
        
        // When
        Customer customer = customerService.createCustomer(authContext, input);
        
        // Then
        assertNotNull(customer);
        assertEquals("new.customer@example.com", customer.getEmail());
        assertEquals("Jane", customer.getFirstName());
        assertEquals("Smith", customer.getLastName());
        assertEquals(CustomerState.ENABLED, customer.getState());
        
        // Verify GraphQL request
        ArgumentCaptor<GraphQLRequest> requestCaptor = ArgumentCaptor.forClass(GraphQLRequest.class);
        verify(graphQLClient).execute(requestCaptor.capture(), any());
        
        GraphQLRequest request = requestCaptor.getValue();
        assertEquals(input, request.getVariables().get("input"));
    }
    
    @Test
    @DisplayName("고객 검색 - 이메일로 검색")
    void testSearchCustomers_ByEmail() {
        // Given
        List<Customer> expectedCustomers = Arrays.asList(
            createTestCustomer("1", "john@example.com", "John", "Doe"),
            createTestCustomer("2", "john.smith@example.com", "John", "Smith")
        );
        
        GraphQLResponse<Object> mockResponse = createMockCustomersSearchResponse(expectedCustomers);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        List<Customer> customers = customerService.searchCustomers(
            authContext,
            "email:john*",
            10,
            null
        );
        
        // Then
        assertEquals(2, customers.size());
        assertTrue(customers.stream().allMatch(c -> c.getEmail().contains("john")));
    }
    
    @Test
    @DisplayName("고객 업데이트 - 부분 업데이트")
    void testUpdateCustomer_PartialUpdate() {
        // Given
        String customerId = "gid://shopify/Customer/123456";
        CustomerInput updateInput = CustomerInput.builder()
            .phone("+1-555-999-8888")
            .acceptsMarketing(false)
            .tags(Arrays.asList("vip", "updated"))
            .build();
        
        Customer updatedCustomer = Customer.builder()
            .id(new ID(customerId))
            .email("existing@example.com")
            .phone("+1-555-999-8888")
            .acceptsMarketing(false)
            .tags(Arrays.asList("vip", "updated"))
            .updatedAt(ZonedDateTime.now())
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockCustomerUpdateResponse(updatedCustomer);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        Customer result = customerService.updateCustomer(authContext, customerId, updateInput);
        
        // Then
        assertNotNull(result);
        assertEquals("+1-555-999-8888", result.getPhone());
        assertFalse(result.isAcceptsMarketing());
        assertTrue(result.getTags().contains("vip"));
    }
    
    @Test
    @DisplayName("고객 주소 추가")
    void testAddCustomerAddress() {
        // Given
        String customerId = "gid://shopify/Customer/123456";
        CustomerAddressInput addressInput = CustomerAddressInput.builder()
            .address1("456 Oak Avenue")
            .city("Los Angeles")
            .province("California")
            .provinceCode("CA")
            .country("United States")
            .countryCode("US")
            .zip("90001")
            .phone("+1-555-123-4567")
            .isDefault(true)
            .build();
        
        CustomerAddress expectedAddress = CustomerAddress.builder()
            .id(new ID("gid://shopify/CustomerAddress/999"))
            .address1("456 Oak Avenue")
            .city("Los Angeles")
            .province("California")
            .country("United States")
            .zip("90001")
            .isDefault(true)
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockAddressCreateResponse(expectedAddress);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        CustomerAddress address = customerService.addCustomerAddress(authContext, customerId, addressInput);
        
        // Then
        assertNotNull(address);
        assertEquals("456 Oak Avenue", address.getAddress1());
        assertEquals("Los Angeles", address.getCity());
        assertTrue(address.isDefault());
    }
    
    @Test
    @DisplayName("고객 삭제")
    void testDeleteCustomer() {
        // Given
        String customerId = "gid://shopify/Customer/123456";
        
        GraphQLResponse<Object> mockResponse = createMockCustomerDeleteResponse(customerId);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        String deletedId = customerService.deleteCustomer(authContext, customerId);
        
        // Then
        assertEquals(customerId, deletedId);
    }
    
    @Test
    @DisplayName("고객 생성 실패 - 중복 이메일")
    void testCreateCustomer_DuplicateEmail() {
        // Given
        CustomerInput input = CustomerInput.builder()
            .email("existing@example.com")
            .firstName("Test")
            .lastName("User")
            .build();
        
        GraphQLResponse<Object> errorResponse = createMockCustomerCreateErrorResponse(
            "email", 
            "Email has already been taken"
        );
        when(graphQLClient.execute(any(), any())).thenReturn(errorResponse);
        
        // When & Then
        ShopifyApiException exception = assertThrows(ShopifyApiException.class, () -> {
            customerService.createCustomer(authContext, input);
        });
        
        assertTrue(exception.getMessage().contains("Email has already been taken"));
    }
    
    @Test
    @DisplayName("고객 마케팅 동의 업데이트")
    void testUpdateCustomerMarketing() {
        // Given
        String customerId = "gid://shopify/Customer/123456";
        CustomerMarketingInput marketingInput = CustomerMarketingInput.builder()
            .acceptsMarketing(true)
            .marketingOptInLevel(MarketingOptInLevel.SINGLE_OPT_IN)
            .consentUpdatedAt(ZonedDateTime.now())
            .build();
        
        // Mock response
        GraphQLResponse<Object> mockResponse = new GraphQLResponse<>();
        mockResponse.setData(Map.of(
            "customerUpdate", Map.of(
                "customer", Map.of(
                    "id", customerId,
                    "acceptsMarketing", true,
                    "marketingOptInLevel", "SINGLE_OPT_IN"
                ),
                "userErrors", new ArrayList<>()
            )
        ));
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        boolean result = customerService.updateCustomerMarketing(authContext, customerId, marketingInput);
        
        // Then
        assertTrue(result);
    }
    
    @Test
    @DisplayName("고객 태그 관리")
    void testManageCustomerTags() {
        // Given
        String customerId = "gid://shopify/Customer/123456";
        List<String> tagsToAdd = Arrays.asList("premium", "loyal");
        List<String> tagsToRemove = Arrays.asList("new");
        
        Customer updatedCustomer = Customer.builder()
            .id(new ID(customerId))
            .tags(Arrays.asList("premium", "loyal", "existing"))
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockCustomerUpdateResponse(updatedCustomer);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        Customer result = customerService.updateCustomerTags(authContext, customerId, tagsToAdd, tagsToRemove);
        
        // Then
        assertTrue(result.getTags().containsAll(Arrays.asList("premium", "loyal")));
        assertFalse(result.getTags().contains("new"));
    }
    
    // Helper methods
    private Customer createTestCustomer(String id, String email, String firstName, String lastName) {
        return Customer.builder()
            .id(new ID("gid://shopify/Customer/" + id))
            .email(email)
            .firstName(firstName)
            .lastName(lastName)
            .displayName(firstName + " " + lastName)
            .state(CustomerState.ENABLED)
            .createdAt(ZonedDateTime.now())
            .build();
    }
    
    private GraphQLResponse<Object> createMockCustomerCreateResponse(Customer customer) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "customerCreate", Map.of(
                "customer", customer,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockCustomersSearchResponse(List<Customer> customers) {
        List<Map<String, Object>> edges = new ArrayList<>();
        for (Customer customer : customers) {
            edges.add(Map.of("node", customer, "cursor", "cursor_" + customer.getId().getValue()));
        }
        
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "customers", Map.of(
                "edges", edges,
                "pageInfo", Map.of("hasNextPage", false)
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockCustomerUpdateResponse(Customer customer) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "customerUpdate", Map.of(
                "customer", customer,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockAddressCreateResponse(CustomerAddress address) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "customerAddressCreate", Map.of(
                "customerAddress", address,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockCustomerDeleteResponse(String customerId) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "customerDelete", Map.of(
                "deletedCustomerId", customerId,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockCustomerCreateErrorResponse(String field, String message) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "customerCreate", Map.of(
                "customer", null,
                "userErrors", Arrays.asList(
                    Map.of(
                        "field", Arrays.asList(field),
                        "message", message,
                        "code", "TAKEN"
                    )
                )
            )
        ));
        return response;
    }
}