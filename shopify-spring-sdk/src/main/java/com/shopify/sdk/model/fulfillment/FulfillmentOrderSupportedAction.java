package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import com.shopify.sdk.model.order.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public enum FulfillmentOrderSupportedAction {
    CANCEL_FULFILLMENT_ORDER,
    CREATE_FULFILLMENT,
    EXTERNAL_REQUEST,
    FULFILLMENT_ORDER_CANCEL,
    FULFILLMENT_ORDER_CLOSE,
    FULFILLMENT_ORDER_HOLD,
    FULFILLMENT_ORDER_MERGE,
    FULFILLMENT_ORDER_MOVE,
    FULFILLMENT_ORDER_OPEN,
    FULFILLMENT_ORDER_RELEASE_HOLD,
    FULFILLMENT_ORDER_RESCHEDULE,
    FULFILLMENT_ORDER_SPLIT,
    FULFILLMENT_ORDER_SUBMIT_CANCELLATION_REQUEST,
    FULFILLMENT_ORDER_SUBMIT_FULFILLMENT_REQUEST,
    MARK_AS_OPEN,
    REQUEST_CANCELLATION,
    REQUEST_FULFILLMENT
}
