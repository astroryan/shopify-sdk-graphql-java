package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import com.shopify.sdk.model.order.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


public enum DisputeStatus {
    ACCEPTED,
    CHARGE_REFUNDED,
    LOST,
    NEEDS_RESPONSE,
    UNDER_REVIEW,
    WON
}