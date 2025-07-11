package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public enum FulfillmentOrderMerchantRequestKind {
    FULFILLMENT_REQUEST,
    CANCELLATION_REQUEST
}
