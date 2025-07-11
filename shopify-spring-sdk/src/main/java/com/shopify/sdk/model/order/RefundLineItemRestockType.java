package com.shopify.sdk.model.order;

/**
 * The type of restock performed when creating a refund.
 */
public enum RefundLineItemRestockType {
    /**
     * The refund line item was returned to the merchant and restocked
     */
    RETURN,
    
    /**
     * The refund line item was canceled and restocked
     */
    CANCEL,
    
    /**
     * The refund line item was restocked, without specifically being identified as a return or cancel
     */
    LEGACY_RESTOCK,
    
    /**
     * The refund line item was not restocked
     */
    NO_RESTOCK
}