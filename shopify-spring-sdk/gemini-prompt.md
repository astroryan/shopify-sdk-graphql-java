# Shopify Model Classes Creation Task

## Context
We're working on a Shopify Spring SDK and have many missing model classes causing compilation errors. We need to create these classes based on the Shopify Admin GraphQL API documentation.

## Missing Classes List

### Event Package (com.shopify.sdk.model.event)
1. **AppCredit** - Referenced by AppCreditEdge
2. **Collection** - Referenced by CollectionEdge (event context)
3. **Product** - Referenced by ProductEdge (event context)
4. **ResourcePublicationV2** - Referenced by ResourcePublicationV2Edge
5. **AppRevenueAttributionRecord** - Referenced by AppRevenueAttributionRecordEdge
6. **AppPurchaseOneTime** - Referenced by AppPurchaseOneTimeEdge

### Store Package (com.shopify.sdk.model.store)
1. **PrivateMetafield** - Referenced by PrivateMetafieldEdge
2. **MarketRegion** - Referenced by MarketRegionEdge
3. **FulfillmentService** - Referenced by FulfillmentServiceEdge (store context)

### Order Package (com.shopify.sdk.model.order)
1. **OrderTransaction** - Used in OrderService.capturePayment()
2. **Fulfillment** - Used in OrderService for fulfillment operations
3. **Refund** - Used in OrderService.createRefund()
4. **TransactionKind** - Enum for transaction types
5. **RefundLineItemRestockType** - Enum for refund restock types

### Additional Response/Input Types Needed
1. **FulfillmentCreateData** - Response wrapper
2. **FulfillmentCreateResponse** - Response type
3. **FulfillmentCancelData** - Response wrapper
4. **FulfillmentCancelResponse** - Response type
5. **RefundCreateData** - Response wrapper
6. **RefundCreateResponse** - Response type
7. **OrderCaptureInput** - Input type
8. **FulfillmentInput** - Input type
9. **RefundInput** - Input type

### Retail Package (com.shopify.sdk.model.retail) - if referenced
1. **Device**
2. **DeviceCredential**
3. **StaffMember**
4. **PosExternalDevice**
5. **RetailSession**
6. **CashTrackingSession**
7. **CashAdjustment**

## Requirements for Each Class

1. **Package Structure**: Place in correct package based on domain
2. **Annotations**: Use Lombok (@Data, @Builder, @NoArgsConstructor, @AllArgsConstructor)
3. **GraphQL Annotations**: Use @GraphQLQuery for fields
4. **Implements Node**: If it has an ID field
5. **Timestamps**: Include createdAt, updatedAt fields
6. **Documentation**: Add JavaDoc based on Shopify API docs

## Reference Documentation
- Shopify Admin GraphQL API: https://shopify.dev/docs/api/admin-graphql

## Example Model Structure
```java
package com.shopify.sdk.model.event;

import com.shopify.sdk.model.common.Node;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;
import java.time.Instant;

/**
 * Represents [description from Shopify docs]
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExampleModel implements Node {
    @GraphQLQuery(name = "id", description = "Globally unique identifier")
    private String id;
    
    // Other fields based on API documentation
    
    @GraphQLQuery(name = "createdAt", description = "When the record was created")
    private Instant createdAt;
    
    @GraphQLQuery(name = "updatedAt", description = "When the record was last updated")
    private Instant updatedAt;
}
```

Please help create each missing class systematically.