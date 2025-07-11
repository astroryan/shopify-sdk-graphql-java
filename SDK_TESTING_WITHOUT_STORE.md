# Shopify Store 없이 SDK 테스트하기

## 문제점
- SDK 사용자들이 실제 Shopify 스토어 없이도 개발하고 테스트해야 함
- 실제 API 호출은 비용이 발생하고 Rate Limit에 걸릴 수 있음
- 테스트 데이터 생성/삭제가 실제 스토어에 영향을 줄 수 있음

## 해결 방안

### 1. 3단계 테스트 전략

```
┌─────────────────────────────────────────────────────────┐
│                    단위 테스트 (80%)                      │
│         Mock 기반, Store 불필요, 항상 실행                 │
├─────────────────────────────────────────────────────────┤
│                  계약 테스트 (15%)                        │
│      Mock Server 기반, API 스키마 검증                    │
├─────────────────────────────────────────────────────────┤
│              통합 테스트 (5%) - Optional                  │
│          실제 Store 필요, CI에서 선택적 실행               │
└─────────────────────────────────────────────────────────┘
```

### 2. 테스트 프로파일 설정

```java
// build.gradle
test {
    useJUnitPlatform {
        // 기본적으로 통합 테스트는 제외
        excludeTags 'integration'
    }
}

// 통합 테스트만 실행하는 별도 태스크
task integrationTest(type: Test) {
    useJUnitPlatform {
        includeTags 'integration'
    }
    
    // 환경변수 체크
    doFirst {
        if (!System.getenv('SHOPIFY_TEST_STORE_DOMAIN')) {
            throw new GradleException('Integration tests require SHOPIFY_TEST_STORE_DOMAIN')
        }
    }
}
```

### 3. 테스트 데이터 Fixture

```java
// src/test/java/com/shopify/sdk/fixtures/ShopifyTestFixtures.java
public class ShopifyTestFixtures {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    // 실제 Shopify API 응답을 기반으로 만든 fixture
    public static String getProductResponse() {
        return readFixture("fixtures/product_response.json");
    }
    
    public static String getOrdersResponse() {
        return readFixture("fixtures/orders_response.json");
    }
    
    public static String getCustomerResponse() {
        return readFixture("fixtures/customer_response.json");
    }
    
    // Rate limit 응답
    public static MockResponse rateLimitResponse() {
        return new MockResponse()
            .setResponseCode(429)
            .setHeader("Retry-After", "2.0")
            .setHeader("X-Shopify-Shop-Api-Call-Limit", "40/40")
            .setBody("{\"errors\":\"Throttled\"}");
    }
    
    // GraphQL 에러 응답
    public static String graphQLErrorResponse(String message, String code) {
        return """
            {
                "errors": [{
                    "message": "%s",
                    "extensions": {"code": "%s"}
                }]
            }
            """.formatted(message, code);
    }
}
```

### 4. 단위 테스트 (Store 불필요)

```java
@ExtendWith(MockitoExtension.class)
class ProductServiceUnitTest {
    
    @Mock
    private ShopifyGraphQLClient graphQLClient;
    
    private ProductService productService;
    
    @Test
    void testCreateProduct_WithoutStore() {
        // Given: Fixture 데이터 사용
        String mockResponse = ShopifyTestFixtures.getProductResponse();
        
        when(graphQLClient.execute(any(), any()))
            .thenReturn(parseGraphQLResponse(mockResponse));
        
        // When: 서비스 호출
        ProductInput input = ProductInput.builder()
            .title("Test Product")
            .build();
            
        Product product = productService.createProduct(authContext, input);
        
        // Then: 검증
        assertNotNull(product);
        assertEquals("Test Product", product.getTitle());
    }
}
```

### 5. Mock Server 기반 테스트

```java
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductServiceMockServerTest {
    
    private MockWebServer mockServer;
    private ProductService productService;
    
    @BeforeAll
    void setUp() throws IOException {
        // Mock Server 시작
        mockServer = new MockWebServer();
        mockServer.start();
        
        // Mock Server를 가리키도록 설정
        String mockUrl = mockServer.url("/").toString();
        ShopifyAuthContext context = ShopifyAuthContext.builder()
            .shopDomain(mockUrl.replace("http://", "").replace("/", ""))
            .accessToken("mock-token")
            .apiVersion("2025-07")
            .build();
        
        // 실제 HTTP 클라이언트 사용
        productService = createProductServiceWithMockServer(mockUrl);
    }
    
    @Test
    void testRealHttpCommunication() throws InterruptedException {
        // Given: Mock 응답 설정
        mockServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody(ShopifyTestFixtures.getProductResponse()));
        
        // When: 실제 HTTP 통신
        Product product = productService.getProduct(context, "123");
        
        // Then: 요청 검증
        RecordedRequest request = mockServer.takeRequest();
        assertEquals("POST", request.getMethod());
        assertEquals("mock-token", request.getHeader("X-Shopify-Access-Token"));
        assertTrue(request.getPath().contains("/admin/api/2025-07/graphql.json"));
    }
}
```

