# Shopify Spring SDK

A comprehensive, enterprise-grade Java Spring SDK for the Shopify Admin GraphQL API. This SDK provides type-safe, reactive access to all Shopify Admin API features with built-in Spring Boot integration.

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2+-green.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-8.0+-blue.svg)](https://gradle.org/)
[![GitHub Packages](https://img.shields.io/badge/Published%20on-GitHub%20Packages-blue.svg)](https://github.com/astroryan/shopify-sdk-java/packages)

## 🚀 Features

### Core Capabilities
- **🎯 Complete API Coverage**: Full implementation of Shopify Admin GraphQL API v2025-07
- **🔒 Type-Safe**: Fully typed models with Jackson serialization and validation
- **⚡ Reactive Programming**: Built on Spring WebFlux for high-performance asynchronous operations
- **🔄 Automatic Retry**: Intelligent retry mechanisms for rate limits and transient errors
- **🏗️ Spring Boot Integration**: Seamless auto-configuration and dependency injection
- **📊 GraphQL & REST**: Support for both GraphQL and REST API endpoints

### Advanced Features
- **🔑 Private App Token Support**: Simple and secure token-based authentication (Recommended)
- **🔐 OAuth 2.0 Flow**: Complete OAuth implementation for public apps (Optional)
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
        url = uri("https://maven.pkg.github.com/astroryan/shopify-sdk-java")
        credentials {
            username = project.findProperty("gpr.user") ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("gpr.key") ?: System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    // Stable release version (recommended for production)
    implementation 'com.shopify:shopify-spring-sdk:1.1.1-RELEASE'
    
    // Or use latest development snapshot (for testing new features)
    // implementation 'com.shopify:shopify-spring-sdk:1.2.0-SNAPSHOT-142'
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

### Recommended: Private App Access Token (Simple & Secure)

For most use cases, Shopify recommends using Private App Access Tokens:

```yaml
shopify:
  # Simple token-based configuration (Recommended)
  admin-api-access-token: ${SHOPIFY_ACCESS_TOKEN}
  api-version: "2025-07"
  
  # Optional logging configuration
  logging:
    level: INFO
    http-requests: false
    timestamps: true
```

### Alternative: OAuth Configuration (For Public Apps)

Only needed if building a public app distributed to multiple stores:

```yaml
shopify:
  # OAuth configuration (only for public apps)
  api-key: ${SHOPIFY_API_KEY}
  api-secret-key: ${SHOPIFY_API_SECRET}
  host-name: ${SHOPIFY_APP_HOST}
  api-version: "2025-07"
  is-embedded-app: true
  
  logging:
    level: INFO
    http-requests: false
    timestamps: true
```

### How to Get Access Token

1. **Create Private App** in your Shopify admin:
   - Go to Settings → Apps and sales channels → Develop apps
   - Click "Create an app" → "Create an app for custom use"
   - Configure Admin API access scopes (e.g., `read_products`, `write_products`)
   - Install the app and copy the Admin API access token

2. **Set Environment Variable**:
   ```bash
   export SHOPIFY_ACCESS_TOKEN="shpat_your-access-token-here"
   ```

## 📋 Version Management

This SDK follows a **branch-based versioning strategy** for different development stages:

### 🏷️ Version Types

| Version Type | Example | Use Case | Stability |
|--------------|---------|----------|-----------|
| **RELEASE** | `1.1.1-RELEASE` | Production deployment | ✅ Stable |
| **SNAPSHOT** | `1.2.0-SNAPSHOT-142` | Development & testing | ⚠️ Unstable |

### 🌳 Branch Strategy

- **`release` branch**: Produces `X.Y.Z-RELEASE` versions for production use
- **`develop` branch**: Produces `X.Y.Z-SNAPSHOT-{BUILD}` versions with auto-incrementing build numbers
- **`main` branch**: Contains stable releases and documentation

### 📦 Choosing the Right Version

**For Production Applications**:
```gradle
dependencies {
    implementation 'com.shopify:shopify-spring-sdk:1.1.1-RELEASE'
}
```

**For Development/Testing**:
```gradle
dependencies {
    implementation 'com.shopify:shopify-spring-sdk:1.2.0-SNAPSHOT-142'
}
```

**Latest Available Versions**:
- 🚀 Latest stable: [`1.1.1-RELEASE`](https://github.com/astroryan/shopify-sdk-java/packages)
- 🔄 Latest snapshot: Check [GitHub Packages](https://github.com/astroryan/shopify-sdk-java/packages) for newest SNAPSHOT
- ✅ GitHub Actions now properly trigger on both `develop` and `release` branch PRs

## 🚀 Quick Start

### Basic Usage with Private App Token (Recommended)

```java
import com.shopify.sdk.service.product.ProductService;
import com.shopify.sdk.model.product.Product;
import com.shopify.sdk.model.product.ProductConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ShopifyIntegrationService {
    
    @Autowired
    private ProductService productService;
    
    @Value("${shopify.admin-api-access-token}")
    private String accessToken;
    
    private final String shopDomain = "your-store.myshopify.com";
    
    public Mono<Product> getProduct(String productId) {
        return productService.getProduct(shopDomain, accessToken, productId);
    }
    
    public Mono<ProductConnection> listProducts() {
        return productService.getProducts(shopDomain, accessToken, 10, null, null);
    }
}
```

### Product Management Example

```java
import com.shopify.sdk.service.product.ProductService;
import com.shopify.sdk.model.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;

@Slf4j
@Service
public class ProductManagementService {
    
    @Autowired
    private ProductService productService;
    
    @Value("${shopify.admin-api-access-token}")
    private String accessToken;
    
    private final String shopDomain = "your-store.myshopify.com";
    
    public Mono<Product> createProduct() {
        ProductInput input = ProductInput.builder()
            .title("Amazing Product")
            .handle("amazing-product")
            .description("This is an amazing product!")
            .vendor("My Company")
            .productType("Electronics")
            .tags(Arrays.asList("new", "featured", "electronics"))
            .status(ProductStatus.ACTIVE)
            .build();
        
        return productService.createProduct(shopDomain, accessToken, input)
            .doOnSuccess(product -> 
                log.info("Created product: {} with ID: {}", 
                    product.getTitle(), product.getId())
            )
            .doOnError(error -> 
                log.error("Failed to create product: {}", error.getMessage())
            );
    }
    
    public Mono<ProductConnection> listActiveProducts() {
        return productService.getProducts(shopDomain, accessToken, 50, null, "status:active")
            .doOnSuccess(connection -> 
                log.info("Retrieved {} products", 
                    connection.getEdges().size())
            );
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

### OAuth Authentication Flow (Advanced - For Public Apps Only)

Most developers should use Private App tokens instead. OAuth is only needed for public apps:

```java
@RestController
@RequestMapping("/auth")
public class ShopifyAuthController {
    
    @Autowired
    private ShopifyApi shopifyApi;
    
    @GetMapping("/install")
    public String initiateOAuth(@RequestParam String shop) {
        // Scopes are defined here during OAuth flow
        List<String> requiredScopes = Arrays.asList(
            "read_products", "write_products", 
            "read_orders", "write_orders"
        );
        
        String authUrl = shopifyApi.getOAuth().buildAuthorizationUrl(
            shop,
            "your-client-id",
            "https://yourapp.com/auth/callback",
            requiredScopes,
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
            
            // Store the access token securely for future API calls
            String accessToken = tokenResponse.getAccessToken();
            
            Map<String, String> response = Map.of(
                "status", "success",
                "shop", shop,
                "token", accessToken  // Store this securely in database
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
# Set your test store domain and access token
export SHOPIFY_TEST_STORE_DOMAIN="your-dev-store.myshopify.com"
export SHOPIFY_ACCESS_TOKEN="shpat_your-private-app-access-token"

# Run integration tests
./gradlew integrationTest
```

**Note**: Use a development store and a test Private App token, never production credentials for testing.

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
  admin-api-access-token: ${SHOPIFY_PROD_ACCESS_TOKEN}
  api-version: "2025-07"
  logging:
    http-requests: false
    level: WARN

# application-dev.yml  
shopify:
  admin-api-access-token: ${SHOPIFY_DEV_ACCESS_TOKEN}
  api-version: "2025-07"
  logging:
    http-requests: true
    level: DEBUG

# application-test.yml
shopify:
  admin-api-access-token: ${SHOPIFY_TEST_ACCESS_TOKEN}
  api-version: "2025-07"
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
git clone https://github.com/astroryan/shopify-sdk-java.git
cd shopify-sdk-java

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

- **GitHub Issues**: [Report bugs and request features](https://github.com/astroryan/shopify-sdk-java/issues)
- **Discussions**: [Community support and questions](https://github.com/astroryan/shopify-sdk-java/discussions)
- **Shopify Documentation**: [Official API documentation](https://shopify.dev/docs)

## 🚦 Roadmap

- [ ] Enhanced Private App Token management and rotation
- [ ] Shopify Functions support
- [ ] Multi-store configuration management
- [ ] Automated API version updates
- [ ] Performance optimization for large-scale operations
- [ ] Extended monitoring and analytics features
- [ ] GraphQL query builder for complex operations

---

Made with ❤️ for the Shopify developer community
