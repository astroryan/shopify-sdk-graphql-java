package com.shopify.sdk.model.billing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationCredit {
    
    private String id;
    private String description;
    private BigDecimal amount;
    
    @JsonProperty("currency_code")
    private String currencyCode;
    
    @JsonProperty("created_at")
    private Instant createdAt;
    
    @JsonProperty("test")
    private Boolean test;
    
    public boolean isTest() {
        return test != null && test;
    }
    
    public boolean isValid() {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }
}