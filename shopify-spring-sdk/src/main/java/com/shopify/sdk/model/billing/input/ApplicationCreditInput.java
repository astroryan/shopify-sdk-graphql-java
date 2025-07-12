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
public class ApplicationCreditInput {
    
    private String description;
    private BigDecimal amount;
    
    @JsonProperty("currency_code")
    private String currencyCode;
    
    @JsonProperty("test")
    private Boolean test;
}