# SDK 테스트 전략 가이드

## SDK 테스트의 특수성

SDK는 다른 개발자들이 사용하는 라이브러리이므로, 일반 애플리케이션과는 다른 테스트 전략이 필요합니다.

### 핵심 원칙
1. **사용자 관점에서 테스트**: SDK 사용자가 실제로 호출할 public API 중심
2. **다양한 환경 고려**: 다양한 Spring Boot 버전, Java 버전에서 동작 검증
3. **문서화된 동작 보장**: README나 JavaDoc에 명시된 동작을 정확히 테스트
4. **하위 호환성 유지**: 버전 업그레이드 시 기존 코드가 깨지지 않도록 보장

## SDK 테스트 계층

### 1. 단위 테스트 (Unit Tests) - 60%

**목적**: 개별 컴포넌트의 정확성 검증

```java
// GraphQL 클라이언트 테스트
@ExtendWith(MockitoExtension.class)
class ShopifyGraphQLClientTest {
    
    @Mock
    private OkHttpClient httpClient;
    
    @Mock
    private ObjectMapper objectMapper;
    
    @Mock
    private RateLimiter rateLimiter;
    
    private ShopifyGraphQLClient client;
    
    @Test
    void testExecuteQuery_Success() {
        // Given: Mock HTTP 응답 설정
        String mockResponse = """
            {
                "data": {
                    "product": {
                        "id": "gid://shopify/Product/123",
                        "title": "Test Product"
                    }
                }
            }
            """;
        
        Response response = new Response.Builder()
            .request(new Request.Builder().url("https://test.com").build())
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body(ResponseBody.create(mockResponse, MediaType.parse("application/json")))
            .build();
        
        when(httpClient.newCall(any())).thenReturn(call);
        when(call.execute()).thenReturn(response);
        
        // When: GraphQL 쿼리 실행
        GraphQLRequest request = GraphQLRequest.builder()
            .query("{ product(id: $id) { id title } }")
            .variables(Map.of("id", "gid://shopify/Product/123"))
            .build();
        
        GraphQLResponse<ProductData> result = client.execute(request, ProductData.class);
        
        // Then: 응답 검증
        assertNotNull(result);
        assertNotNull(result.getData());
        assertEquals("Test Product", result.getData().getProduct().getTitle());
    }
    
    @Test
    void testRateLimitHandling() {
        // Rate limit 시나리오 테스트
        when(rateLimiter.tryAcquire(anyLong(), any())).thenReturn(false);
        
        assertThrows(ShopifyRateLimitException.class, () -> {
            client.execute(request, ProductData.class);
        });
    }
}
```

### 2. 통합 테스트 (Integration Tests) - 20%

**목적**: 컴포넌트 간 상호작용 및 Spring 통합 검증

```java
@SpringBootTest
@AutoConfigureMockMvc
class ShopifySDKSpringIntegrationTest {
    
    @Autowired
    private ProductService productService;
    
    @MockBean
    private ShopifyGraphQLClient graphQLClient;
    
    @Test
    void testServiceAutowiring() {
        // Spring 컨텍스트에서 서비스가 올바르게 주입되는지 확인
        assertNotNull(productService);
    }
    
    @Test
    void testConfigurationProperties() {
        // application.yml 설정이 올바르게 로드되는지 확인
        ShopifyProperties properties = context.getBean(ShopifyProperties.class);
        assertEquals("2025-07", properties.getApi().getVersion());
    }
}
```

### 3. 계약 테스트 (Contract Tests) - 10%

**목적**: Shopify API와의 계약(스키마) 준수 검증

