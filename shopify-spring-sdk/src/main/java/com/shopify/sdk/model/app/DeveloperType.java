package com.shopify.sdk.model.app;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeveloperType {
    SHOPIFY("SHOPIFY"),
    PARTNER("PARTNER"),
    MERCHANT("MERCHANT"),
    UNKNOWN("UNKNOWN");
    
    @JsonValue
    private final String value;
}