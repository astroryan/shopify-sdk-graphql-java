package com.shopify.sdk.model.metafield;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


public enum MetafieldDefinitionValidationStatus {
    ALL_VALID,
    IN_PROGRESS,
    SOME_INVALID
}