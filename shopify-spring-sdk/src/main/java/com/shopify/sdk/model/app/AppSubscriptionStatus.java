package com.shopify.sdk.model.app;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppSubscriptionStatus {
    PENDING("PENDING"),
    ACTIVE("ACTIVE"),
    DECLINED("DECLINED"),
    EXPIRED("EXPIRED"),
    FROZEN("FROZEN"),
    CANCELLED("CANCELLED");
    
    @JsonValue
    private final String value;
}