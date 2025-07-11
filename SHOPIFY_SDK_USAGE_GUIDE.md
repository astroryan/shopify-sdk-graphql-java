# Shopify Spring SDK 사용 가이드

## 목차

1. [프로젝트 설정](#1-프로젝트-설정)
2. [기본 구성](#2-기본-구성)
3. [인증 설정](#3-인증-설정)
4. [주요 서비스 사용법](#4-주요-서비스-사용법)
5. [고급 기능](#5-고급-기능)
6. [에러 처리](#6-에러-처리)
7. [베스트 프랙티스](#7-베스트-프랙티스)

## 1. 프로젝트 설정

### Gradle 의존성 추가

```gradle
dependencies {
    implementation 'com.shopify:shopify-spring-sdk:1.1.0'
    implementation 'org.springframework.boot:spring-boot-starter-web:3.2.0'
}
```

### 프로젝트 구조

```text
shopify-spring-sdk/
├── src/main/java/com/shopify/sdk/
│   ├── auth/                    # 인증 관련
│   ├── client/                  # GraphQL 클라이언트
│   ├── config/                  # 설정 클래스
│   ├── exception/               # 예외 처리
│   ├── model/                   # 도메인 모델
│   └── service/                 # 서비스 레이어
```

## 2. 기본 구성

### application.yml 설정

```yaml
shopify:
  sdk:
    api:
      version: "2025-07"
      timeout:
        connect: 10s
        read: 30s
        write: 30s
      retry:
        max-attempts: 3
        backoff-delay: 1000
    graphql:
      endpoint: "https://{shop}.myshopify.com/admin/api/{version}/graphql.json"
      max-query-depth: 10
      max-query-complexity: 1000
    rate-limit:
      enabled: true
      max-calls-per-second: 2
      bucket-size: 40
    logging:
      level: INFO
      log-requests: false
      log-responses: false
```

### Spring Boot Configuration

```java
@Configuration
@EnableConfigurationProperties(ShopifyProperties.class)
public class ShopifyConfig {
    
    @Bean
    public ShopifyGraphQLClient shopifyGraphQLClient(
            ShopifyProperties properties,
            ObjectMapper objectMapper) {
        
        OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(properties.getApi().getTimeout().getConnect())
            .readTimeout(properties.getApi().getTimeout().getRead())
            .writeTimeout(properties.getApi().getTimeout().getWrite())
            .build();
            
        RateLimiter rateLimiter = new RateLimiter(
            properties.getRateLimit().getMaxCallsPerSecond(),
            properties.getRateLimit().getBucketSize()
        );
        
        return new ShopifyGraphQLClient(
            httpClient,
            objectMapper,
            rateLimiter,
            properties
        );
    }
}
```

## 3. 인증 설정

### ShopifyAuthContext 생성

```java
// 방법 1: 직접 생성
ShopifyAuthContext authContext = ShopifyAuthContext.builder()
    .shopDomain("your-store.myshopify.com")
    .accessToken("shpat_your_access_token")
    .apiVersion("2025-07")
    .build();

// 방법 2: ThreadLocal 사용 (멀티테넌트 환경)
@Component
public class ShopifyAuthManager {
    
    private static final ThreadLocal<ShopifyAuthContext> contextHolder = 
        new ThreadLocal<>();
    
    public void setContext(String shopDomain, String accessToken) {
        ShopifyAuthContext context = ShopifyAuthContext.builder()
            .shopDomain(shopDomain)
            .accessToken(accessToken)
            .apiVersion("2025-07")
            .build();
        contextHolder.set(context);
    }
    
    public ShopifyAuthContext getContext() {
        return contextHolder.get();
    }
    
    public void clearContext() {
        contextHolder.remove();
    }
}
```

### OAuth 인증 플로우

```java
@RestController
@RequestMapping("/shopify/auth")
public class ShopifyAuthController {
    
    @Autowired
    private ShopifyOAuthService oAuthService;
    
    @GetMapping("/install")
    public ResponseEntity<String> install(@RequestParam String shop) {
        String authUrl = oAuthService.buildAuthorizationUrl(
            shop,
            "your-client-id",
            "https://yourapp.com/shopify/auth/callback",
            Arrays.asList("read_products", "write_products", "read_orders"),
            UUID.randomUUID().toString() // state for CSRF protection
        );
        
        return ResponseEntity.ok(authUrl);
    }
    
    @GetMapping("/callback")
    public ResponseEntity<AccessToken> callback(
            @RequestParam String code,
            @RequestParam String shop,
            @RequestParam String state) {
        
        // Verify state parameter for CSRF protection
        if (!isValidState(state)) {
            throw new SecurityException("Invalid state parameter");
        }
        
        AccessToken token = oAuthService.exchangeCodeForToken(
            shop,
            code,
            "your-client-id",
            "your-client-secret"
        );
        
        // Store token securely in your database
        saveAccessToken(shop, token.getAccessToken());
        
        return ResponseEntity.ok(token);
    }
}
```

## 4. 주요 서비스 사용법

### 4.1 상품 관리 (ProductService)

```java
@Service
public class MyProductService {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private ShopifyAuthManager authManager;
    
    // 상품 생성
    public Product createProduct(ProductInput input) {
        ShopifyAuthContext context = authManager.getContext();
        
        ProductInput productInput = ProductInput.builder()
            .title("새로운 상품")
            .handle("new-product")
            .description("상품 설명입니다")
            .vendor("My Company")
            .productType("전자제품")
            .status(ProductStatus.ACTIVE)
            .tags(Arrays.asList("신상품", "인기"))
            .seo(SEOInput.builder()
                .title("SEO 제목")
                .description("SEO 설명")
                .build())
            .build();
        
        try {
            Product product = productService.createProduct(context, productInput);
            log.info("상품 생성 완료: {}", product.getId());
            return product;
        } catch (ShopifyApiException e) {
            log.error("상품 생성 실패: {}", e.getMessage());
            throw e;
        }
    }
    
    // 상품 목록 조회
    public List<Product> listProducts(int pageSize, String cursor) {
        ShopifyAuthContext context = authManager.getContext();
        
        ProductConnection connection = productService.listProducts(
            context,
            pageSize,
            cursor,
            "status:active", // GraphQL 쿼리 필터
            ProductStatus.ACTIVE
        );
        
        return connection.getEdges().stream()
            .map(Edge::getNode)
            .collect(Collectors.toList());
    }
    
    // 상품 업데이트
    public Product updateProduct(String productId, ProductInput updates) {
        ShopifyAuthContext context = authManager.getContext();
        
        updates.setId(productId); // 업데이트할 상품 ID 설정
        return productService.updateProduct(context, updates);
    }
    
    // 상품 삭제
    public void deleteProduct(String productId) {
        ShopifyAuthContext context = authManager.getContext();
        productService.deleteProduct(context, productId);
    }
}
```

### 4.2 주문 관리 (OrderService)

```java
@Service
public class MyOrderService {
    
    @Autowired
    private OrderService orderService;
    
    // 주문 목록 조회
    public List<Order> getRecentOrders(int limit) {
        ShopifyAuthContext context = getContext();
        
        return orderService.listOrders(
            context,
            limit,
            null,
            "created_at:>2024-01-01",
            OrderSortKeys.CREATED_AT,
            true // reverse order (최신순)
        );
    }
    
    // 주문 상세 조회
    public Order getOrderDetails(String orderId) {
        ShopifyAuthContext context = getContext();
        return orderService.getOrder(context, orderId);
    }
    
    // 주문 이행 (Fulfillment)
    public Fulfillment fulfillOrder(String orderId, List<LineItemInput> lineItems) {
        ShopifyAuthContext context = getContext();
        
        FulfillmentInput input = FulfillmentInput.builder()
            .orderId(orderId)
            .lineItems(lineItems)
            .notifyCustomer(true)
            .trackingInfo(TrackingInfo.builder()
                .number("1234567890")
                .company("DHL")
                .url("https://tracking.dhl.com/1234567890")
                .build())
            .build();
        
        return orderService.createFulfillment(context, input);
    }
}
```

### 4.3 고객 관리 (CustomerService)

```java
@Service
public class MyCustomerService {
    
    @Autowired
    private CustomerService customerService;
    
    // 고객 생성
    public Customer createCustomer(CustomerInput input) {
        ShopifyAuthContext context = getContext();
        
        CustomerInput customerInput = CustomerInput.builder()
            .email("customer@example.com")
            .firstName("홍")
            .lastName("길동")
            .phone("+82-10-1234-5678")
            .acceptsMarketing(true)
            .tags(Arrays.asList("VIP", "신규"))
            .addresses(Arrays.asList(
                AddressInput.builder()
                    .address1("서울시 강남구 테헤란로 123")
                    .city("서울")
                    .province("서울특별시")
                    .country("KR")
                    .zip("12345")
                    .build()
            ))
            .build();
        
        return customerService.createCustomer(context, customerInput);
    }
    
    // 고객 검색
    public List<Customer> searchCustomers(String query) {
        ShopifyAuthContext context = getContext();
        
        return customerService.searchCustomers(
            context,
            query, // e.g., "email:*@example.com"
            10,
            null
        );
    }
}
```

### 4.4 재고 관리 (InventoryService)

```java
@Service
public class MyInventoryService {
    
    @Autowired
    private InventoryService inventoryService;
    
    // 재고 수준 조정
    public InventoryLevel adjustInventory(
            String inventoryItemId, 
            String locationId, 
            int quantity) {
        
        ShopifyAuthContext context = getContext();
        
        InventoryAdjustmentInput input = InventoryAdjustmentInput.builder()
            .inventoryItemId(inventoryItemId)
            .locationId(locationId)
            .availableDelta(quantity) // 양수: 증가, 음수: 감소
            .build();
        
        return inventoryService.adjustInventoryLevel(context, input);
    }
    
    // 재고 이동
    public void moveInventory(
            String inventoryItemId,
            String fromLocationId,
            String toLocationId,
            int quantity) {
        
        ShopifyAuthContext context = getContext();
        
        InventoryMoveInput input = InventoryMoveInput.builder()
            .inventoryItemId(inventoryItemId)
            .fromLocationId(fromLocationId)
            .toLocationId(toLocationId)
            .quantity(quantity)
            .build();
        
        inventoryService.moveInventory(context, input);
    }
}
```

## 5. 고급 기능

### 5.1 대량 작업 (Bulk Operations)

```java
@Service
public class BulkOperationExample {
    
    @Autowired
    private BulkOperationService bulkOperationService;
    
    public void exportAllProducts() {
        ShopifyAuthContext context = getContext();
        
        // 대량 쿼리 생성
        String bulkQuery = """
            {
                products {
                    edges {
                        node {
                            id
                            title
                            handle
                            vendor
                            variants {
                                edges {
                                    node {
                                        id
                                        sku
                                        price
                                        inventoryQuantity
                                    }
                                }
                            }
                        }
                    }
                }
            }
            """;
        
        // 대량 작업 시작
        BulkOperation operation = bulkOperationService.createBulkQuery(
            context, 
            bulkQuery
        );
        
        // 작업 완료 대기 (폴링)
        BulkOperation completed = waitForCompletion(
            context, 
            operation.getId(),
            Duration.ofMinutes(10)
        );
        
        if (completed.getStatus() == BulkOperationStatus.COMPLETED) {
            // 결과 다운로드 및 처리
            String resultUrl = completed.getUrl();
            processResults(resultUrl);
        }
    }
    
    private void processResults(String url) {
        // JSONL 파일 다운로드 및 처리
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new URL(url).openStream()))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                // 각 라인은 하나의 JSON 객체
                Product product = objectMapper.readValue(line, Product.class);
                processProduct(product);
            }
        } catch (IOException e) {
            log.error("결과 처리 실패", e);
        }
    }
}
```

### 5.2 웹훅 처리

```java
@RestController
@RequestMapping("/webhooks")
public class WebhookController {
    
    @Autowired
    private WebhookService webhookService;
    
    // 웹훅 등록
    @PostMapping("/register")
    public WebhookSubscription registerWebhook() {
        ShopifyAuthContext context = getContext();
        
        WebhookSubscriptionInput input = WebhookSubscriptionInput.builder()
            .topic(WebhookTopic.ORDERS_CREATE)
            .callbackUrl("https://myapp.com/webhooks/orders/create")
            .format(WebhookFormat.JSON)
            .includeFields(Arrays.asList("id", "email", "total_price"))
            .build();
        
        return webhookService.createSubscription(context, input);
    }
    
    // 웹훅 수신 처리
    @PostMapping("/orders/create")
    public ResponseEntity<Void> handleOrderCreated(
            @RequestHeader("X-Shopify-Hmac-Sha256") String hmac,
            @RequestHeader("X-Shopify-Topic") String topic,
            @RequestHeader("X-Shopify-Shop-Domain") String shopDomain,
            @RequestBody String payload) {
        
        // HMAC 검증
        if (!webhookService.verifyWebhook(payload, hmac, "your-webhook-secret")) {
            return ResponseEntity.status(401).build();
        }
        
        // 웹훅 처리
        Order order = objectMapper.readValue(payload, Order.class);
        processNewOrder(order);
        
        return ResponseEntity.ok().build();
    }
}
```

### 5.3 메타필드 사용

```java
@Service
public class MetafieldExample {
    
    @Autowired
    private MetafieldService metafieldService;
    
    public void addProductMetafield(String productId, String namespace, 
                                   String key, String value) {
        ShopifyAuthContext context = getContext();
        
        MetafieldInput input = MetafieldInput.builder()
            .ownerId(productId)
            .namespace(namespace)
            .key(key)
            .value(value)
            .type("single_line_text_field")
            .build();
        
        Metafield metafield = metafieldService.createMetafield(context, input);
        log.info("메타필드 생성: {}", metafield.getId());
    }
    
    public List<Metafield> getProductMetafields(String productId) {
        ShopifyAuthContext context = getContext();
        
        return metafieldService.getMetafields(
            context,
            productId,
            null, // namespace filter
            10,
            null
        );
    }
}
```

### 5.4 GraphQL 직접 사용

```java
@Service
public class CustomGraphQLExample {
    
    @Autowired
    private ShopifyGraphQLClient graphQLClient;
    
    public void customQuery() {
        ShopifyAuthContext context = getContext();
        
        // 커스텀 쿼리
        String query = """
            query getProductWithInventory($id: ID!) {
                product(id: $id) {
                    id
                    title
                    totalInventory
                    variants(first: 10) {
                        edges {
                            node {
                                id
                                sku
                                inventoryItem {
                                    id
                                    tracked
                                    inventoryLevels(first: 10) {
                                        edges {
                                            node {
                                                location {
                                                    id
                                                    name
                                                }
                                                available
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            """;
        
        Map<String, Object> variables = Map.of(
            "id", "gid://shopify/Product/123456789"
        );
        
        GraphQLRequest request = GraphQLRequest.builder()
            .query(query)
            .variables(variables)
            .build();
        
        graphQLClient.setAuthContext(context);
        GraphQLResponse<ProductData> response = graphQLClient.execute(
            request,
            ProductData.class
        );
        
        if (response.hasErrors()) {
            response.getErrors().forEach(error -> 
                log.error("GraphQL 에러: {}", error.getMessage())
            );
        } else {
            Product product = response.getData().getProduct();
            processProductInventory(product);
        }
    }
}
```

## 6. 에러 처리

### 6.1 예외 종류와 처리

```java
@ControllerAdvice
public class ShopifyExceptionHandler {
    
    @ExceptionHandler(ShopifyApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ShopifyApiException e) {
        ErrorResponse response = new ErrorResponse();
        
        if (e instanceof ShopifyRateLimitException) {
            // Rate Limit 에러
            response.setMessage("API 호출 한도 초과");
            response.setRetryAfter(((ShopifyRateLimitException) e).getRetryAfter());
            return ResponseEntity.status(429).body(response);
        }
        
        if (e.hasUserErrors()) {
            // 유효성 검증 에러
            response.setMessage("입력값 검증 실패");
            response.setErrors(e.getUserErrors());
            return ResponseEntity.badRequest().body(response);
        }
        
        // 기타 API 에러
        response.setMessage(e.getMessage());
        return ResponseEntity.status(500).body(response);
    }
}
```

### 6.2 재시도 로직

```java
@Component
public class RetryableShopifyService {
    
    @Retryable(
        value = {ShopifyRateLimitException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public Product createProductWithRetry(ProductInput input) {
        return productService.createProduct(getContext(), input);
    }
    
    @Recover
    public Product recoverFromRateLimit(ShopifyRateLimitException e, 
                                       ProductInput input) {
        log.error("재시도 실패, 대체 로직 실행");
        // 대체 로직 (예: 큐에 저장 후 나중에 처리)
        queueService.enqueue(input);
        return null;
    }
}
```

## 7. 베스트 프랙티스

### 7.1 성능 최적화

```java
@Configuration
public class PerformanceConfig {
    
    // 1. 연결 풀 설정
    @Bean
    public OkHttpClient optimizedHttpClient() {
        return new OkHttpClient.Builder()
            .connectionPool(new ConnectionPool(
                20, // 최대 idle 연결 수
                5,  // keep-alive 시간 (분)
                TimeUnit.MINUTES
            ))
            .connectTimeout(Duration.ofSeconds(10))
            .readTimeout(Duration.ofSeconds(30))
            .writeTimeout(Duration.ofSeconds(30))
            .build();
    }
    
    // 2. 캐싱 설정
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(10))
            .maximumSize(1000));
        return cacheManager;
    }
}

@Service
public class CachedProductService {
    
    @Cacheable(value = "products", key = "#productId")
    public Product getProduct(String productId) {
        return productService.getProduct(getContext(), productId);
    }
    
    @CacheEvict(value = "products", key = "#productId")
    public Product updateProduct(String productId, ProductInput input) {
        return productService.updateProduct(getContext(), input);
    }
}
```

### 7.2 보안 고려사항

```java
@Component
public class SecurityConfig {
    
    // 1. Access Token 암호화 저장
    @Service
    public class SecureTokenService {
        
        @Value("${encryption.key}")
        private String encryptionKey;
        
        public String encryptToken(String token) {
            // AES 암호화 구현
            return encrypt(token, encryptionKey);
        }
        
        public String decryptToken(String encryptedToken) {
            // AES 복호화 구현
            return decrypt(encryptedToken, encryptionKey);
        }
    }
    
    // 2. 웹훅 HMAC 검증
    public boolean verifyWebhookHmac(String payload, String hmacHeader, 
                                     String secret) {
        String calculatedHmac = calculateHmac(payload, secret);
        return MessageDigest.isEqual(
            calculatedHmac.getBytes(),
            hmacHeader.getBytes()
        );
    }
    
    // 3. API 요청 로깅 (민감정보 제외)
    @Bean
    public RequestInterceptor secureLoggingInterceptor() {
        return template -> {
            log.info("API Request: {} {}", 
                template.method(), 
                template.url());
            // Access Token은 로깅하지 않음
        };
    }
}
```

### 7.3 멀티테넌트 지원

```java
@Component
@RequestScope
public class TenantContext {
    
    private String shopDomain;
    private String accessToken;
    
    public ShopifyAuthContext getAuthContext() {
        return ShopifyAuthContext.builder()
            .shopDomain(shopDomain)
            .accessToken(accessToken)
            .apiVersion("2025-07")
            .build();
    }
}

@Component
public class TenantInterceptor implements HandlerInterceptor {
    
    @Autowired
    private TenantContext tenantContext;
    
    @Autowired
    private TokenRepository tokenRepository;
    
    @Override
    public boolean preHandle(HttpServletRequest request, 
                           HttpServletResponse response, 
                           Object handler) {
        
        String shopDomain = request.getHeader("X-Shopify-Shop-Domain");
        if (shopDomain == null) {
            response.setStatus(400);
            return false;
        }
        
        String accessToken = tokenRepository.findByShop(shopDomain);
        
        tenantContext.setShopDomain(shopDomain);
        tenantContext.setAccessToken(accessToken);
        
        return true;
    }
}
```

### 7.4 모니터링 및 로깅

```java
@Aspect
@Component
public class ShopifyServiceMonitor {
    
    private final MeterRegistry meterRegistry;
    
    @Around("@annotation(com.shopify.sdk.annotation.Monitored)")
    public Object monitorApiCall(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        
        return Timer.Sample
            .start(meterRegistry)
            .stop(meterRegistry.timer(
                "shopify.api.call",
                "method", methodName
            ))
            .recordCallable(() -> {
                try {
                    return joinPoint.proceed();
                } catch (ShopifyRateLimitException e) {
                    meterRegistry.counter(
                        "shopify.api.rate_limit",
                        "method", methodName
                    ).increment();
                    throw e;
                } catch (Exception e) {
                    meterRegistry.counter(
                        "shopify.api.error",
                        "method", methodName,
                        "error", e.getClass().getSimpleName()
                    ).increment();
                    throw e;
                }
            });
    }
}
```

## 요약

이 Shopify Spring SDK는 다음과 같은 주요 기능을 제공합니다:

1. **완전한 타입 안정성**: 모든 API 응답이 Java 객체로 매핑
2. **자동 재시도**: Rate Limit 및 일시적 오류에 대한 자동 재시도
3. **Spring Boot 통합**: 의존성 주입 및 설정 관리 용이
4. **멀티테넌트 지원**: 여러 상점을 동시에 처리 가능
5. **포괄적인 에러 처리**: 상세한 에러 정보 제공

SDK를 사용할 때는 항상:

- API 호출 제한을 고려하여 적절한 캐싱 구현
- Access Token을 안전하게 저장 및 관리
- 웹훅 HMAC 검증으로 보안 강화
- 적절한 로깅 및 모니터링 구현

더 자세한 정보는 [Shopify Admin API 문서](https://shopify.dev/docs/api/admin-graphql)를 참조하세요.
