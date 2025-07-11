package com.shopify.sdk.integration;

import com.shopify.sdk.config.ShopifyAuthContext;
import com.shopify.sdk.config.ShopifyConfig;
import com.shopify.sdk.model.common.*;
import com.shopify.sdk.model.products.*;
import com.shopify.sdk.model.orders.*;
import com.shopify.sdk.model.customers.*;
import com.shopify.sdk.service.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Shopify SDK.
 * These tests require a valid Shopify store and access token.
 * 
 * To run these tests, set the following environment variables:
 * - SHOPIFY_TEST_STORE_DOMAIN: Your test store domain (e.g., "test-store.myshopify.com")
 * - SHOPIFY_TEST_ACCESS_TOKEN: Your test store access token
 * 
 * Note: These tests will create/modify/delete data in your test store.
 * Only run against a development/test store.
 */
@SpringBootTest
@TestPropertySource(properties = {
    "shopify.sdk.api.version=2025-07",
    "shopify.sdk.api.timeout.connect=10s",
    "shopify.sdk.api.timeout.read=30s",
    "shopify.sdk.api.timeout.write=30s",
    "shopify.sdk.api.retry.max-attempts=3",
    "shopify.sdk.api.retry.backoff-delay=1000"
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled("Integration tests require valid Shopify credentials")
public class ShopifySDKIntegrationTest {
    
    @Autowired
    private ShopifyConfig shopifyConfig;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private InventoryService inventoryService;
    
    @Autowired
    private StorePropertiesService storePropertiesService;
    
    private static ShopifyAuthContext authContext;
    private static String createdProductId;
    private static String createdCustomerId;
    
    @BeforeAll
    static void setupAuth() {
        String storeDomain = System.getenv("SHOPIFY_TEST_STORE_DOMAIN");
        String accessToken = System.getenv("SHOPIFY_TEST_ACCESS_TOKEN");
        
        if (storeDomain == null || accessToken == null) {
            fail("Test credentials not provided. Set SHOPIFY_TEST_STORE_DOMAIN and SHOPIFY_TEST_ACCESS_TOKEN environment variables.");
        }
        
        authContext = new ShopifyAuthContext(storeDomain, accessToken);
    }
    
    @Test
    @Order(1)
    void testGetShopInformation() {
        // Test getting shop information
        Shop shop = storePropertiesService.getShop(authContext);
        
        assertNotNull(shop);
        assertNotNull(shop.getName());
        assertNotNull(shop.getEmail());
        assertNotNull(shop.getCurrencyCode());
        assertNotNull(shop.getMyshopifyDomain());
        
        System.out.println("Connected to shop: " + shop.getName());
        System.out.println("Currency: " + shop.getCurrencyCode());
        System.out.println("Domain: " + shop.getMyshopifyDomain());
    }
    
    @Test
    @Order(2)
    void testCreateProduct() {
        // Create a test product
        ProductInput input = ProductInput.builder()
                .title("Test Product - SDK Integration")
                .handle("test-product-sdk-integration")
                .description("This is a test product created by the Shopify SDK integration test")
                .productType("Test Type")
                .vendor("Test Vendor")
                .status(ProductStatus.ACTIVE)
                .tags(Arrays.asList("test", "integration", "sdk"))
                .build();
        
        Product product = productService.createProduct(authContext, input);
        
        assertNotNull(product);
        assertNotNull(product.getId());
        assertEquals("Test Product - SDK Integration", product.getTitle());
        assertEquals("test-product-sdk-integration", product.getHandle());
        assertEquals(ProductStatus.ACTIVE, product.getStatus());
        
        createdProductId = product.getId().getValue();
        System.out.println("Created product: " + createdProductId);
    }
    
    @Test
    @Order(3)
    void testCreateProductVariant() {
        assertNotNull(createdProductId, "Product must be created first");
        
        // Create a variant for the test product
        ProductVariantInput variantInput = ProductVariantInput.builder()
                .price("19.99")
                .sku("TEST-SKU-001")
                .barcode("1234567890123")
                .weight(1.5)
                .weightUnit(WeightUnit.KILOGRAMS)
                .inventoryQuantity(100)
                .options(Arrays.asList("Red", "Large"))
                .build();
        
        ProductVariant variant = productService.createProductVariant(authContext, createdProductId, variantInput);
        
        assertNotNull(variant);
        assertNotNull(variant.getId());
        assertEquals("TEST-SKU-001", variant.getSku());
        assertEquals("19.99", variant.getPrice().getAmount());
        
        System.out.println("Created variant: " + variant.getId().getValue());
    }
    
    @Test
    @Order(4)
    void testGetProducts() {
        // List products
        List<Product> products = productService.getProducts(
                authContext, 
                10, 
                null, 
                "title:*SDK Integration*",
                ProductSortKeys.CREATED_AT,
                true
        );
        
        assertNotNull(products);
        assertFalse(products.isEmpty());
        
        // Find our test product
        Product testProduct = products.stream()
                .filter(p -> p.getId().getValue().equals(createdProductId))
                .findFirst()
                .orElse(null);
        
        assertNotNull(testProduct);
        assertEquals("Test Product - SDK Integration", testProduct.getTitle());
        
        System.out.println("Found " + products.size() + " products");
    }
    
    @Test
    @Order(5)
    void testCreateCustomer() {
        // Create a test customer
        CustomerInput customerInput = CustomerInput.builder()
                .firstName("Test")
                .lastName("Customer")
                .email("test.customer.sdk@example.com")
                .phone("+1234567890")
                .acceptsMarketing(true)
                .tags(Arrays.asList("test", "sdk"))
                .build();
        
        Customer customer = customerService.createCustomer(authContext, customerInput);
        
        assertNotNull(customer);
        assertNotNull(customer.getId());
        assertEquals("Test", customer.getFirstName());
        assertEquals("Customer", customer.getLastName());
        assertEquals("test.customer.sdk@example.com", customer.getEmail());
        
        createdCustomerId = customer.getId().getValue();
        System.out.println("Created customer: " + createdCustomerId);
    }
    
    @Test
    @Order(6)
    void testUpdateCustomer() {
        assertNotNull(createdCustomerId, "Customer must be created first");
        
        // Update the customer
        CustomerInput updateInput = CustomerInput.builder()
                .firstName("Updated")
                .lastName("Customer")
                .note("Updated via SDK integration test")
                .build();
        
        Customer updatedCustomer = customerService.updateCustomer(authContext, createdCustomerId, updateInput);
        
        assertNotNull(updatedCustomer);
        assertEquals("Updated", updatedCustomer.getFirstName());
        assertEquals("Updated via SDK integration test", updatedCustomer.getNote());
        
        System.out.println("Updated customer: " + updatedCustomer.getId().getValue());
    }
    
    @Test
    @Order(7)
    void testGetOrders() {
        // List recent orders
        List<Order> orders = orderService.getOrders(
                authContext,
                5,
                null,
                null,
                OrderSortKeys.CREATED_AT,
                true
        );
        
        assertNotNull(orders);
        // Note: Test store might not have orders
        
        System.out.println("Found " + orders.size() + " orders");
        
        if (!orders.isEmpty()) {
            Order firstOrder = orders.get(0);
            System.out.println("First order: " + firstOrder.getName() + " - " + firstOrder.getDisplayFinancialStatus());
        }
    }
    
    @Test
    @Order(8)
    void testInventoryOperations() {
        // Get inventory levels for a location
        List<Location> locations = inventoryService.getLocations(authContext, 5, null);
        
        assertNotNull(locations);
        assertFalse(locations.isEmpty(), "Store must have at least one location");
        
        Location firstLocation = locations.get(0);
        System.out.println("First location: " + firstLocation.getName());
        
        // Get inventory levels at this location
        List<InventoryLevel> levels = inventoryService.getInventoryLevels(
                authContext,
                10,
                null,
                Arrays.asList(firstLocation.getId().getValue()),
                null
        );
        
        assertNotNull(levels);
        System.out.println("Found " + levels.size() + " inventory levels at " + firstLocation.getName());
    }
    
    @Test
    @Order(9)
    void testProductUpdate() {
        assertNotNull(createdProductId, "Product must be created first");
        
        // Update the test product
        ProductInput updateInput = ProductInput.builder()
                .title("Updated Test Product - SDK Integration")
                .description("This product has been updated by the integration test")
                .build();
        
        Product updatedProduct = productService.updateProduct(authContext, createdProductId, updateInput);
        
        assertNotNull(updatedProduct);
        assertEquals("Updated Test Product - SDK Integration", updatedProduct.getTitle());
        assertEquals("This product has been updated by the integration test", updatedProduct.getDescription());
        
        System.out.println("Updated product: " + updatedProduct.getId().getValue());
    }
    
    @Test
    @Order(10)
    void testCleanup() {
        // Clean up test data
        
        // Delete test customer
        if (createdCustomerId != null) {
            String deletedCustomerId = customerService.deleteCustomer(authContext, createdCustomerId);
            assertEquals(createdCustomerId, deletedCustomerId);
            System.out.println("Deleted customer: " + deletedCustomerId);
        }
        
        // Delete test product
        if (createdProductId != null) {
            String deletedProductId = productService.deleteProduct(authContext, createdProductId);
            assertEquals(createdProductId, deletedProductId);
            System.out.println("Deleted product: " + deletedProductId);
        }
    }
    
    @Test
    @Order(11)
    void testErrorHandling() {
        // Test error handling with invalid product ID
        assertThrows(Exception.class, () -> {
            productService.getProduct(authContext, "gid://shopify/Product/invalid");
        });
        
        // Test error handling with invalid input
        ProductInput invalidInput = ProductInput.builder()
                .title("") // Empty title should cause validation error
                .build();
        
        assertThrows(Exception.class, () -> {
            productService.createProduct(authContext, invalidInput);
        });
    }
}