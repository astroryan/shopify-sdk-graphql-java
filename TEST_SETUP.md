# Shopify SDK Test Environment Setup Guide

This guide explains how to set up your test environment for the Shopify Spring SDK.

## Table of Contents
1. [Unit Testing](#unit-testing)
2. [Integration Testing](#integration-testing)
3. [Testing with Real Shopify API](#testing-with-real-shopify-api)
4. [Running Tests](#running-tests)

## Unit Testing

Unit tests use Mockito to mock external dependencies and test individual components in isolation.

### Prerequisites
- Java 17 or higher
- Gradle 7.x or higher

### Running Unit Tests
```bash
./gradlew test
```

This runs all unit tests excluding integration tests.

## Integration Testing

Integration tests use WireMock to simulate Shopify API responses without requiring a real Shopify store.

### Running Integration Tests
```bash
./gradlew integrationTest
```

Integration tests are tagged with `@Tag("integration")` and run separately from unit tests.

## Testing with Real Shopify API

To test against the real Shopify API, you need access to a Shopify store. Here are three methods:

### Method 1: Shopify Partner Account (Recommended)

1. **Create a Shopify Partner Account**
   - Go to https://partners.shopify.com
   - Sign up for a free partner account
   - Create a development store

2. **Create a Custom App**
   - In your development store admin, go to Settings > Apps and sales channels
   - Click "Develop apps"
   - Create a new app
   - Configure API scopes as needed

3. **Get API Credentials**
   - In your app settings, get the API key and secret
   - Generate an access token for your store

### Method 2: Shopify 14-Day Trial

1. **Start a Trial**
   - Go to https://www.shopify.com
   - Start a 14-day free trial
   - Follow the same steps as Method 1 to create a custom app

### Method 3: Mock Server (Development Only)

Use the included WireMock integration tests to simulate Shopify API responses.

## Environment Variables for Integration Tests

Set these environment variables to run integration tests against a real Shopify store:

```bash
export SHOPIFY_TEST_STORE_DOMAIN="your-store.myshopify.com"
export SHOPIFY_TEST_ACCESS_TOKEN="your-access-token"
export SHOPIFY_TEST_API_KEY="your-api-key"
export SHOPIFY_TEST_API_SECRET="your-api-secret"
```

## Test Configuration

### application-test.yml

Create `src/test/resources/application-test.yml`:

```yaml
shopify:
  api:
    key: ${SHOPIFY_TEST_API_KEY:test-api-key}
    secret: ${SHOPIFY_TEST_API_SECRET:test-api-secret}
    version: "2024-01"
  scopes: "read_products,write_products,read_orders,write_orders"
  host:
    name: ${SHOPIFY_TEST_HOST:test-app.example.com}
  session:
    storage: memory
  webhook:
    path: /webhooks
```

### Test Data Setup

For integration tests with real Shopify stores, you may want to:

1. **Create Test Products**
```java
@Test
void setupTestProducts() {
    ProductService.ProductCreateInput product = ProductService.ProductCreateInput.builder()
        .title("Test Product " + System.currentTimeMillis())
        .productType("Test")
        .vendor("Test Vendor")
        .build();
    
    Mono<Product> result = productService.createProduct(shop, accessToken, product);
    // ... assertions
}
```

2. **Clean Up Test Data**
```java
@AfterEach
void cleanup() {
    // Delete test products created during tests
    // Delete test orders if possible
    // Remove test webhooks
}
```

## Test Categories

### 1. Authentication Tests
- OAuth flow validation
- JWT token validation
- Session management

### 2. API Client Tests
- GraphQL client operations
- REST client operations
- Rate limiting behavior

### 3. Service Layer Tests
- Product operations
- Order operations
- Customer operations
- Billing operations

### 4. Webhook Tests
- HMAC validation
- Event processing
- Handler registration

## Running Specific Test Categories

```bash
# Run only authentication tests
./gradlew test --tests "*auth*"

# Run only service tests
./gradlew test --tests "*service*"

# Run a specific test class
./gradlew test --tests "ProductServiceTest"

# Run tests with coverage report
./gradlew test jacocoTestReport
```

## Test Coverage

View test coverage reports after running tests:
- HTML Report: `build/reports/jacoco/test/html/index.html`
- XML Report: `build/reports/jacoco/test/jacocoTestReport.xml`

## Debugging Tests

### Enable Debug Logging

Add to `src/test/resources/logback-test.xml`:

```xml
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <logger name="com.shopify.sdk" level="DEBUG"/>
    <logger name="org.springframework.web.reactive" level="DEBUG"/>
    
    <root level="INFO">
        <appender-ref ref="STDOUT"/>
    </root>
</configuration>
```

### Running Tests in IDE

Most IDEs support running tests directly:
- IntelliJ IDEA: Right-click on test class/method and select "Run"
- Eclipse: Right-click on test class/method and select "Run As > JUnit Test"
- VS Code: Use the Test Explorer extension

## Best Practices

1. **Isolate Tests**: Each test should be independent and not rely on the state from other tests
2. **Use Test Fixtures**: Create reusable test data using the TestUtils class
3. **Mock External Dependencies**: Use Mockito for unit tests to avoid external API calls
4. **Clean Up Resources**: Always clean up test data in integration tests
5. **Descriptive Names**: Use @DisplayName to provide clear test descriptions
6. **Arrange-Act-Assert**: Follow the AAA pattern in test methods

## Troubleshooting

### Common Issues

1. **Rate Limiting**: If testing against real API, implement delays between tests
2. **Authentication Failures**: Ensure your access token has the required scopes
3. **Webhook Validation**: Check that your API secret matches between app and tests
4. **Timeout Issues**: Increase timeout values for integration tests

### Getting Help

- Check the [Shopify API documentation](https://shopify.dev/api)
- Review the [GraphQL Admin API reference](https://shopify.dev/api/admin-graphql)
- Submit issues to the project repository

## Example Test Suite

Here's a complete example of testing a feature end-to-end:

```java
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShopifyFeatureIntegrationTest {
    
    @Autowired
    private ShopifyApi shopifyApi;
    
    private static String createdProductId;
    
    @Test
    @Order(1)
    void createProduct() {
        // Create a product
    }
    
    @Test
    @Order(2)
    void updateProduct() {
        // Update the created product
    }
    
    @Test
    @Order(3)
    void queryProduct() {
        // Query the product
    }
    
    @Test
    @Order(4)
    void deleteProduct() {
        // Clean up by deleting the product
    }
}
```