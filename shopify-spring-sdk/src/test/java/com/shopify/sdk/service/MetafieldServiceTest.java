package com.shopify.sdk.service;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.common.ID;
import com.shopify.sdk.model.graphql.GraphQLRequest;
import com.shopify.sdk.model.graphql.GraphQLResponse;
import com.shopify.sdk.model.metafield.*;
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
 * MetafieldService 테스트
 * 메타필드(커스텀 데이터 필드) 관리 기능을 테스트합니다.
 */
@ExtendWith(MockitoExtension.class)
class MetafieldServiceTest {
    
    @Mock
    private ShopifyGraphQLClient graphQLClient;
    
    private MetafieldService metafieldService;
    private ShopifyAuthContext authContext;
    
    @BeforeEach
    void setUp() {
        metafieldService = new MetafieldService(graphQLClient);
        authContext = ShopifyAuthContext.builder()
            .shopDomain("test-store.myshopify.com")
            .accessToken("test-token")
            .apiVersion("2025-07")
            .build();
    }
    
    @Test
    @DisplayName("메타필드 생성 - 단일 값")
    void testCreateMetafield_SingleValue() {
        // Given
        MetafieldInput input = MetafieldInput.builder()
            .namespace("custom_data")
            .key("special_instructions")
            .value("Handle with care")
            .type("single_line_text_field")
            .ownerType(MetafieldOwnerType.PRODUCT)
            .ownerId("gid://shopify/Product/123456")
            .build();
        
        Metafield expectedMetafield = Metafield.builder()
            .id(new ID("gid://shopify/Metafield/999888"))
            .namespace("custom_data")
            .key("special_instructions")
            .value("Handle with care")
            .type("single_line_text_field")
            .createdAt(ZonedDateTime.now())
            .updatedAt(ZonedDateTime.now())
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockMetafieldCreateResponse(expectedMetafield);
        when(graphQLClient.execute(any(GraphQLRequest.class), any())).thenReturn(mockResponse);
        
        // When
        Metafield metafield = metafieldService.createMetafield(authContext, input);
        
        // Then
        assertNotNull(metafield);
        assertEquals("custom_data", metafield.getNamespace());
        assertEquals("special_instructions", metafield.getKey());
        assertEquals("Handle with care", metafield.getValue());
        assertEquals("single_line_text_field", metafield.getType());
        
        // Verify GraphQL request
        ArgumentCaptor<GraphQLRequest> requestCaptor = ArgumentCaptor.forClass(GraphQLRequest.class);
        verify(graphQLClient).execute(requestCaptor.capture(), any());
        
        GraphQLRequest request = requestCaptor.getValue();
        assertTrue(request.getQuery().contains("metafieldCreate"));
        assertEquals(input, request.getVariables().get("input"));
    }
    
    @Test
    @DisplayName("메타필드 생성 - JSON 값")
    void testCreateMetafield_JsonValue() {
        // Given
        Map<String, Object> jsonValue = Map.of(
            "color", "red",
            "size", "large",
            "features", Arrays.asList("waterproof", "lightweight")
        );
        
        MetafieldInput input = MetafieldInput.builder()
            .namespace("product_specs")
            .key("attributes")
            .value(jsonValue)
            .type("json")
            .ownerType(MetafieldOwnerType.PRODUCT)
            .ownerId("gid://shopify/Product/123456")
            .build();
        
        Metafield expectedMetafield = Metafield.builder()
            .id(new ID("gid://shopify/Metafield/999889"))
            .namespace("product_specs")
            .key("attributes")
            .value(jsonValue)
            .type("json")
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockMetafieldCreateResponse(expectedMetafield);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        Metafield metafield = metafieldService.createMetafield(authContext, input);
        
        // Then
        assertNotNull(metafield);
        assertEquals("json", metafield.getType());
        assertTrue(metafield.getValue() instanceof Map);
        Map<String, Object> resultValue = (Map<String, Object>) metafield.getValue();
        assertEquals("red", resultValue.get("color"));
    }
    
