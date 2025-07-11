package com.shopify.sdk.model.bulk;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BulkOperationErrorCode {
    ACCESS_DENIED("ACCESS_DENIED"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
    TIMEOUT("TIMEOUT");
    
    @JsonValue
    private final String value;
}