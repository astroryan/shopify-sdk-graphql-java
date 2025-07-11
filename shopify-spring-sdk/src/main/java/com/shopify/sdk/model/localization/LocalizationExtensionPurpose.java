package com.shopify.sdk.model.localization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public enum LocalizationExtensionPurpose {
    SHIPPING_FILTER_ERROR,
    SHIPPING_FILTER_WARNING,
    SHIPPING_FILTER_CAPTION,
    SHIPPING_RATE_ERROR,
    SHIPPING_RATE_TITLE,
    SHIPPING_RATE_DESCRIPTION,
    ORDER_DISCOUNT_MESSAGE,
    PRODUCT_DISCOUNT_MESSAGE,
    SHIPPING_DISCOUNT_MESSAGE,
    PAYMENT_GATEWAY_NAME,
    PAYMENT_METHOD_NAME
}
