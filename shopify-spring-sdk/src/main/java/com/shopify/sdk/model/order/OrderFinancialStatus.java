package com.shopify.sdk.model.order;

/**
 * The financial status of an order.
 */
public enum OrderFinancialStatus {
    /**
     * The order is authorized.
     */
    AUTHORIZED,
    
    /**
     * The order is paid.
     */
    PAID,
    
    /**
     * The order is partially paid.
     */
    PARTIALLY_PAID,
    
    /**
     * The order is partially refunded.
     */
    PARTIALLY_REFUNDED,
    
    /**
     * The order is pending.
     */
    PENDING,
    
    /**
     * The order is refunded.
     */
    REFUNDED,
    
    /**
     * The order is unpaid.
     */
    UNPAID,
    
    /**
     * The order is voided.
     */
    VOIDED
}