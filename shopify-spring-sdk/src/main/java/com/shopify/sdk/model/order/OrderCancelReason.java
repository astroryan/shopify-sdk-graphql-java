package com.shopify.sdk.model.order;

/**
 * The reason for the order's cancellation.
 */
public enum OrderCancelReason {
    /**
     * The customer wanted to cancel the order.
     */
    CUSTOMER,
    
    /**
     * The order was fraudulent.
     */
    FRAUD,
    
    /**
     * Items in the order were not in inventory.
     */
    INVENTORY,
    
    /**
     * The order was cancelled for an unlisted reason.
     */
    OTHER
}