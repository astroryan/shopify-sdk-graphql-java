# Changelog

All notable changes to the Shopify Spring SDK will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Advanced webhook processing with event validation
- Comprehensive monitoring and metrics integration
- Enhanced error handling with detailed exception types
- Thread-safe session management for multi-store support

### Changed
- Improved reactive programming patterns with WebFlux
- Enhanced authentication flow with JWT validation
- Better rate limiting with exponential backoff strategies

### Fixed
- Memory optimization for large bulk operations
- Connection pooling for better performance under load

## [1.1.0] - 2025-07-11

### Added
- **API Version Update**: Updated to Shopify Admin API version 2025-07
- **Comprehensive Testing**: Complete test coverage for all core services
  - Unit tests for service classes with MockWebServer
  - Integration tests with real Shopify store (optional)
  - Test fixtures and utilities for consistent testing
- **GitHub Packages Publishing**: Complete CI/CD pipeline for automated publishing
  - Automated release workflows with GitHub Actions
  - Manual workflow dispatch for on-demand publishing
  - Publication verification and testing procedures
- **Enhanced Documentation**: Comprehensive guides and examples
  - Detailed README with usage examples
  - Publishing guide with step-by-step instructions
  - API reference documentation
- **Developer Experience Improvements**:
  - Mock server support for testing without live stores
  - Test setup guides and configuration examples
  - Comprehensive error handling examples

### Changed
- **Test Structure**: Reorganized test directory for better maintainability
  - Separated unit tests from integration tests
  - Added test tags for selective test execution
  - Improved test naming conventions and organization
- **Error Handling**: Enhanced error handling across all service classes
  - More descriptive exception messages
  - Better error categorization (rate limits, validation, etc.)
  - Improved retry logic with exponential backoff
- **Documentation**: Updated all documentation to reflect API version 2025-07
  - Corrected API endpoints and GraphQL schema references
  - Updated examples with current best practices
  - Added security and performance guidelines

### Fixed
- **Package Structure**: Resolved package structure inconsistencies in test files
  - Aligned test package structure with main source
  - Fixed import statements and package declarations
- **Dependency Resolution**: Fixed GraphQL extended scalars dependency issues
  - Resolved version conflicts with transitive dependencies
  - Optimized dependency management with proper exclusions
  - Updated to compatible versions of all major dependencies

### Security
- **Secure Token Handling**: Improved security practices for access tokens
  - Enhanced OAuth flow with state validation
  - Secure storage recommendations for production deployments
  - JWT validation and verification improvements

## [1.0.0] - 2025-07-10

### Added
- **Initial Release**: Complete implementation of Shopify Spring SDK
- **Core E-Commerce Features**:
  - **Products & Collections**: Full product catalog management
    - Product CRUD operations with variants and images
    - Collection management and product associations
    - SEO optimization and metadata handling
  - **Orders & Fulfillment**: Complete order lifecycle management
    - Order creation, updates, and cancellation
    - Fulfillment tracking and shipping integration
    - Refund processing and financial management
  - **Customer Management**: Comprehensive customer data handling
    - Customer profiles and contact information
    - Customer segmentation and lifecycle tracking
    - Address management and preferences
  - **Inventory Management**: Multi-location inventory tracking
    - Real-time inventory updates and adjustments
    - Location-based inventory distribution
    - Stock level monitoring and alerts

- **Financial Operations**:
  - **Billing & Payments**: Advanced billing system integration
    - App subscription management and billing cycles
    - Usage-based charging and metered billing
    - Payment processing and transaction handling
  - **Discounts & Promotions**: Complete discount management
    - Price rules and automatic discounts
    - Coupon code generation and validation
    - Promotional campaign management

- **Store Operations**:
  - **Shipping & Fulfillment**: Comprehensive shipping solutions
    - Multiple shipping rate calculations
    - Fulfillment service integrations
    - Delivery tracking and notifications
  - **Online Store Management**: Complete storefront control
    - Theme management and customization
    - Page and blog content management
    - Navigation and SEO optimization

- **Advanced Features**:
  - **B2B & Markets**: International commerce support
    - Multi-currency and localization
    - B2B wholesale pricing and catalogs
    - Market-specific configurations
  - **Metafields & Metaobjects**: Flexible data structures
    - Custom field definitions and validation
    - Complex data relationships
    - Extended product and order attributes
  - **Webhooks & Events**: Real-time event processing
    - Comprehensive webhook subscription management
    - Event validation and processing
    - Retry mechanisms for failed deliveries
  - **Bulk Operations**: Large-scale data processing
    - Bulk data imports and exports
    - Background job processing
    - Progress tracking and error handling

