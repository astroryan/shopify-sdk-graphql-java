# Release Template for Shopify Spring SDK

## 🚀 Shopify Spring SDK v{VERSION}

### ✨ What's New

- 🆕 **New Features**: 
  - [List new features here]
  
- 🐛 **Bug Fixes**:
  - [List bug fixes here]
  
- 🔧 **Improvements**:
  - [List improvements here]

- ⚠️ **Breaking Changes** (if any):
  - [List breaking changes here]

### 📦 Installation

Add to your `build.gradle`:

```gradle
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/astroryan/shopify-sdk-java")
        credentials {
            username = project.findProperty("gpr.user") ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("gpr.key") ?: System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation 'com.shopify:shopify-spring-sdk:{VERSION}'
}
```

### 🔧 Quick Configuration

```yaml
shopify:
  # Private App Token (Recommended)
  admin-api-access-token: ${SHOPIFY_ACCESS_TOKEN}
  api-version: "2025-07"
  
  # Optional logging
  logging:
    level: INFO
    http-requests: false
```

### 📋 Version Compatibility

| Component | Version |
|-----------|---------|
| Java | 17+ |
| Spring Boot | 3.2.0+ |
| Shopify API | 2025-07 |
| Gradle | 8.0+ |

### 🆙 Migration Guide

#### From v{PREVIOUS_VERSION} to v{VERSION}

[Add migration instructions if needed]

### 🧪 Testing

This release has been tested with:
- ✅ Unit tests (100% passing)
- ✅ Integration tests (with real Shopify store)
- ✅ Performance benchmarks
- ✅ Security scans

### 📚 Documentation

- 📖 [README](https://github.com/astroryan/shopify-sdk-java/blob/main/README.md)
- 📘 [Publishing Guide](https://github.com/astroryan/shopify-sdk-java/blob/main/PUBLISHING.md)
- 📝 [Full Changelog](https://github.com/astroryan/shopify-sdk-java/blob/main/CHANGELOG.md)
- 🔗 [API Documentation](https://shopify.dev/docs/api/admin-graphql)

### 🙏 Contributors

Special thanks to all contributors who made this release possible!

### 📞 Support

- 🐛 [Report Issues](https://github.com/astroryan/shopify-sdk-java/issues)
- 💬 [Discussions](https://github.com/astroryan/shopify-sdk-java/discussions)
- 📧 [Email Support](mailto:support@example.com)

---

🤖 *This release was automatically created by GitHub Actions*