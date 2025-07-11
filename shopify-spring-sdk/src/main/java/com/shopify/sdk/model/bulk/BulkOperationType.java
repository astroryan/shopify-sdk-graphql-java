package com.shopify.sdk.model.bulk;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BulkOperationType {
    QUERY("QUERY"),
    MUTATION("MUTATION");
    
    @JsonValue
    private final String value;
}