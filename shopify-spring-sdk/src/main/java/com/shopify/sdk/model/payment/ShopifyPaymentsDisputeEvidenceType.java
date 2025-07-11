package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import com.shopify.sdk.model.order.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


public enum ShopifyPaymentsDisputeEvidenceType {
    CUSTOMER_COMMUNICATION_FILE,
    UNCATEGORIZED_FILE,
    SERVICE_DOCUMENTATION_FILE,
    SHIPPING_DOCUMENTATION_FILE,
    CANCELLATION_POLICY_FILE,
    REFUND_POLICY_FILE,
    CUSTOMER_SIGNATURE_FILE,
    BILLING_ADDRESS_FILE,
    RECEIPT_FILE
}