    @Test
    @DisplayName("메타필드 일괄 생성")
    void testCreateMetafields_Bulk() {
        // Given
        List<MetafieldInput> inputs = Arrays.asList(
            MetafieldInput.builder()
                .namespace("custom")
                .key("field1")
                .value("value1")
                .type("single_line_text_field")
                .ownerId("gid://shopify/Product/111")
                .build(),
            MetafieldInput.builder()
                .namespace("custom")
                .key("field2")
                .value("123")
                .type("number_integer")
                .ownerId("gid://shopify/Product/111")
                .build()
        );
        
        List<Metafield> expectedMetafields = Arrays.asList(
            Metafield.builder()
                .id(new ID("gid://shopify/Metafield/1"))
                .namespace("custom")
                .key("field1")
                .value("value1")
                .build(),
            Metafield.builder()
                .id(new ID("gid://shopify/Metafield/2"))
                .namespace("custom")
                .key("field2")
                .value("123")
                .build()
        );
        
        GraphQLResponse<Object> mockResponse = createMockMetafieldsBulkCreateResponse(expectedMetafields);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        List<Metafield> metafields = metafieldService.createMetafields(authContext, inputs);
        
        // Then
        assertEquals(2, metafields.size());
        assertEquals("field1", metafields.get(0).getKey());
        assertEquals("field2", metafields.get(1).getKey());
    }
    
    @Test
    @DisplayName("메타필드 조회 - ID로")
    void testGetMetafield_ById() {
        // Given
        String metafieldId = "gid://shopify/Metafield/123456";
        
        Metafield expectedMetafield = Metafield.builder()
            .id(new ID(metafieldId))
            .namespace("custom_data")
            .key("special_note")
            .value("Important information")
            .type("multi_line_text_field")
            .owner(Map.of(
                "__typename", "Product",
                "id", "gid://shopify/Product/789"
            ))
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockMetafieldGetResponse(expectedMetafield);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        Metafield metafield = metafieldService.getMetafield(authContext, metafieldId);
        
        // Then
        assertNotNull(metafield);
        assertEquals(metafieldId, metafield.getId().getValue());
        assertEquals("custom_data", metafield.getNamespace());
        assertEquals("special_note", metafield.getKey());
    }
    
    @Test
    @DisplayName("메타필드 목록 조회 - 소유자별")
    void testListMetafieldsByOwner() {
        // Given
        String ownerId = "gid://shopify/Product/123456";
        
        List<Metafield> expectedMetafields = Arrays.asList(
            createTestMetafield("1", "specs", "weight", "2.5kg"),
            createTestMetafield("2", "specs", "dimensions", "10x20x30cm"),
            createTestMetafield("3", "custom", "care_instructions", "Machine washable")
        );
        
        GraphQLResponse<Object> mockResponse = createMockMetafieldsListResponse(expectedMetafields);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        List<Metafield> metafields = metafieldService.listMetafieldsByOwner(
            authContext,
            ownerId,
            null,
            10,
            null
        );
        
        // Then
        assertEquals(3, metafields.size());
        assertEquals("weight", metafields.get(0).getKey());
        assertEquals("dimensions", metafields.get(1).getKey());
        assertEquals("care_instructions", metafields.get(2).getKey());
    }
    
    @Test
    @DisplayName("메타필드 목록 조회 - 네임스페이스별 필터링")
    void testListMetafieldsByNamespace() {
        // Given
        String ownerId = "gid://shopify/Product/123456";
        String namespace = "specs";
        
        List<Metafield> allMetafields = Arrays.asList(
            createTestMetafield("1", "specs", "weight", "2.5kg"),
            createTestMetafield("2", "specs", "dimensions", "10x20x30cm"),
            createTestMetafield("3", "custom", "care_instructions", "Machine washable")
        );
        
        GraphQLResponse<Object> mockResponse = createMockMetafieldsListResponse(allMetafields);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        List<Metafield> metafields = metafieldService.listMetafieldsByOwner(
            authContext,
            ownerId,
            namespace,
            10,
            null
        );
        
        // Then
        assertEquals(2, metafields.size());
        assertTrue(metafields.stream().allMatch(m -> "specs".equals(m.getNamespace())));
    }
    
