package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public enum FulfillmentHoldReason {
    AWAITING_PAYMENT,
    HIGH_RISK_OF_FRAUD,
    INCORRECT_ADDRESS,
    INVENTORY_OUT_OF_STOCK,
    OTHER,
    REGULATORY_CLEARANCE,
    UNFULFILLABLE_PRODUCT
}
