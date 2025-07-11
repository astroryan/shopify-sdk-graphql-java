# Shopify Spring SDK

A comprehensive Java Spring SDK for the Shopify Admin GraphQL API, providing type-safe access to all Shopify Admin API features.

## Features

- 🚀 **Complete API Coverage**: Implements all Shopify Admin GraphQL API modules
- 🔒 **Type-Safe**: Fully typed models and responses with Jackson serialization
- 🔄 **Automatic Retry**: Built-in retry mechanism for rate limits and transient errors
- 🏗️ **Spring Boot Integration**: Seamless integration with Spring Boot applications
- 📊 **GraphQL Support**: Custom scalar types for Shopify-specific data types
- 🧪 **Well-Tested**: Comprehensive unit and integration tests

## Requirements

- Java 17 or higher
- Spring Boot 3.2.0 or higher
- Gradle 8.0 or higher

## Installation

### GitHub Packages

This SDK is published to GitHub Packages. To use it, you need to configure your Gradle build to authenticate with GitHub Packages.

#### 1. Create a Personal Access Token

Create a GitHub Personal Access Token with the `read:packages` scope:
1. Go to GitHub Settings → Developer settings → Personal access tokens
2. Generate new token (classic)
3. Select the `read:packages` scope
4. Save your token securely

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

Either set environment variables:
```bash
export GITHUB_ACTOR=your-github-username
export GITHUB_TOKEN=your-personal-access-token
```

Or add to `~/.gradle/gradle.properties`:
```properties
gpr.user=your-github-username
gpr.key=your-personal-access-token
```

## Configuration

Configure the SDK in your `application.yml` or `application.properties`:

```yaml
shopify:
  sdk:
    api:
      version: "2025-07"
    request-timeout: 30000
    max-retries: 3
    retry-delay: 1000
```

## Quick Start

### 1. Create Authentication Context

```java
import com.shopify.sdk.config.ShopifyAuthContext;

// Create auth context for your store
ShopifyAuthContext authContext = new ShopifyAuthContext(
    "your-store.myshopify.com",
    "your-access-token"
);
```

### 2. Use Services

```java
import com.shopify.sdk.service.ProductService;
import com.shopify.sdk.model.products.*;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class MyShopifyService {
    
    @Autowired
    private ProductService productService;
    
    public void createProduct() {
        // Create a new product
        ProductInput input = ProductInput.builder()
            .title("Amazing Product")
            .handle("amazing-product")
            .description("This is an amazing product!")
            .vendor("My Company")
            .status(ProductStatus.ACTIVE)
            .tags(Arrays.asList("new", "featured"))
            .build();
        
        Product product = productService.createProduct(authContext, input);
        System.out.println("Created product: " + product.getId());
    }
    
    public void listProducts() {
        // List products with pagination
        List<Product> products = productService.getProducts(
            authContext,
            10,           // first
            null,         // after cursor
            null,         // query
            ProductSortKeys.TITLE,
            false         // reverse
        );
        
        products.forEach(p -> 
            System.out.println(p.getTitle() + " - " + p.getStatus())
        );
    }
}
```

## Supported Modules

The SDK provides comprehensive coverage of all Shopify Admin API modules:

### Core Commerce
- ✅ **Products & Collections**: Full product catalog management
- ✅ **Orders**: Order creation, fulfillment, and management
- ✅ **Customers**: Customer data and segmentation
- ✅ **Inventory**: Multi-location inventory tracking

### Financial
- ✅ **Billing**: App billing and subscriptions
- ✅ **Shopify Payments**: Payment processing and payouts
- ✅ **Discounts**: Discount codes and automatic discounts

### Operational
- ✅ **Shipping & Fulfillment**: Shipping rates and fulfillment services
- ✅ **Online Store**: Themes, pages, blogs, and navigation
- ✅ **Retail**: POS operations and device management

### Advanced Features
- ✅ **B2B**: Wholesale and B2B company management
- ✅ **Markets**: Multi-currency and international selling
- ✅ **Metafields & Metaobjects**: Custom data structures
- ✅ **Apps**: App installations and configurations
- ✅ **Access**: Authentication and authorization
- ✅ **Bulk Operations**: Large-scale data operations
- ✅ **Cart**: Cart transformation and management
- ✅ **Checkout Branding**: Checkout customization
- ✅ **Events**: Event tracking and webhooks
- ✅ **Localizations**: Multi-language support
- ✅ **Marketplaces**: Marketplace integrations
- ✅ **Privacy**: GDPR and privacy compliance
- ✅ **Store Properties**: Store configuration
- ✅ **Webhooks**: Real-time event notifications

## Advanced Usage

### Custom GraphQL Queries

For cases where you need custom queries:

```java
@Autowired
private ShopifyGraphQLClient graphQLClient;

public void customQuery() {
    String query = """
        query getProductWithMetafields($id: ID!) {
            product(id: $id) {
                id
                title
                metafields(first: 10) {
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
        """;
    
    Map<String, Object> variables = new HashMap<>();
    variables.put("id", "gid://shopify/Product/123");
    
    GraphQLRequest request = GraphQLRequest.builder()
        .query(query)
        .variables(variables)
        .build();
    
    GraphQLResponse<ProductResponse> response = graphQLClient.execute(
        request,
        new TypeReference<GraphQLResponse<ProductResponse>>() {}
    );
}
```