    @Test
    @DisplayName("메타필드 업데이트")
    void testUpdateMetafield() {
        // Given
        String metafieldId = "gid://shopify/Metafield/123456";
        
        MetafieldUpdateInput updateInput = MetafieldUpdateInput.builder()
            .id(metafieldId)
            .value("Updated value")
            .build();
        
        Metafield updatedMetafield = Metafield.builder()
            .id(new ID(metafieldId))
            .namespace("custom_data")
            .key("special_note")
            .value("Updated value")
            .type("single_line_text_field")
            .updatedAt(ZonedDateTime.now())
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockMetafieldUpdateResponse(updatedMetafield);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        Metafield metafield = metafieldService.updateMetafield(authContext, updateInput);
        
        // Then
        assertNotNull(metafield);
        assertEquals("Updated value", metafield.getValue());
    }
    
    @Test
    @DisplayName("메타필드 삭제 - 단일")
    void testDeleteMetafield() {
        // Given
        String metafieldId = "gid://shopify/Metafield/123456";
        
        GraphQLResponse<Object> mockResponse = createMockMetafieldDeleteResponse(metafieldId);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        String deletedId = metafieldService.deleteMetafield(authContext, metafieldId);
        
        // Then
        assertEquals(metafieldId, deletedId);
    }
    
    @Test
    @DisplayName("메타필드 일괄 삭제")
    void testDeleteMetafields_Bulk() {
        // Given
        List<String> metafieldIds = Arrays.asList(
            "gid://shopify/Metafield/111",
            "gid://shopify/Metafield/222",
            "gid://shopify/Metafield/333"
        );
        
        GraphQLResponse<Object> mockResponse = createMockMetafieldsBulkDeleteResponse(metafieldIds);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        List<String> deletedIds = metafieldService.deleteMetafields(authContext, metafieldIds);
        
        // Then
        assertEquals(3, deletedIds.size());
        assertEquals(metafieldIds, deletedIds);
    }
    
    @Test
    @DisplayName("메타필드 정의 조회")
    void testGetMetafieldDefinition() {
        // Given
        String definitionId = "gid://shopify/MetafieldDefinition/123456";
        
        MetafieldDefinition expectedDefinition = MetafieldDefinition.builder()
            .id(new ID(definitionId))
            .name("Product Specifications")
            .namespace("product_specs")
            .key("technical_details")
            .type(MetafieldDefinitionType.builder()
                .name("json")
                .supportedValidations(Arrays.asList(
                    MetafieldDefinitionValidation.builder()
                        .name("json_schema")
                        .type("json")
                        .build()
                ))
                .build())
            .description("Technical specifications for products")
            .ownerType(MetafieldOwnerType.PRODUCT)
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockMetafieldDefinitionResponse(expectedDefinition);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        MetafieldDefinition definition = metafieldService.getMetafieldDefinition(
            authContext,
            definitionId
        );
        
        // Then
        assertNotNull(definition);
        assertEquals("Product Specifications", definition.getName());
        assertEquals("product_specs", definition.getNamespace());
        assertEquals("technical_details", definition.getKey());
    }
    