```java
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShopifyAPIContractTest {
    
    private MockWebServer mockServer;
    private ShopifyGraphQLClient client;
    
    @BeforeAll
    void setUp() throws IOException {
        mockServer = new MockWebServer();
        mockServer.start();
        
        // 실제 Shopify API 응답 구조를 모방
        client = new ShopifyGraphQLClient(
            ShopifyAuthContext.builder()
                .shopDomain("localhost:" + mockServer.getPort())
                .accessToken("test-token")
                .apiVersion("2025-07")
                .build()
        );
    }
    
    @Test
    void testProductQueryContract() throws InterruptedException {
        // Given: Shopify API 계약에 따른 응답
        String shopifyResponse = """
            {
                "data": {
                    "product": {
                        "id": "gid://shopify/Product/123",
                        "title": "Sample Product",
                        "handle": "sample-product",
                        "status": "ACTIVE",
                        "variants": {
                            "edges": [{
                                "node": {
                                    "id": "gid://shopify/ProductVariant/456",
                                    "sku": "SKU123",
                                    "price": {
                                        "amount": "29.99",
                                        "currencyCode": "USD"
                                    }
                                }
                            }]
                        }
                    }
                },
                "extensions": {
                    "cost": {
                        "requestedQueryCost": 10,
                        "actualQueryCost": 8,
                        "throttleStatus": {
                            "maximumAvailable": 1000,
                            "currentlyAvailable": 992,
                            "restoreRate": 50
                        }
                    }
                }
            }
            """;
        
        mockServer.enqueue(new MockResponse()
            .setBody(shopifyResponse)
            .setResponseCode(200)
            .addHeader("Content-Type", "application/json"));
        
        // When: SDK를 통한 호출
        Product product = productService.getProduct(authContext, "gid://shopify/Product/123");
        
        // Then: 응답이 모델에 올바르게 매핑되는지 검증
        assertNotNull(product);
        assertEquals("Sample Product", product.getTitle());
        assertEquals(ProductStatus.ACTIVE, product.getStatus());
        assertNotNull(product.getVariants());
        assertEquals(1, product.getVariants().getEdges().size());
        
        // API 호출 검증
        RecordedRequest request = mockServer.takeRequest();
        assertEquals("POST", request.getMethod());
        assertTrue(request.getPath().contains("/admin/api/2025-07/graphql.json"));
        assertEquals("test-token", request.getHeader("X-Shopify-Access-Token"));
    }
}
```

### 4. 예제 코드 테스트 (Example Tests) - 10%

**목적**: 문서에 있는 예제 코드가 실제로 동작하는지 검증

```java
class DocumentationExamplesTest {
    
    @Test
    void testReadmeQuickStartExample() {
        // README.md의 Quick Start 예제가 컴파일되고 실행되는지 확인
        ShopifyAuthContext authContext = ShopifyAuthContext.builder()
            .shopDomain("test-store.myshopify.com")
            .accessToken("test-token")
            .apiVersion("2025-07")
            .build();
        
        ProductInput input = ProductInput.builder()
            .title("Amazing Product")
            .handle("amazing-product")
            .description("This is an amazing product!")
            .vendor("My Company")
            .status(ProductStatus.ACTIVE)
            .tags(Arrays.asList("new", "featured"))
            .build();
        
        // 컴파일되고 예외 없이 실행되는지만 확인
        assertDoesNotThrow(() -> {
            // 실제 API 호출은 하지 않고 객체 생성만 테스트
            assertNotNull(input);
            assertNotNull(authContext);
        });
    }
}
```

## SDK 특화 테스트 패턴

### 1. Mock Server 활용

```java
public class MockShopifyServer {
    private final MockWebServer server = new MockWebServer();
    
    public void stubProductQuery(String productId, Product product) {
        server.enqueue(new MockResponse()
            .setBody(toGraphQLResponse(product))
            .setResponseCode(200)
            .addHeader("Content-Type", "application/json")
            .addHeader("X-Shopify-Shop-Api-Call-Limit", "40/40"));
    }
    
    public void stubRateLimit() {
        server.enqueue(new MockResponse()
            .setResponseCode(429)
            .addHeader("Retry-After", "2.0"));
    }
}
```

### 2. 다양한 시나리오 테스트

