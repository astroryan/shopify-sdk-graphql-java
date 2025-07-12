package com.shopify.sdk.model.order;

/**
 * The fulfillment status for an order.
 */
public enum OrderFulfillmentStatus {
    /**
     * Displayed as 'Fulfilled'. Every line item in the order has been fulfilled.
     */
    FULFILLED,
    
    /**
     * Displayed as 'In transit'. Some line items in the order have been fulfilled.
     */
    IN_TRANSIT,
    
    /**
     * Displayed as 'On hold'. A request for manual action such as calling the customer has been sent to the merchant.
     */
    ON_HOLD,
    
    /**
     * Displayed as 'Open'. None of the line items in the order have been fulfilled.
     */
    OPEN,
    
    /**
     * Displayed as 'Partially fulfilled'. Some line items in the order have been fulfilled.
     */
    PARTIALLY_FULFILLED,
    
    /**
     * Displayed as 'Pending fulfillment'. A fulfillment service is processing the order.
     */
    PENDING_FULFILLMENT,
    
    /**
     * Displayed as 'Restocked'. Every line item in the order has been restocked.
     */
    RESTOCKED,
    
    /**
     * Displayed as 'Scheduled'. All line items in the order are scheduled for fulfillment at later time.
     */
    SCHEDULED,
    
    /**
     * Displayed as 'Unfulfilled'. None of the line items in the order have been fulfilled.
     */
    UNFULFILLED
}