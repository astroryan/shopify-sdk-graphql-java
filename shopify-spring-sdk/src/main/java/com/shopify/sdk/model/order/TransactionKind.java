package com.shopify.sdk.model.order;

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
