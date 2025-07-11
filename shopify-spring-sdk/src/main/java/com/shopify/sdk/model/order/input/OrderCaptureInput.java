package com.shopify.sdk.model.order.input;

import com.shopify.sdk.model.common.CurrencyCode;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.math.BigDecimal;

/**
 * Input for capturing payment on an order.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCaptureInput {
    
    @GraphQLQuery(name = "id", description = "The order ID")
    private String id;
    
    @GraphQLQuery(name = "parentTransactionId", description = "The ID of the parent transaction to capture")
    private String parentTransactionId;
    
    @GraphQLQuery(name = "amount", description = "The amount to capture")
    private BigDecimal amount;
    
    @GraphQLQuery(name = "currency", description = "The currency for the capture amount")
    private CurrencyCode currency;
}