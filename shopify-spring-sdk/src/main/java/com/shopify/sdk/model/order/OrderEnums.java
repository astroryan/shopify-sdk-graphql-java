package com.shopify.sdk.model.order;

public enum OrderCancelReason {
    CUSTOMER,
    DECLINED,
    FRAUD,
    INVENTORY,
    OTHER,
    STAFF
}

public enum OrderDisplayFinancialStatus {
    AUTHORIZED,
    EXPIRED,
    PAID,
    PARTIALLY_PAID,
    PARTIALLY_REFUNDED,
    PENDING,
    REFUNDED,
    VOIDED
}

public enum OrderDisplayFulfillmentStatus {
    FULFILLED,
    IN_PROGRESS,
    ON_HOLD,
    OPEN,
    PARTIALLY_FULFILLED,
    PENDING_FULFILLMENT,
    RESTOCKED,
    SCHEDULED,
    UNFULFILLED
}

public enum OrderReturnStatus {
    CANCELLED,
    CLOSED,
    DECLINED,
    IN_PROGRESS,
    INSPECTION_COMPLETE,
    NO_RETURN,
    OPEN,
    REQUESTED,
    RETURN_FAILED,
    RETURN_IN_PROGRESS,
    RETURNED
}

public enum OrderRiskLevel {
    HIGH,
    LOW,
    MEDIUM
}

public enum FulfillmentStatus {
    CANCELLED,
    ERROR,
    FAILURE,
    IN_TRANSIT,
    OPEN,
    PENDING,
    SUCCESS
}

public enum FulfillmentDisplayStatus {
    ATTEMPTED_DELIVERY,
    CANCELED,
    CONFIRMED,
    DELIVERED,
    FAILURE,
    FULFILLED,
    IN_TRANSIT,
    LABEL_PRINTED,
    LABEL_PURCHASED,
    LABEL_VOIDED,
    MARKED_AS_FULFILLED,
    NOT_DELIVERED,
    OUT_FOR_DELIVERY,
    PICKED_UP,
    READY_FOR_PICKUP,
    SUBMITTED
}

public enum RefundLineItemRestockType {
    CANCEL,
    LEGACY_RESTOCK,
    NO_RESTOCK,
    RETURN
}

public enum TransactionKind {
    AUTHORIZATION,
    CAPTURE,
    CHANGE,
    EMV_AUTHORIZATION,
    REFUND,
    SALE,
    SUGGESTED_REFUND,
    VOID
}

public enum TransactionStatus {
    AWAITING_RESPONSE,
    ERROR,
    FAILURE,
    PENDING,
    SUCCESS,
    UNKNOWN
}