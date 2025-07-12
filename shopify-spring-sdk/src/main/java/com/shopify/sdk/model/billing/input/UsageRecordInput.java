package com.shopify.sdk.model.billing.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsageRecordInput {
    
    private String description;
    private BigDecimal price;
    
    @JsonProperty("currency_code")
    private String currencyCode;
}