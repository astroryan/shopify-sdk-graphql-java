# Shopify Model Classes Creation - Collaboration with Gemini

## Context
We're building a Shopify Spring SDK and need to create missing model classes. We have about 100 compilation errors due to missing classes. I'll work with you to create these classes comprehensively, referencing the Shopify Admin GraphQL API documentation.

## Pattern to Follow
All Connection classes should follow this pattern:
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SomeConnection {
    private List<SomeEdge> edges;
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SomeEdge {
    private String cursor;
    private SomeNode node;
}
```

## Missing Classes to Create

### 1. Retail Package Connection Classes
Location: `src/main/java/com/shopify/sdk/model/retail/`

#### DeviceCredentialConnection & DeviceCredentialEdge
- DeviceCredentialConnection.java
- DeviceCredentialEdge.java

#### StaffMemberConnection & StaffMemberEdge
- StaffMemberConnection.java
- StaffMemberEdge.java

#### PosExternalDeviceConnection & PosExternalDeviceEdge
- PosExternalDeviceConnection.java
- PosExternalDeviceEdge.java

#### RetailSessionConnection & RetailSessionEdge
- RetailSessionConnection.java
- RetailSessionEdge.java

#### CashTrackingSessionConnection & CashTrackingSessionEdge
- CashTrackingSessionConnection.java
- CashTrackingSessionEdge.java

### 2. Marketplace Package Classes
Location: `src/main/java/com/shopify/sdk/model/marketplace/`

#### Core Models
- Channel.java
- Market.java
- Catalog.java
- PriceList.java

#### Connection Classes
- MarketConnection.java & MarketEdge.java
- CatalogConnection.java & CatalogEdge.java
- ChannelConnection.java & ChannelEdge.java
- PriceListConnection.java & PriceListEdge.java

#### Input Classes
Location: `src/main/java/com/shopify/sdk/model/marketplace/input/`
- MarketCreateInput.java
- MarketUpdateInput.java
- CatalogCreateInput.java
- CatalogUpdateInput.java
- PriceListCreateInput.java
- PriceListUpdateInput.java

### 3. Bulk Operation Package
Location: `src/main/java/com/shopify/sdk/model/bulk/`
- BulkOperation.java (main model with all fields from API)

### 4. Store Properties Package
Location: `src/main/java/com/shopify/sdk/model/store/`

#### Core Models
- Shop.java
- Domain.java
- StorefrontAccessToken.java
- ShopLocalization.java
- ShopLocale.java
- ShopPolicy.java

#### Connection Classes
- StorefrontAccessTokenConnection.java & StorefrontAccessTokenEdge.java
- ShopLocaleConnection.java & ShopLocaleEdge.java
- DomainConnection.java & DomainEdge.java

#### Input Classes
Location: `src/main/java/com/shopify/sdk/model/store/input/`
- StorePropertiesInput.java
- ShopPolicyInput.java
- ShopPolicyUpdateInput.java
- StorefrontAccessTokenInput.java
- StorefrontAccessTokenDeleteInput.java

## Requirements for Each Class
1. Use Lombok annotations: @Data, @Builder, @NoArgsConstructor, @AllArgsConstructor
2. Add proper JavaDoc comments explaining the purpose
3. Include all fields from Shopify Admin GraphQL API
4. Use appropriate Java types (String for ID, LocalDateTime for timestamps, BigDecimal for money amounts)
5. For enums, create separate enum files if they don't exist
6. Import necessary classes from other packages (e.g., PageInfo, Money, etc.)

## API Reference
Please reference: https://shopify.dev/docs/api/admin-graphql

Let's start with the Retail Package Connection Classes. Can you help me create these classes based on the Shopify Admin GraphQL API documentation?