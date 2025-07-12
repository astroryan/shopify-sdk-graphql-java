# Shopify Spring SDK

A comprehensive, enterprise-grade Java Spring SDK for the Shopify Admin GraphQL API. This SDK provides type-safe, reactive access to all Shopify Admin API features with built-in Spring Boot integration.

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2+-green.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-8.0+-blue.svg)](https://gradle.org/)
[![GitHub Packages](https://img.shields.io/badge/Published%20on-GitHub%20Packages-blue.svg)](https://github.com/astroryan/shopify-sdk-graphql-java/packages)

## 🚀 Features

### Core Capabilities
- **🎯 Complete API Coverage**: Full implementation of Shopify Admin GraphQL API v2025-07
- **🔒 Type-Safe**: Fully typed models with Jackson serialization and validation
- **⚡ Reactive Programming**: Built on Spring WebFlux for high-performance asynchronous operations
- **🔄 Automatic Retry**: Intelligent retry mechanisms for rate limits and transient errors
- **🏗️ Spring Boot Integration**: Seamless auto-configuration and dependency injection
- **📊 GraphQL & REST**: Support for both GraphQL and REST API endpoints

### Advanced Features
- **🔐 OAuth 2.0 Flow**: Complete OAuth implementation with JWT validation
- **🌐 Session Management**: Thread-safe session handling with multiple store support
- **📈 Rate Limiting**: Built-in rate limit handling with exponential backoff
- **🔍 Monitoring**: Comprehensive metrics and logging for API operations
- **🔗 Webhook Processing**: Event-driven webhook handling with validation
- **📦 Bulk Operations**: Large-scale data operations with progress tracking
- **🧪 Test-Friendly**: Comprehensive testing utilities and mock server support

## 📋 Requirements

- **Java**: 17 or higher
- **Spring Boot**: 3.2.0 or higher
- **Gradle**: 8.0 or higher

## 🛠️ Installation

### GitHub Packages Setup

This SDK is published to GitHub Packages. Follow these steps to integrate it into your project:

#### 1. Generate GitHub Personal Access Token

1. Navigate to GitHub Settings → Developer settings → Personal access tokens
2. Generate a new token (classic) with `read:packages` scope
3. Save the token securely

#### 2. Configure Gradle

Add the following to your `build.gradle`:

```gradle
repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/astroryan/shopify-sdk-graphql-java")
        credentials {
            username = project.findProperty("gpr.user") ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("gpr.key") ?: System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation 'com.shopify:shopify-spring-sdk:1.1.0'
}
```

#### 3. Set Authentication

**Option A: Environment Variables**
```bash
export GITHUB_ACTOR=your-github-username
export GITHUB_TOKEN=your-personal-access-token
```

**Option B: Gradle Properties** (`~/.gradle/gradle.properties`)
```properties
gpr.user=your-github-username
gpr.key=your-personal-access-token
```

## ⚙️ Configuration

Configure the SDK in your `application.yml`:

```yaml
shopify:
  api-key: ${SHOPIFY_API_KEY}
  api-secret-key: ${SHOPIFY_API_SECRET}
  scopes: 
    - read_products
    - write_products
    - read_orders
    - write_orders
  host-name: ${SHOPIFY_APP_HOST}
  api-version: "2025-07"
  is-embedded-app: true
  logging:
    level: INFO
    http-requests: false
    timestamps: true
```

## 🚀 Quick Start

### Basic Usage with Spring Dependency Injection

```java
import com.shopify.sdk.ShopifyApi;
import com.shopify.sdk.service.product.ProductService;
import com.shopify.sdk.model.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ShopifyIntegrationService {
    
    @Autowired
    private ShopifyApi shopifyApi;
    
    public Mono<Product> getProduct(String shop, String accessToken, String productId) {
        return shopifyApi.forShop(shop, accessToken)
            .products()
            .getProduct(shop, accessToken, productId);
    }
}
```

### Product Management Example

```java
@Service
public class ProductManagementService {
    
    @Autowired
    private ProductService productService;
    
    public Mono<Product> createProduct(String shop, String accessToken) {
        ProductInput input = ProductInput.builder()
            .title("Amazing Product")
            .handle("amazing-product")
            .description("This is an amazing product!")
            .vendor("My Company")
            .productType("Electronics")
            .tags(Arrays.asList("new", "featured", "electronics"))
            .status(ProductStatus.ACTIVE)
            .build();
        
        return productService.createProduct(shop, accessToken, input)
            .doOnSuccess(product -> 
                log.info("Created product: {} with ID: {}", 
                    product.getTitle(), product.getId())
            )
            .doOnError(error -> 
                log.error("Failed to create product: {}", error.getMessage())
            );
    }
    
    public Mono<ProductConnection> listProducts(String shop, String accessToken) {
        return productService.getProducts(shop, accessToken, 50, null, "status:active")
            .doOnSuccess(connection -> 
                log.info("Retrieved {} products", 
                    connection.getEdges().size())
            );
    }
}
```

### OAuth Authentication Flow

```java
@RestController
@RequestMapping("/auth")
public class ShopifyAuthController {
    
    @Autowired
    private ShopifyApi shopifyApi;
    
    @GetMapping("/install")
    public String initiateOAuth(@RequestParam String shop) {
        String authUrl = shopifyApi.getOAuth().buildAuthorizationUrl(
            shop,
            "your-client-id",
            "https://yourapp.com/auth/callback",
            Arrays.asList("read_products", "write_products", "read_orders"),
            "secure-random-state"
        );
        
        return "redirect:" + authUrl;
    }
    
    @GetMapping("/callback")
    public ResponseEntity<Map<String, String>> handleCallback(
            @RequestParam String code,
            @RequestParam String shop,
            @RequestParam String state) {
        
        try {
            AccessTokenResponse tokenResponse = shopifyApi.getOAuth()
                .exchangeCodeForToken(shop, code, "your-client-id", "your-client-secret");
            
            // Store the access token securely
            String accessToken = tokenResponse.getAccessToken();
            
            Map<String, String> response = Map.of(
                "status", "success",
                "shop", shop,
                "message", "App installed successfully"
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = Map.of(
                "status", "error",
                "message", "OAuth flow failed: " + e.getMessage()
            );
            return ResponseEntity.badRequest().body(error);
        }
    }
}
```

## 📚 Comprehensive API Coverage

### E-Commerce Core
- **Products & Collections**: Complete product catalog management with variants, images, and SEO
- **Orders**: Order lifecycle management, fulfillment, refunds, and tracking
- **Customers**: Customer data management, segments, and lifecycle tracking
- **Inventory**: Multi-location inventory tracking and management

### Financial Operations
- **Billing**: App billing, subscriptions, and usage-based charging
- **Payments**: Payment processing, refunds, and transaction management
- **Discounts**: Price rules, discount codes, and automatic discounts

### Store Operations
- **Shipping & Fulfillment**: Shipping rates, fulfillment services, and delivery tracking
- **Online Store**: Themes, pages, blogs, navigation, and SEO management
- **Locations**: Multi-location management and inventory distribution

### Advanced Features
- **B2B Commerce**: Wholesale pricing, company management, and B2B catalogs
- **International Markets**: Multi-currency, localization, and market-specific features
- **Metafields & Metaobjects**: Custom data structures and extended attributes
- **Webhooks**: Real-time event notifications and processing
- **Bulk Operations**: Large-scale data imports, exports, and transformations

## 🔧 Advanced Usage

### Custom GraphQL Queries

```java
@Service
public class CustomQueryService {
    
    @Autowired
    private ShopifyGraphQLClient graphQLClient;
    
    public Mono<JsonNode> getProductWithMetafields(String shop, String accessToken, String productId) {
        String query = """
            query getProductWithMetafields($id: ID!) {
                product(id: $id) {
                    id
                    title
                    handle
                    metafields(first: 10) {
                        edges {
                            node {
                                namespace
                                key
                                value
                                type
                            }
                        }
                    }
                    variants(first: 10) {
                        edges {
                            node {
                                id
                                title
                                price
                                inventoryQuantity
                                metafields(first: 5) {
                                    edges {
                                        node {
                                            namespace
                                            key
                                            value
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            """;
        
        Map<String, Object> variables = Map.of("id", productId);
        
        return graphQLClient.query(shop, accessToken, query, variables)
            .map(GraphQLResponse::getData);
    }
}
```

### Error Handling and Resilience

```java
@Service
public class ResilientShopifyService {
    
    @Autowired
    private ProductService productService;
    
    public Mono<Product> createProductWithRetry(String shop, String accessToken, ProductInput input) {
        return productService.createProduct(shop, accessToken, input)
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                .filter(throwable -> throwable instanceof ShopifyApiException &&
                    ((ShopifyApiException) throwable).isRateLimited())
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                    new ShopifyApiException("Maximum retries exceeded for product creation")
                )
            )
            .onErrorResume(ShopifyApiException.class, error -> {
                if (error.hasUserErrors()) {
                    log.error("Product creation failed with validation errors:");
                    error.getUserErrors().forEach(userError ->
                        log.error("  - {}: {}", userError.getField(), userError.getMessage())
                    );
                }
                return Mono.error(error);
            });
    }
}
```

### Bulk Operations

```java
@Service
public class BulkDataService {
    
    @Autowired
    private BulkOperationService bulkService;
    
    public Mono<String> exportAllProducts(String shop, String accessToken) {
        String query = """
            {
                products {
                    edges {
                        node {
                            id
                            title
                            handle
                            status
                            createdAt
                            variants {
                                edges {
                                    node {
                                        id
                                        title
                                        price
                                        sku
                                        inventoryQuantity
                                    }
                                }
                            }
                        }
                    }
                }
            }
            """;
        
        return bulkService.createBulkQuery(shop, accessToken, query)
            .flatMap(operation -> 
                bulkService.waitForCompletion(shop, accessToken, operation.getId().getValue(), 300000)
            )
            .map(completedOperation -> {
                if (completedOperation.getStatus() == BulkOperationStatus.COMPLETED) {
                    return completedOperation.getUrl();
                } else {
                    throw new ShopifyApiException("Bulk operation failed: " + completedOperation.getErrorCode());
                }
            });
    }
}
```

### Webhook Processing

```java
@RestController
@RequestMapping("/webhooks")
public class WebhookController {
    
    @Autowired
    private WebhookProcessor webhookProcessor;
    
    @PostMapping("/orders/create")
    public ResponseEntity<String> handleOrderCreate(
            @RequestBody String payload,
            @RequestHeader Map<String, String> headers) {
        
        try {
            WebhookEvent event = webhookProcessor.processWebhook(payload, headers);
            
            if (event.getTopic() == WebhookEventType.ORDERS_CREATE) {
                Order order = (Order) event.getData();
                // Process new order
                log.info("New order received: {} for customer: {}", 
                    order.getName(), order.getCustomer().getEmail());
            }
            
            return ResponseEntity.ok("Webhook processed successfully");
        } catch (ShopifyWebhookException e) {
            log.error("Webhook processing failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Webhook validation failed");
        }
    }
}
```

## 🧪 Testing

### Unit Tests

```bash
# Run all unit tests
./gradlew test

# Run with coverage
./gradlew test jacocoTestReport

# View coverage report
open build/reports/jacoco/test/html/index.html
```

### Integration Tests

Set up environment variables for integration testing:

```bash
export SHOPIFY_TEST_STORE_DOMAIN="your-dev-store.myshopify.com"
export SHOPIFY_TEST_ACCESS_TOKEN="your-private-app-token"

# Run integration tests
./gradlew integrationTest
```

### Test Configuration

```java
@TestConfiguration
public class ShopifyTestConfig {
    
    @Bean
    @Primary
    public ShopifyGraphQLClient mockGraphQLClient() {
        return Mockito.mock(ShopifyGraphQLClient.class);
    }
    
    @Bean
    public WireMockServer wireMockServer() {
        WireMockServer server = new WireMockServer(8080);
        server.start();
        return server;
    }
}
```

## 🔒 Security Best Practices

### Secure Token Management

```java
@Configuration
public class SecurityConfig {
    
    @Value("${shopify.webhook.secret}")
    private String webhookSecret;
    
    @Bean
    public WebhookValidator webhookValidator() {
        return new HmacWebhookValidator(webhookSecret);
    }
    
    @Bean
    public SessionStore sessionStore() {
        // Use Redis or database for production
        return new RedisSessionStore(redisTemplate);
    }
}
```

### Environment-Based Configuration

```yaml
# application-prod.yml
shopify:
  api-key: ${SHOPIFY_API_KEY}
  api-secret-key: ${SHOPIFY_API_SECRET}
  logging:
    http-requests: false
    level: WARN

# application-dev.yml
shopify:
  api-key: ${SHOPIFY_DEV_API_KEY}
  api-secret-key: ${SHOPIFY_DEV_API_SECRET}
  logging:
    http-requests: true
    level: DEBUG
```

## 📊 Monitoring and Observability

### Metrics Integration

```java
@Component
public class ShopifyMetrics {
    
    private final MeterRegistry meterRegistry;
    private final Counter apiCallCounter;
    private final Timer apiResponseTimer;
    
    public ShopifyMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.apiCallCounter = Counter.builder("shopify.api.calls")
            .description("Number of Shopify API calls")
            .register(meterRegistry);
        this.apiResponseTimer = Timer.builder("shopify.api.response.time")
            .description("Shopify API response time")
            .register(meterRegistry);
    }
    
    public void recordApiCall(String endpoint, String status) {
        apiCallCounter.increment(Tags.of(
            Tag.of("endpoint", endpoint),
            Tag.of("status", status)
        ));
    }
}
```

## 🤝 Contributing

We welcome contributions! Please read our [Contributing Guidelines](CONTRIBUTING.md) and submit pull requests to our repository.

### Development Setup

```bash
# Clone the repository
git clone https://github.com/astroryan/shopify-sdk-graphql-java.git
cd shopify-sdk-graphql-java

# Build the project
./gradlew build

# Run tests
./gradlew test

# Run with test coverage
./gradlew test jacocoTestReport
```

## 📖 Documentation

- **API Reference**: [Shopify Admin API](https://shopify.dev/docs/api/admin-graphql)
- **GraphQL Playground**: [GraphiQL App](https://shopify.dev/docs/apps/tools/graphiql-admin-api)
- **Webhook Events**: [Webhook Reference](https://shopify.dev/docs/api/admin/webhooks)

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🛟 Support

- **GitHub Issues**: [Report bugs and request features](https://github.com/astroryan/shopify-sdk-graphql-java/issues)
- **Discussions**: [Community support and questions](https://github.com/astroryan/shopify-sdk-graphql-java/discussions)
- **Shopify Documentation**: [Official API documentation](https://shopify.dev/docs)

## 🚦 Roadmap

- [ ] Shopify Functions support
- [ ] Enhanced TypeScript definitions generation
- [ ] Automated API version updates
- [ ] Performance optimization for large-scale operations
- [ ] Extended monitoring and analytics features

---

**Made with ❤️ for the Shopify developer community**