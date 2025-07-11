package com.shopify.sdk.model.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public enum DigitalWallet {
    APPLE_PAY,
    ANDROID_PAY,
    GOOGLE_PAY,
    SHOPIFY_PAY
}
