package com.shopify.sdk.model.product;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus {
    ACTIVE("ACTIVE"),
    ARCHIVED("ARCHIVED"),
    DRAFT("DRAFT");
    
    @JsonValue
    private final String value;
}