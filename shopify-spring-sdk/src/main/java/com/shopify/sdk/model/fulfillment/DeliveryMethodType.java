package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import com.shopify.sdk.model.order.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public enum DeliveryMethodType {
    LOCAL,
    NONE,
    PICK_UP,
    RETAIL,
    SHIPPING
}