- **Technical Infrastructure**:
  - **OAuth Authentication**: Complete OAuth 2.0 implementation
    - Secure token exchange and validation
    - Multi-store authentication support
    - Session management and persistence
  - **Automatic Retry Mechanisms**: Intelligent error handling
    - Rate limit detection and backoff strategies
    - Transient error recovery
    - Configurable retry policies
  - **Custom GraphQL Scalars**: Shopify-specific data types
    - Money scalar with currency support
    - DateTime handling with timezone awareness
    - ID type validation and conversion
  - **Spring Boot Auto-Configuration**: Seamless integration
    - Automatic bean configuration and wiring
    - Configuration property validation
    - Environment-specific configurations

- **Developer Experience**:
  - **Comprehensive Documentation**: Complete usage guides
    - Quick start tutorials and examples
    - API reference documentation
    - Best practices and security guidelines
  - **Type Safety**: Full type safety with Jackson serialization
    - Strongly typed models and responses
    - Validation annotations and constraints
    - Error-safe deserialization
  - **Testing Support**: Complete testing infrastructure
    - Mock server implementations
    - Test utilities and fixtures
    - Integration testing guidelines

### Technical Specifications
- **Java Compatibility**: Java 17+ support with modern language features
- **Spring Framework**: Spring Boot 3.2.0+ with reactive programming support
- **Build System**: Gradle 8.0+ with modern build practices
- **API Compatibility**: Shopify Admin GraphQL API 2024-10 (initial version)
- **Dependencies**:
  - Spring WebFlux for reactive programming
  - Jackson for JSON serialization/deserialization
  - OkHttp for HTTP client operations
  - JUnit 5 for testing framework
  - Mockito for mocking and testing
  - WireMock for HTTP service virtualization

### Quality Assurance
- **Test Coverage**: 80%+ code coverage requirement
- **Code Quality**: SonarQube integration with quality gates
- **Security**: OWASP dependency check and vulnerability scanning
- **Performance**: Load testing and performance benchmarking
- **Documentation**: Complete API documentation with examples

### Deployment & Distribution
- **GitHub Packages**: Maven repository hosted on GitHub Packages
- **Semantic Versioning**: Strict adherence to SemVer specifications
- **Release Process**: Automated CI/CD with GitHub Actions
- **Artifact Signing**: GPG signing for release artifacts
- **License**: MIT License for open-source compatibility

## [0.9.0-beta.2] - 2025-07-05

### Added
- Beta testing framework with mock Shopify responses
- Comprehensive integration tests for major workflows
- Performance benchmarking and optimization

### Fixed
- Memory leaks in WebFlux streaming operations
- Connection pooling issues under high load
- OAuth token refresh race conditions

## [0.9.0-beta.1] - 2025-07-01

### Added
- Beta release with core functionality
- Product and order management services
- Basic authentication and webhook support

### Known Issues
- Limited test coverage for edge cases
- Performance optimization needed for bulk operations
- Documentation requires completion

## [0.8.0-alpha.3] - 2025-06-25

### Added
- GraphQL client implementation with custom scalars
- Spring Boot auto-configuration
- Basic service layer architecture

### Changed
- Refactored client architecture for better separation of concerns
- Improved error handling and exception hierarchy

## [0.8.0-alpha.2] - 2025-06-20

### Added
- OAuth authentication flow implementation
- Basic product service with CRUD operations
- Project structure and build configuration

### Fixed
- Dependency version conflicts
- Package structure organization

## [0.8.0-alpha.1] - 2025-06-15

### Added
- Initial project setup and structure
- Basic Gradle configuration
- Core model classes for Shopify entities
- Initial GraphQL client implementation

---

## Development Guidelines

### Version Numbering
- **Major (X.0.0)**: Breaking API changes, architectural changes
- **Minor (X.Y.0)**: New features, backward-compatible additions
- **Patch (X.Y.Z)**: Bug fixes, documentation updates, minor improvements

### Release Process
1. **Development**: Feature branches merged to `develop`
2. **Testing**: Comprehensive testing in staging environment
3. **Release Candidate**: `main` branch with release candidate tag
4. **Production**: Tagged release with GitHub release notes
5. **Hotfixes**: Critical fixes follow patch version increments

### Changelog Guidelines
- **Added**: New features and functionality
- **Changed**: Changes in existing functionality
- **Deprecated**: Soon-to-be removed features (with migration path)
- **Removed**: Features removed in this version
- **Fixed**: Bug fixes and error corrections
- **Security**: Security vulnerability fixes and improvements

### Contributing
- All changes must include appropriate changelog entries
- Breaking changes require migration documentation
- New features require comprehensive testing and documentation
- Security fixes follow responsible disclosure practices

---

**Note**: This changelog is automatically updated during the release process. For detailed commit history, see the [Git log](https://github.com/astroryan/shopify-sdk-graphql-java/commits).