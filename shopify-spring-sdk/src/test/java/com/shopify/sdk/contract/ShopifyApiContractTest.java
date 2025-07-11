package com.shopify.sdk.contract;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.config.ShopifyProperties;
import com.shopify.sdk.model.product.Product;
import com.shopify.sdk.model.product.ProductStatus;
import com.shopify.sdk.service.product.ProductService;
import com.shopify.sdk.test.ShopifyMockServer;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contract Tests - Shopify API 스키마와의 계약 준수 검증
 * 
 * 이 테스트는 SDK가 실제 Shopify API의 응답 구조를 올바르게 처리하는지 확인합니다.
 * API 버전이 업데이트되더라도 SDK가 계속 작동하도록 보장합니다.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShopifyApiContractTest {
    
    private ShopifyMockServer mockServer;
    private ProductService productService;
    private ShopifyAuthContext authContext;
    
    @BeforeAll
    void setUp() throws IOException {
        mockServer = new ShopifyMockServer("2025-07");
        
        // Mock 서버를 사용하도록 설정
        ShopifyProperties properties = new ShopifyProperties();
        properties.getApi().setVersion("2025-07");
        
        authContext = ShopifyAuthContext.builder()
            .shopDomain("localhost:" + mockServer.getUrl().split(":")[2].replace("/", ""))
            .accessToken("test-token")
            .apiVersion("2025-07")
            .build();
        
        // 실제 HTTP 통신을 하는 클라이언트 설정
        ShopifyGraphQLClient client = new ShopifyGraphQLClient(
            createHttpClientForMockServer(),
            objectMapper,
            rateLimiter,
            properties
        );
        
        productService = new ProductService(client);
    }
    
    @AfterAll
    void tearDown() throws IOException {
        mockServer.shutdown();
    }
    
    @Test
    @DisplayName("Product Query - 2025-07 API 계약 준수")
    void testProductQueryContract_2025_07() {
        // Given: 실제 Shopify API 2025-07 응답 구조
        String actualShopifyResponse = """
            {
                "data": {
                    "product": {
                        "id": "gid://shopify/Product/7234567890123",
                        "title": "The Collection Snowboard: Liquid",
                        "handle": "the-collection-snowboard-liquid",
                        "description": "This COLLECTION board is a limited edition.",
                        "descriptionHtml": "<p>This COLLECTION board is a limited edition.</p>",
                        "vendor": "Hydrogen Vendor",
                        "productType": "Snowboards",
                        "status": "ACTIVE",
                        "tags": ["Premium", "Snow", "Winter"],
                        "publishedAt": "2024-01-01T00:00:00Z",
                        "createdAt": "2024-01-01T00:00:00Z",
                        "updatedAt": "2024-01-01T00:00:00Z",
                        "featuredImage": {
                            "id": "gid://shopify/ProductImage/1234567890",
                            "url": "https://cdn.shopify.com/s/files/1/0000/0001/products/snowboard.jpg",
                            "altText": "Snowboard product image",
                            "width": 1200,
                            "height": 1200
                        },
                        "priceRange": {
                            "minVariantPrice": {
                                "amount": "699.99",
                                "currencyCode": "USD"
                            },
                            "maxVariantPrice": {
                                "amount": "699.99",
                                "currencyCode": "USD"
                            }
                        },
                        "seo": {
                            "title": "Collection Snowboard | Premium Winter Sports Equipment",
                            "description": "Limited edition COLLECTION snowboard perfect for winter sports enthusiasts"
                        },
                        "variants": {
                            "edges": [
                                {
                                    "node": {
                                        "id": "gid://shopify/ProductVariant/41234567890123",
                                        "title": "Default Title",
                                        "sku": "SNOW-LIQUID-001",
                                        "barcode": "123456789012",
                                        "price": {
                                            "amount": "699.99",
                                            "currencyCode": "USD"
                                        },
                                        "compareAtPrice": {
                                            "amount": "899.99",
                                            "currencyCode": "USD"
                                        },
                                        "weight": 5.5,
                                        "weightUnit": "POUNDS",
                                        "availableForSale": true,
                                        "inventoryQuantity": 15,
                                        "selectedOptions": [
                                            {
                                                "name": "Title",
                                                "value": "Default Title"
                                            }
                                        ]
                                    }
                                }
                            ],
                            "pageInfo": {
                                "hasNextPage": false,
                                "hasPreviousPage": false
                            }
                        },
                        "options": [
                            {
                                "id": "gid://shopify/ProductOption/9234567890123",
                                "name": "Title",
                                "position": 1,
                                "values": ["Default Title"]
                            }
                        ]
                    }
                },
                "extensions": {
                    "cost": {
                        "requestedQueryCost": 15,
                        "actualQueryCost": 12,
                        "throttleStatus": {
                            "maximumAvailable": 2000,
                            "currentlyAvailable": 1988,
                            "restoreRate": 100
                        }
                    }
                }
            }
            """;
        
        mockServer.server.enqueue(new MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setHeader("X-Shopify-Shop-Api-Call-Limit", "40/40")
            .setBody(actualShopifyResponse));
        
        // When: SDK를 통해 상품 조회
        Product product = productService.getProduct(authContext, "gid://shopify/Product/7234567890123");
        
        // Then: 모든 필드가 올바르게 매핑되는지 검증
        assertNotNull(product, "Product should not be null");
        assertEquals("gid://shopify/Product/7234567890123", product.getId().getValue());
        assertEquals("The Collection Snowboard: Liquid", product.getTitle());
        assertEquals("the-collection-snowboard-liquid", product.getHandle());
        assertEquals("This COLLECTION board is a limited edition.", product.getDescription());
        assertEquals("Hydrogen Vendor", product.getVendor());
        assertEquals("Snowboards", product.getProductType());
        assertEquals(ProductStatus.ACTIVE, product.getStatus());
        
        // Tags 검증
        assertNotNull(product.getTags());
        assertEquals(3, product.getTags().size());
        assertTrue(product.getTags().contains("Premium"));
        
        // Featured Image 검증
        assertNotNull(product.getFeaturedImage());
        assertEquals("https://cdn.shopify.com/s/files/1/0000/0001/products/snowboard.jpg", 
                    product.getFeaturedImage().getUrl());
        
        // Price Range 검증
        assertNotNull(product.getPriceRange());
        assertEquals("699.99", product.getPriceRange().getMinVariantPrice().getAmount());
        assertEquals("USD", product.getPriceRange().getMinVariantPrice().getCurrencyCode().getValue());
        
        // SEO 검증
        assertNotNull(product.getSeo());
        assertEquals("Collection Snowboard | Premium Winter Sports Equipment", product.getSeo().getTitle());
        
        // Variants 검증
        assertNotNull(product.getVariants());
        assertEquals(1, product.getVariants().getEdges().size());
        
        var variant = product.getVariants().getEdges().get(0).getNode();
        assertEquals("SNOW-LIQUID-001", variant.getSku());
        assertEquals("699.99", variant.getPrice().getAmount());
        assertEquals(15, variant.getInventoryQuantity());
        assertTrue(variant.isAvailableForSale());
    }
    
    @Test
    @DisplayName("Error Response - API 에러 응답 계약")
    void testErrorResponseContract() {
        // Given: Shopify API 에러 응답 구조
        String errorResponse = """
            {
                "errors": [
                    {
                        "message": "Field 'invalidField' doesn't exist on type 'Product'",
                        "extensions": {
                            "code": "GRAPHQL_VALIDATION_FAILED",
                            "documentation": "https://shopify.dev/api/errors"
                        },
                        "locations": [
                            {
                                "line": 3,
                                "column": 5
                            }
                        ],
                        "path": ["product", "invalidField"]
                    }
                ],
                "extensions": {
                    "cost": {
                        "requestedQueryCost": 0,
                        "actualQueryCost": 0,
                        "throttleStatus": {
                            "maximumAvailable": 2000,
                            "currentlyAvailable": 2000,
                            "restoreRate": 100
                        }
                    }
                }
            }
            """;
        
        mockServer.server.enqueue(new MockResponse()
            .setResponseCode(200)  // GraphQL은 200을 반환하고 errors 필드로 에러 표시
            .setHeader("Content-Type", "application/json")
            .setBody(errorResponse));
        
        // When & Then: SDK가 에러를 올바르게 처리하는지 확인
        ShopifyApiException exception = assertThrows(ShopifyApiException.class, () -> {
            productService.getProduct(authContext, "gid://shopify/Product/123");
        });
        
        assertTrue(exception.getMessage().contains("GRAPHQL_VALIDATION_FAILED"));
    }
    
    @Test
    @DisplayName("Rate Limit Response - 429 응답 계약")
    void testRateLimitResponseContract() {
        // Given: Shopify Rate Limit 응답
        mockServer.server.enqueue(new MockResponse()
            .setResponseCode(429)
            .setHeader("Retry-After", "2.0")
            .setHeader("X-Shopify-Shop-Api-Call-Limit", "40/40")
            .setBody("{\"errors\":\"Throttled\"}"));
        
        // When & Then: Rate limit 예외가 올바르게 발생하는지 확인
        ShopifyRateLimitException exception = assertThrows(ShopifyRateLimitException.class, () -> {
            productService.getProduct(authContext, "gid://shopify/Product/123");
        });
        
        assertEquals(2.0, exception.getRetryAfter());
    }
    
    @Test
    @DisplayName("Pagination - 페이징 응답 계약")
    void testPaginationContract() {
        // Given: 페이징이 포함된 응답
        String paginatedResponse = """
            {
                "data": {
                    "products": {
                        "edges": [
                            {
                                "node": {
                                    "id": "gid://shopify/Product/1",
                                    "title": "Product 1"
                                },
                                "cursor": "eyJsYXN0X2lkIjoxfQ=="
                            }
                        ],
                        "pageInfo": {
                            "hasNextPage": true,
                            "hasPreviousPage": false,
                            "startCursor": "eyJsYXN0X2lkIjoxfQ==",
                            "endCursor": "eyJsYXN0X2lkIjoxfQ=="
                        },
                        "totalCount": 100
                    }
                }
            }
            """;
        
        mockServer.server.enqueue(new MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody(paginatedResponse));
        
        // When: 페이징된 결과 조회
        var result = productService.getProductsWithPaging(authContext, 1, null);
        
        // Then: 페이징 정보가 올바르게 파싱되는지 확인
        assertNotNull(result);
        assertNotNull(result.getPageInfo());
        assertTrue(result.getPageInfo().isHasNextPage());
        assertFalse(result.getPageInfo().isHasPreviousPage());
        assertEquals("eyJsYXN0X2lkIjoxfQ==", result.getPageInfo().getEndCursor());
        assertEquals(100, result.getTotalCount());
    }
    
    private OkHttpClient createHttpClientForMockServer() {
        return new OkHttpClient.Builder()
            .connectTimeout(Duration.ofSeconds(10))
            .readTimeout(Duration.ofSeconds(30))
            .writeTimeout(Duration.ofSeconds(30))
            .build();
    }
}