### 6. 통합 테스트 (선택적 실행)

```java
@Tag("integration")  // 기본 테스트에서 제외
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnabledIfEnvironmentVariable(named = "SHOPIFY_TEST_STORE_DOMAIN", matches = ".+")
class ShopifyIntegrationTest {
    
    private static final String STORE_DOMAIN = System.getenv("SHOPIFY_TEST_STORE_DOMAIN");
    private static final String ACCESS_TOKEN = System.getenv("SHOPIFY_TEST_ACCESS_TOKEN");
    
    @BeforeAll
    void checkCredentials() {
        assumeTrue(STORE_DOMAIN != null, "SHOPIFY_TEST_STORE_DOMAIN not set");
        assumeTrue(ACCESS_TOKEN != null, "SHOPIFY_TEST_ACCESS_TOKEN not set");
    }
    
    @Test
    void testRealApiCall() {
        // 실제 API 호출 테스트
        // CI/CD에서만 실행되도록 설정
    }
}
```

### 7. 테스트 데이터 생성기

```java
public class ShopifyTestDataGenerator {
    
    private static final Faker faker = new Faker();
    
    public static Product generateProduct() {
        return Product.builder()
            .id(new ID("gid://shopify/Product/" + faker.number().digits(10)))
            .title(faker.commerce().productName())
            .handle(faker.internet().slug())
            .vendor(faker.company().name())
            .status(ProductStatus.ACTIVE)
            .price(Money.builder()
                .amount(faker.commerce().price())
                .currencyCode(CurrencyCode.USD)
                .build())
            .createdAt(ZonedDateTime.now())
            .build();
    }
    
    public static Order generateOrder() {
        return Order.builder()
            .id(new ID("gid://shopify/Order/" + faker.number().digits(10)))
            .name("#" + faker.number().digits(4))
            .email(faker.internet().emailAddress())
            .totalPrice(Money.builder()
                .amount(new BigDecimal(faker.commerce().price()))
                .currencyCode(CurrencyCode.USD)
                .build())
            .financialStatus(OrderFinancialStatus.PAID)
            .createdAt(ZonedDateTime.now())
            .build();
    }
}
```

### 8. CI/CD 설정

```yaml
# .github/workflows/test.yml
name: SDK Tests

on: [push, pull_request]

jobs:
  unit-tests:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Run Unit Tests (No Store Required)
      run: ./gradlew test
    
  integration-tests:
    runs-on: ubuntu-latest
    if: github.event_name == 'push' && github.ref == 'refs/heads/main'
    steps:
    - uses: actions/checkout@v3
    - name: Run Integration Tests
      env:
        SHOPIFY_TEST_STORE_DOMAIN: ${{ secrets.SHOPIFY_TEST_STORE_DOMAIN }}
        SHOPIFY_TEST_ACCESS_TOKEN: ${{ secrets.SHOPIFY_TEST_ACCESS_TOKEN }}
      run: ./gradlew integrationTest
```

### 9. 개발자를 위한 테스트 가이드

```java
/**
 * SDK 사용자를 위한 테스트 예제
 * 
 * Shopify Store 없이 SDK를 테스트하는 방법을 보여줍니다.
 */
public class UserApplicationTest {
    
    @Test
    void testMyShopifyIntegration() {
        // 1. Mock ProductService 생성
        ProductService mockService = Mockito.mock(ProductService.class);
        
        // 2. 테스트 데이터 설정
        Product testProduct = ShopifyTestDataGenerator.generateProduct();
        when(mockService.getProduct(any(), anyString()))
            .thenReturn(testProduct);
        
        // 3. 비즈니스 로직 테스트
        MyProductManager manager = new MyProductManager(mockService);
        ProductDTO result = manager.getProductDetails("123");
        
        assertNotNull(result);
        assertEquals(testProduct.getTitle(), result.getName());
    }
}
```

## 권장사항

1. **개발 중**: Mock Server 기반 테스트 사용
2. **CI/CD**: 단위 테스트는 항상, 통합 테스트는 선택적 실행
3. **릴리스 전**: 실제 테스트 스토어에서 통합 테스트 실행
4. **SDK 사용자**: 제공된 Mock 도구와 Fixture 활용

## 테스트 커버리지 목표

| 테스트 유형 | 커버리지 | Store 필요 | 실행 빈도 |
|-----------|---------|-----------|----------|
| 단위 테스트 | 80%+ | ❌ | 항상 |
| Mock Server | 15% | ❌ | 항상 |
| 통합 테스트 | 5% | ✅ | 선택적 |

이 방식으로 SDK 사용자들은 Shopify Store 없이도 안정적으로 개발하고 테스트할 수 있습니다.