# Changelog

All notable changes to the Shopify Spring SDK will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.1.0] - 2025-01-11

### Added
- Updated to Shopify API version 2025-07
- Comprehensive test coverage for core services
- Mock server and test fixtures for SDK testing without Shopify store
- GitHub Packages publishing configuration
- CI/CD workflows for automated testing and publishing

### Changed
- Improved test directory structure for consistency
- Enhanced error handling in all service classes
- Updated all documentation to reflect API version 2025-07

### Fixed
- Package structure inconsistencies in test files
- GraphQL extended scalars dependency resolution

## [1.0.0] - 2025-01-10

### Added
- Initial release of Shopify Spring SDK
- Complete implementation of Shopify Admin GraphQL API
- Support for all major Shopify modules:
  - Products & Collections
  - Orders & Fulfillment
  - Customers
  - Inventory Management
  - Billing & Payments
  - Discounts
  - Shipping
  - Online Store
  - B2B & Markets
  - Metafields & Metaobjects
  - Webhooks
  - Bulk Operations
- OAuth authentication flow
- Automatic retry mechanism for rate limits
- Custom GraphQL scalar types
- Spring Boot auto-configuration
- Comprehensive documentation and examples