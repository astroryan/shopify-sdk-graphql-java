package com.shopify.sdk.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.shopify.sdk.client.GraphQLRequest;
import com.shopify.sdk.client.GraphQLResponse;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.config.ShopifyAuthContext;
import com.shopify.sdk.exception.ShopifyApiException;
import com.shopify.sdk.model.common.*;
import com.shopify.sdk.model.products.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    
    @Mock
    private ShopifyGraphQLClient graphQLClient;
    
    private ProductService productService;
    
    private ShopifyAuthContext authContext;
    
    @BeforeEach
    void setUp() {
        productService = new ProductService(graphQLClient);
        authContext = new ShopifyAuthContext("test-shop.myshopify.com", "test-token");
    }
    
    @Test
    void testGetProducts() {
        // Arrange
        List<Product> expectedProducts = Arrays.asList(
                Product.builder()
                        .id(new ID("gid://shopify/Product/1"))
                        .title("Product 1")
                        .handle("product-1")
                        .status(ProductStatus.ACTIVE)
                        .build(),
                Product.builder()
                        .id(new ID("gid://shopify/Product/2"))
                        .title("Product 2")
                        .handle("product-2")
                        .status(ProductStatus.ACTIVE)
                        .build()
        );
        
        GraphQLResponse<Object> mockResponse = createMockProductsResponse(expectedProducts);
        
        when(graphQLClient.execute(any(GraphQLRequest.class), any(TypeReference.class)))
                .thenReturn(mockResponse);
        
        // Act
        List<Product> actualProducts = productService.getProducts(
                authContext, 10, null, null, ProductSortKeys.TITLE, false
        );
        
        // Assert
        assertEquals(2, actualProducts.size());
        assertEquals("Product 1", actualProducts.get(0).getTitle());
        assertEquals("Product 2", actualProducts.get(1).getTitle());
        
        verify(graphQLClient).execute(any(GraphQLRequest.class), any(TypeReference.class));
    }
    
    @Test
    void testGetProduct() {
        // Arrange
        String productId = "gid://shopify/Product/123";
        Product expectedProduct = Product.builder()
                .id(new ID(productId))
                .title("Test Product")
                .handle("test-product")
                .description("Test Description")
                .productType("Test Type")
                .vendor("Test Vendor")
                .status(ProductStatus.ACTIVE)
                .tags(Arrays.asList("tag1", "tag2"))
                .build();
        
        GraphQLResponse<Object> mockResponse = createMockProductResponse(expectedProduct);
        
        when(graphQLClient.execute(any(GraphQLRequest.class), any(TypeReference.class)))
                .thenReturn(mockResponse);
        
        // Act
        Product actualProduct = productService.getProduct(authContext, productId);
        
        // Assert
        assertNotNull(actualProduct);
        assertEquals(productId, actualProduct.getId().getValue());
        assertEquals("Test Product", actualProduct.getTitle());
        assertEquals(2, actualProduct.getTags().size());
        
        // Verify the GraphQL query was called with correct parameters
        ArgumentCaptor<GraphQLRequest> requestCaptor = ArgumentCaptor.forClass(GraphQLRequest.class);
        verify(graphQLClient).execute(requestCaptor.capture(), any(TypeReference.class));
        
        GraphQLRequest capturedRequest = requestCaptor.getValue();
        assertEquals(productId, capturedRequest.getVariables().get("id"));
    }
    
    @Test
    void testCreateProduct() {
        // Arrange
        ProductInput input = ProductInput.builder()
                .title("New Product")
                .handle("new-product")
                .description("New Product Description")
                .productType("Type")
                .vendor("Vendor")
                .status(ProductStatus.ACTIVE)
                .tags(Arrays.asList("new", "product"))
                .build();
        
        Product expectedProduct = Product.builder()
                .id(new ID("gid://shopify/Product/999"))
                .title("New Product")
                .handle("new-product")
                .description("New Product Description")
                .productType("Type")
                .vendor("Vendor")
                .status(ProductStatus.ACTIVE)
                .tags(Arrays.asList("new", "product"))
                .build();
        
        GraphQLResponse<Object> mockResponse = createMockProductCreateResponse(expectedProduct);
        
        when(graphQLClient.execute(any(GraphQLRequest.class), any(TypeReference.class)))
                .thenReturn(mockResponse);
        
        // Act
        Product actualProduct = productService.createProduct(authContext, input);
        
        // Assert
        assertNotNull(actualProduct);
        assertEquals("New Product", actualProduct.getTitle());
        assertEquals("new-product", actualProduct.getHandle());
        
        // Verify mutation was called
        ArgumentCaptor<GraphQLRequest> requestCaptor = ArgumentCaptor.forClass(GraphQLRequest.class);
        verify(graphQLClient).execute(requestCaptor.capture(), any(TypeReference.class));
        
        GraphQLRequest capturedRequest = requestCaptor.getValue();
        assertEquals(input, capturedRequest.getVariables().get("input"));
    }
    
    @Test
    void testUpdateProduct() {
        // Arrange
        String productId = "gid://shopify/Product/123";
        ProductInput input = ProductInput.builder()
                .title("Updated Product")
                .description("Updated Description")
                .build();
        
        Product expectedProduct = Product.builder()
                .id(new ID(productId))
                .title("Updated Product")
                .description("Updated Description")
                .build();
        
        GraphQLResponse<Object> mockResponse = createMockProductUpdateResponse(expectedProduct);
        
        when(graphQLClient.execute(any(GraphQLRequest.class), any(TypeReference.class)))
                .thenReturn(mockResponse);
        
        // Act
        Product actualProduct = productService.updateProduct(authContext, productId, input);
        
        // Assert
        assertNotNull(actualProduct);
        assertEquals("Updated Product", actualProduct.getTitle());
        assertEquals("Updated Description", actualProduct.getDescription());
    }
    
    @Test
    void testDeleteProduct() {
        // Arrange
        String productId = "gid://shopify/Product/123";
        
        GraphQLResponse<Object> mockResponse = createMockProductDeleteResponse(productId);
        
        when(graphQLClient.execute(any(GraphQLRequest.class), any(TypeReference.class)))
                .thenReturn(mockResponse);
        
        // Act
        String deletedId = productService.deleteProduct(authContext, productId);
        
        // Assert
        assertEquals(productId, deletedId);
    }
    
    @Test
    void testCreateProductVariant() {
        // Arrange
        String productId = "gid://shopify/Product/123";
        ProductVariantInput input = ProductVariantInput.builder()
                .price("19.99")
                .sku("SKU123")
                .barcode("123456789")
                .weight(1.5)
                .weightUnit(WeightUnit.KILOGRAMS)
                .inventoryQuantity(100)
                .options(Arrays.asList("Red", "Large"))
                .build();
        
        ProductVariant expectedVariant = ProductVariant.builder()
                .id(new ID("gid://shopify/ProductVariant/456"))
                .title("Red / Large")
                .price(Money.builder().amount("19.99").currencyCode(CurrencyCode.USD).build())
                .sku("SKU123")
                .barcode("123456789")
                .weight(1.5)
                .weightUnit(WeightUnit.KILOGRAMS)
                .build();
        
        GraphQLResponse<Object> mockResponse = createMockVariantCreateResponse(expectedVariant);
        
        when(graphQLClient.execute(any(GraphQLRequest.class), any(TypeReference.class)))
                .thenReturn(mockResponse);
        
        // Act
        ProductVariant actualVariant = productService.createProductVariant(authContext, productId, input);
        
        // Assert
        assertNotNull(actualVariant);
        assertEquals("SKU123", actualVariant.getSku());
        assertEquals("19.99", actualVariant.getPrice().getAmount());
    }
    
    @Test
    void testGetProductsWithError() {
        // Arrange
        GraphQLResponse<Object> mockResponse = new GraphQLResponse<>();
        mockResponse.setErrors(Arrays.asList(
                GraphQLError.builder()
                        .message("Unauthorized")
                        .extensions(Map.of("code", "UNAUTHORIZED"))
                        .build()
        ));
        
        when(graphQLClient.execute(any(GraphQLRequest.class), any(TypeReference.class)))
                .thenReturn(mockResponse);
        
        // Act & Assert
        assertThrows(ShopifyApiException.class, () -> {
            productService.getProducts(authContext, 10, null, null, null, false);
        });
    }
    
    @Test
    void testCreateProductWithUserErrors() {
        // Arrange
        ProductInput input = ProductInput.builder()
                .title("") // Invalid - empty title
                .build();
        
        GraphQLResponse<Object> mockResponse = createMockProductCreateResponseWithErrors();
        
        when(graphQLClient.execute(any(GraphQLRequest.class), any(TypeReference.class)))
                .thenReturn(mockResponse);
        
        // Act & Assert
        ShopifyApiException exception = assertThrows(ShopifyApiException.class, () -> {
            productService.createProduct(authContext, input);
        });
        
        assertTrue(exception.getMessage().contains("Failed to create product"));
    }
    
    // Helper methods to create mock responses
    private GraphQLResponse<Object> createMockProductsResponse(List<Product> products) {
        // Create a mock response structure that matches the actual GraphQL response
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> productsData = new HashMap<>();
        List<Map<String, Object>> edges = new ArrayList<>();
        
        for (Product product : products) {
            Map<String, Object> edge = new HashMap<>();
            edge.put("node", product);
            edge.put("cursor", "cursor_" + product.getId().getValue());
            edges.add(edge);
        }
        
        productsData.put("edges", edges);
        productsData.put("pageInfo", Map.of("hasNextPage", false, "endCursor", null));
        data.put("products", productsData);
        
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(data);
        return response;
    }
    
    private GraphQLResponse<Object> createMockProductResponse(Product product) {
        Map<String, Object> data = new HashMap<>();
        data.put("product", product);
        
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(data);
        return response;
    }
    
    private GraphQLResponse<Object> createMockProductCreateResponse(Product product) {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> productCreate = new HashMap<>();
        productCreate.put("product", product);
        productCreate.put("userErrors", new ArrayList<>());
        data.put("productCreate", productCreate);
        
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(data);
        return response;
    }
    
    private GraphQLResponse<Object> createMockProductUpdateResponse(Product product) {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> productUpdate = new HashMap<>();
        productUpdate.put("product", product);
        productUpdate.put("userErrors", new ArrayList<>());
        data.put("productUpdate", productUpdate);
        
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(data);
        return response;
    }
    
    private GraphQLResponse<Object> createMockProductDeleteResponse(String productId) {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> productDelete = new HashMap<>();
        productDelete.put("deletedProductId", productId);
        productDelete.put("userErrors", new ArrayList<>());
        data.put("productDelete", productDelete);
        
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(data);
        return response;
    }
    
    private GraphQLResponse<Object> createMockVariantCreateResponse(ProductVariant variant) {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> variantCreate = new HashMap<>();
        variantCreate.put("productVariant", variant);
        variantCreate.put("userErrors", new ArrayList<>());
        data.put("productVariantCreate", variantCreate);
        
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(data);
        return response;
    }
    
    private GraphQLResponse<Object> createMockProductCreateResponseWithErrors() {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> productCreate = new HashMap<>();
        productCreate.put("product", null);
        productCreate.put("userErrors", Arrays.asList(
                Map.of(
                        "field", Arrays.asList("title"),
                        "message", "Title can't be blank"
                )
        ));
        data.put("productCreate", productCreate);
        
        GraphQLResponse<Object> response = new GraphQLResponse<>();
        response.setData(data);
        return response;
    }
}