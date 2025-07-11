package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import com.shopify.sdk.model.order.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


public enum ShopifyPaymentsDisputeReason {
    BANK_CANNOT_PROCESS,
    CREDIT_NOT_PROCESSED,
    CUSTOMER_INITIATED,
    DEBIT_NOT_AUTHORIZED,
    DUPLICATE,
    FRAUDULENT,
    GENERAL,
    INCORRECT_ACCOUNT_DETAILS,
    INSUFFICIENT_FUNDS,
    PRODUCT_NOT_RECEIVED,
    PRODUCT_UNACCEPTABLE,
    SUBSCRIPTION_CANCELLED,
    UNRECOGNIZED
}