```java
@ParameterizedTest
@ValueSource(strings = {"2024-10", "2025-01", "2025-07"})
void testMultipleApiVersions(String apiVersion) {
    // 여러 API 버전에서 SDK가 동작하는지 확인
    ShopifyAuthContext context = ShopifyAuthContext.builder()
        .shopDomain("test.myshopify.com")
        .accessToken("token")
        .apiVersion(apiVersion)
        .build();
    
    // 각 버전에서 기본 기능이 동작하는지 검증
}

@Test
void testLargeResponseHandling() {
    // 대용량 응답 처리 (1000개 이상의 상품)
    List<Product> products = IntStream.range(0, 1000)
        .mapToObj(i -> createTestProduct(i))
        .collect(Collectors.toList());
    
    mockServer.enqueue(createBulkResponse(products));
    
    // 메모리 문제 없이 처리되는지 확인
}
```

### 3. 에러 시나리오 완벽 커버

```java
@Test
void testNetworkTimeout() {
    mockServer.enqueue(new MockResponse()
        .setBodyDelay(35, TimeUnit.SECONDS)); // 타임아웃 발생
    
    assertThrows(SocketTimeoutException.class, () -> {
        productService.getProduct(authContext, "123");
    });
}

@Test
void testMalformedResponse() {
    mockServer.enqueue(new MockResponse()
        .setBody("{ invalid json"));
    
    assertThrows(ShopifyApiException.class, () -> {
        productService.getProduct(authContext, "123");
    });
}
```

## 테스트 자동화 및 CI/CD

### GitHub Actions 설정

```yaml
name: SDK Tests

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    strategy:
      matrix:
        java: [17, 21]
        spring-boot: [3.1.0, 3.2.0, 3.3.0]
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK ${{ matrix.java }}
      uses: actions/setup-java@v3
      with:
        java-version: ${{ matrix.java }}
    
    - name: Test with Spring Boot ${{ matrix.spring-boot }}
      run: |
        sed -i "s/3.2.0/${{ matrix.spring-boot }}/g" build.gradle
        ./gradlew clean test
    
    - name: Upload coverage to Codecov
      uses: codecov/codecov-action@v3
```

## SDK 사용자를 위한 테스트 가이드

SDK에 포함할 테스트 유틸리티:

```java
// SDK 사용자가 자신의 코드를 테스트할 때 사용할 수 있는 유틸리티
public class ShopifyTestUtils {
    
    public static Product createTestProduct(String id, String title) {
        return Product.builder()
            .id(new ID(id))
            .title(title)
            .status(ProductStatus.ACTIVE)
            .build();
    }
    
    public static ShopifyAuthContext createTestContext() {
        return ShopifyAuthContext.builder()
            .shopDomain("test.myshopify.com")
            .accessToken("test-token")
            .apiVersion("2025-07")
            .build();
    }
}

// 테스트용 Mock 빌더
public class ShopifyMockBuilder {
    
    public ProductService mockProductService() {
        ProductService mock = Mockito.mock(ProductService.class);
        when(mock.getProduct(any(), anyString()))
            .thenReturn(createTestProduct("123", "Test Product"));
        return mock;
    }
}
```

## 권장 커버리지 목표

| 컴포넌트 | 목표 커버리지 | 이유 |
|---------|-------------|------|
| Public API | 95%+ | SDK 사용자가 직접 호출하는 메서드 |
| 에러 처리 | 90%+ | 예외 상황 대응이 중요 |
| 내부 유틸리티 | 70%+ | 간접적으로 테스트됨 |
| 모델 클래스 | 60%+ | 주로 데이터 홀더 역할 |

## 결론

SDK 테스트는 **사용자 관점**에서 **안정성**과 **호환성**을 보장하는 것이 핵심입니다. 
Mock Server를 활용한 계약 테스트와 다양한 환경에서의 통합 테스트를 통해 
SDK 사용자들이 안심하고 사용할 수 있는 라이브러리를 제공해야 합니다.