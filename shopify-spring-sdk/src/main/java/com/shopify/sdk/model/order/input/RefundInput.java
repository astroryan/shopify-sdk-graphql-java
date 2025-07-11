package com.shopify.sdk.model.order.input;

import com.shopify.sdk.model.common.CurrencyCode;
import com.shopify.sdk.model.order.RefundLineItemRestockType;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Input for creating a refund.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundInput {
    
    @GraphQLQuery(name = "orderId", description = "The ID of the order to refund")
    private String orderId;
    
    @GraphQLQuery(name = "note", description = "An optional note for the refund")
    private String note;
    
    @GraphQLQuery(name = "notify", description = "Whether to notify the customer")
    private Boolean notify;
    
    @GraphQLQuery(name = "shipping", description = "The shipping amount to refund")
    private ShippingRefundInput shipping;
    
    @GraphQLQuery(name = "refundLineItems", description = "The line items to refund")
    private List<RefundLineItemInput> refundLineItems;
    
    @GraphQLQuery(name = "transactions", description = "The transactions to process for the refund")
    private List<RefundTransactionInput> transactions;
    
    @GraphQLQuery(name = "currency", description = "The currency for the refund")
    private CurrencyCode currency;
    
    /**
     * Input for refunding shipping
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShippingRefundInput {
        @GraphQLQuery(name = "amount", description = "The amount to refund for shipping")
        private BigDecimal amount;
        
        @GraphQLQuery(name = "fullRefund", description = "Whether to fully refund the shipping")
        private Boolean fullRefund;
    }
    
    /**
     * Input for refunding a line item
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefundLineItemInput {
        @GraphQLQuery(name = "lineItemId", description = "The ID of the line item to refund")
        private String lineItemId;
        
        @GraphQLQuery(name = "quantity", description = "The quantity to refund")
        private Integer quantity;
        
        @GraphQLQuery(name = "restockType", description = "How to restock the refunded items")
        private RefundLineItemRestockType restockType;
        
        @GraphQLQuery(name = "locationId", description = "The ID of the location to restock items")
        private String locationId;
    }
    
    /**
     * Input for refund transaction
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefundTransactionInput {
        @GraphQLQuery(name = "orderId", description = "The ID of the order")
        private String orderId;
        
        @GraphQLQuery(name = "parentId", description = "The ID of the parent transaction")
        private String parentId;
        
        @GraphQLQuery(name = "amount", description = "The amount to refund")
        private BigDecimal amount;
        
        @GraphQLQuery(name = "gateway", description = "The payment gateway")
        private String gateway;
        
        @GraphQLQuery(name = "kind", description = "The kind of transaction")
        private String kind;
    }
}