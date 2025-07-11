package com.shopify.sdk.model.customer;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomerState {
    ENABLED("ENABLED"),
    DISABLED("DISABLED"),
    INVITED("INVITED"),
    DECLINED("DECLINED");
    
    @JsonValue
    private final String value;
}