package com.shopify.sdk.model.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public enum ShopPolicyType {
    REFUND_POLICY,
    SHIPPING_POLICY,
    PRIVACY_POLICY,
    TERMS_OF_SERVICE,
    TERMS_OF_SALE,
    LEGAL_NOTICE,
    SUBSCRIPTION_POLICY
}