    @Test
    @DisplayName("메타필드 생성 실패 - 잘못된 타입")
    void testCreateMetafield_InvalidType() {
        // Given
        MetafieldInput input = MetafieldInput.builder()
            .namespace("custom")
            .key("test")
            .value("not a number")
            .type("number_integer") // 숫자 타입인데 문자열 값
            .ownerId("gid://shopify/Product/123")
            .build();
        
        GraphQLResponse<Object> errorResponse = createMockMetafieldCreateErrorResponse(
            "value",
            "Value must be a valid integer"
        );
        when(graphQLClient.execute(any(), any())).thenReturn(errorResponse);
        
        // When & Then
        ShopifyApiException exception = assertThrows(ShopifyApiException.class, () -> {
            metafieldService.createMetafield(authContext, input);
        });
        
        assertTrue(exception.getMessage().contains("Value must be a valid integer"));
    }
    
    @Test
    @DisplayName("메타필드 Upsert - 생성 또는 업데이트")
    void testUpsertMetafield() {
        // Given
        MetafieldInput input = MetafieldInput.builder()
            .namespace("inventory")
            .key("warehouse_location")
            .value("A-12-B")
            .type("single_line_text_field")
            .ownerType(MetafieldOwnerType.PRODUCT_VARIANT)
            .ownerId("gid://shopify/ProductVariant/987654")
            .build();
        
        Metafield upsertedMetafield = Metafield.builder()
            .id(new ID("gid://shopify/Metafield/555666"))
            .namespace("inventory")
            .key("warehouse_location")
            .value("A-12-B")
            .type("single_line_text_field")
            .build();
        
        GraphQLResponse<Object> mockResponse = createMockMetafieldUpsertResponse(upsertedMetafield);
        when(graphQLClient.execute(any(), any())).thenReturn(mockResponse);
        
        // When
        Metafield metafield = metafieldService.upsertMetafield(authContext, input);
        
        // Then
        assertNotNull(metafield);
        assertEquals("warehouse_location", metafield.getKey());
        assertEquals("A-12-B", metafield.getValue());
    }
    
    // Helper methods
    private Metafield createTestMetafield(String id, String namespace, String key, String value) {
        return Metafield.builder()
            .id(new ID("gid://shopify/Metafield/" + id))
            .namespace(namespace)
            .key(key)
            .value(value)
            .type("single_line_text_field")
            .createdAt(ZonedDateTime.now())
            .updatedAt(ZonedDateTime.now())
            .build();
    }
    
    private GraphQLResponse<Object> createMockMetafieldCreateResponse(Metafield metafield) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "metafieldCreate", Map.of(
                "metafield", metafield,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockMetafieldsBulkCreateResponse(List<Metafield> metafields) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "metafieldsSet", Map.of(
                "metafields", metafields,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockMetafieldGetResponse(Metafield metafield) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of("metafield", metafield));
        return response;
    }
    
    private GraphQLResponse<Object> createMockMetafieldsListResponse(List<Metafield> metafields) {
        List<Map<String, Object>> edges = new ArrayList<>();
        for (Metafield metafield : metafields) {
            edges.add(Map.of("node", metafield));
        }
        
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "metafields", Map.of(
                "edges", edges,
                "pageInfo", Map.of("hasNextPage", false)
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockMetafieldUpdateResponse(Metafield metafield) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "metafieldUpdate", Map.of(
                "metafield", metafield,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockMetafieldDeleteResponse(String metafieldId) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "metafieldDelete", Map.of(
                "deletedId", metafieldId,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockMetafieldsBulkDeleteResponse(List<String> metafieldIds) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "metafieldsDelete", Map.of(
                "deletedMetafieldIds", metafieldIds,
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
    
    private GraphQLResponse<Object> createMockMetafieldDefinitionResponse(MetafieldDefinition definition) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of("metafieldDefinition", definition));
        return response;
    }
    
    private GraphQLResponse<Object> createMockMetafieldCreateErrorResponse(String field, String message) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "metafieldCreate", Map.of(
                "metafield", null,
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
    
    private GraphQLResponse<Object> createMockMetafieldUpsertResponse(Metafield metafield) {
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(Map.of(
            "metafieldsSet", Map.of(
                "metafields", Arrays.asList(metafield),
                "userErrors", new ArrayList<>()
            )
        ));
        return response;
    }
}