package com.shopify.sdk.model.bulk;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BulkOperationStatus {
    CANCELED("CANCELED"),
    CANCELING("CANCELING"),
    COMPLETED("COMPLETED"),
    CREATED("CREATED"),
    EXPIRED("EXPIRED"),
    FAILED("FAILED"),
    RUNNING("RUNNING");
    
    @JsonValue
    private final String value;
}