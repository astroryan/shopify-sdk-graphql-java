# Shopify SDK GraphQL Java

A comprehensive Java Spring SDK for the Shopify Admin GraphQL API, providing type-safe access to all Shopify Admin API features.

[![CI](https://github.com/astroryan/shopify-sdk-graphql-java/actions/workflows/ci.yml/badge.svg)](https://github.com/astroryan/shopify-sdk-graphql-java/actions/workflows/ci.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## 📦 Installation

This SDK is published to GitHub Packages. See the [detailed installation guide](shopify-spring-sdk/README.md#installation) for setup instructions.

```gradle
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/astroryan/shopify-sdk-graphql-java")
        credentials {
            username = System.getenv("GITHUB_ACTOR")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation 'com.shopify:shopify-spring-sdk:1.1.0'
}
```

## 🚀 Features

- **Complete API Coverage**: Implements all Shopify Admin GraphQL API modules
- **Type-Safe**: Fully typed models and responses with Jackson serialization
- **Spring Boot Integration**: Seamless integration with Spring Boot applications
- **Automatic Retry**: Built-in retry mechanism for rate limits
- **Well-Tested**: Comprehensive unit and integration tests
- **Mock Server**: Test your integration without a Shopify store

## 📖 Documentation

- [SDK Documentation](shopify-spring-sdk/README.md) - Complete usage guide and examples
- [Publishing Guide](PUBLISHING.md) - How to publish new versions
- [Changelog](CHANGELOG.md) - Version history and changes

## 🛠️ Quick Start

```java
// Create auth context
ShopifyAuthContext authContext = new ShopifyAuthContext(
    "your-store.myshopify.com",
    "your-access-token"
);

// Use services
@Autowired
private ProductService productService;

// Create a product
ProductInput input = ProductInput.builder()
    .title("Amazing Product")
    .description("This is an amazing product!")
    .build();

Product product = productService.createProduct(authContext, input);
```

## 📊 Supported Modules

- ✅ Products & Collections
- ✅ Orders & Fulfillment
- ✅ Customers
- ✅ Inventory Management
- ✅ Billing & Payments
- ✅ Discounts
- ✅ Shipping
- ✅ Webhooks
- ✅ Bulk Operations
- ✅ And many more...

See the [full list of supported modules](shopify-spring-sdk/README.md#supported-modules).

## 🧪 Testing

The SDK includes comprehensive test utilities:

```bash
# Run unit tests
cd shopify-spring-sdk
./gradlew test

# Run with coverage
./gradlew test jacocoTestReport
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🔗 Links

- [Shopify Admin API Documentation](https://shopify.dev/docs/api/admin-graphql)
- [GitHub Repository](https://github.com/astroryan/shopify-sdk-graphql-java)
- [Published Package](https://github.com/astroryan/shopify-sdk-graphql-java/packages)