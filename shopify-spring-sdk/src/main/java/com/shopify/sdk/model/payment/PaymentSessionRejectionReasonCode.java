package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


public enum PaymentSessionRejectionReasonCode {
    BUYER_CANCELED,
    BUYER_REFUSED,
    CALL_ISSUER,
    CARD_DECLINED,
    CONFIG_ERROR,
    DO_NOT_HONOR,
    EXPIRED_CARD,
    FRAUDULENT,
    GENERIC_DECLINE,
    INCORRECT_ADDRESS,
    INCORRECT_CVC,
    INCORRECT_NUMBER,
    INCORRECT_PIN,
    INCORRECT_ZIP,
    INSUFFICIENT_FUNDS,
    INVALID_ACCOUNT,
    INVALID_AMOUNT,
    INVALID_CARD,
    ISSUER_UNAVAILABLE,
    LOST_CARD,
    PICK_UP_CARD,
    PROCESSING_ERROR,
    RESTRICTED_CARD,
    RISKY,
    TEST_MODE_LIVE_CARD,
    UNSUPPORTED_FEATURE
}