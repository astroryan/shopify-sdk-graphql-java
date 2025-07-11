package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


public enum ShopifyPaymentsVerificationDocumentType {
    BUSINESS_REGISTRATION,
    DRIVERS_LICENSE,
    GOVERNMENT_IDENTIFICATION,
    PASSPORT,
    VOIDED_CHECK
}