### Error Handling

The SDK provides comprehensive error handling:

```java
try {
    Product product = productService.createProduct(authContext, input);
} catch (ShopifyApiException e) {
    if (e.isRateLimited()) {
        // Handle rate limiting
        System.err.println("Rate limited. Retry after: " + e.getRetryAfter());
    } else if (e.hasUserErrors()) {
        // Handle validation errors
        e.getUserErrors().forEach(error -> 
            System.err.println("Field: " + error.getField() + 
                             ", Message: " + error.getMessage())
        );
    } else {
        // Handle other errors
        System.err.println("API Error: " + e.getMessage());
    }
}
```

### Bulk Operations

For large-scale operations:

```java
@Autowired
private BulkOperationService bulkOperationService;

public void exportProducts() {
    String query = """
        {
            products {
                edges {
                    node {
                        id
                        title
                        variants {
                            edges {
                                node {
                                    id
                                    sku
                                    price
                                }
                            }
                        }
                    }
                }
            }
        }
        """;
    
    BulkOperation operation = bulkOperationService.createBulkQuery(
        authContext, 
        query
    );
    
    // Wait for completion
    BulkOperation completed = bulkOperationService.waitForCompletion(
        authContext,
        operation.getId().getValue(),
        300000 // 5 minute timeout
    );
    
    if (completed.getStatus() == BulkOperationStatus.COMPLETED) {
        String resultUrl = completed.getUrl();
        // Download and process results
    }
}
```

### Webhooks

Register and manage webhooks:

```java
@Autowired
private WebhookService webhookService;

public void registerWebhook() {
    WebhookSubscriptionInput input = WebhookSubscriptionInput.builder()
        .topic(WebhookSubscriptionTopic.PRODUCTS_CREATE)
        .callbackUrl("https://myapp.com/webhooks/products/create")
        .format(WebhookSubscriptionFormat.JSON)
        .build();
    
    WebhookSubscription webhook = webhookService.createWebhookSubscription(
        authContext,
        input
    );
    
    System.out.println("Webhook registered: " + webhook.getId());
}
```

### OAuth Authentication

Complete OAuth flow implementation:

```java
@RestController
@RequestMapping("/shopify")
public class ShopifyAuthController {
    
    @Autowired
    private ShopifyOAuthService oAuthService;
    
    @GetMapping("/install")
    public String install(@RequestParam String shop) {
        String authUrl = oAuthService.buildAuthorizationUrl(
            shop,
            "your-client-id",
            "https://yourapp.com/shopify/callback",
            Arrays.asList("read_products", "write_products"),
            "random-state-value"
        );
        
        return "redirect:" + authUrl;
    }
    
    @GetMapping("/callback")
    public ShopifyAuthContext callback(
            @RequestParam String code,
            @RequestParam String shop,
            @RequestParam String state) {
        
        // Exchange code for access token
        AccessTokenResponse tokenResponse = oAuthService.exchangeCodeForToken(
            shop,
            code,
            "your-client-id",
            "your-client-secret"
        );
        
        // Create auth context
        return new ShopifyAuthContext(shop, tokenResponse.getAccessToken());
    }
}
```

## Testing

### Unit Tests

Run unit tests with:

```bash
./gradlew test
```

### Integration Tests

To run integration tests, set up environment variables:

```bash
export SHOPIFY_TEST_STORE_DOMAIN="your-test-store.myshopify.com"
export SHOPIFY_TEST_ACCESS_TOKEN="your-test-access-token"

./gradlew test --tests "*IntegrationTest"
```

## Thread Safety

The SDK is designed to be thread-safe. The `ShopifyAuthContext` uses ThreadLocal storage, allowing different threads to work with different stores simultaneously:

```java
// Thread 1
ShopifyAuthContext.set(new ShopifyAuthContext("store1.myshopify.com", "token1"));
productService.getProducts(...);

// Thread 2 (concurrent)
ShopifyAuthContext.set(new ShopifyAuthContext("store2.myshopify.com", "token2"));
productService.getProducts(...);
```

## Best Practices

1. **Reuse Service Instances**: Services are stateless and thread-safe, autowire them as singletons
2. **Handle Rate Limits**: The SDK automatically retries on rate limits, but consider implementing backoff strategies for bulk operations
3. **Use Pagination**: Always use cursor-based pagination for large datasets
4. **Cache Results**: Consider caching frequently accessed data like shop information
5. **Monitor API Usage**: Track your API call usage to stay within Shopify's limits

## Contributing

Contributions are welcome! Please read our contributing guidelines and submit pull requests to our repository.

## License

This SDK is licensed under the MIT License. See the LICENSE file for details.

## Support

For issues and feature requests, please use the GitHub issue tracker.

For Shopify API documentation, visit: https://shopify.dev/docs/api/admin-graphql