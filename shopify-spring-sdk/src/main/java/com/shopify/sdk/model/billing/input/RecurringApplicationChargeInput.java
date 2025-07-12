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
public class RecurringApplicationChargeInput {
    
    private String name;
    private BigDecimal price;
    
    @JsonProperty("currency_code")
    private String currencyCode;
    
    @JsonProperty("trial_days")
    private Integer trialDays;
    
    @JsonProperty("capped_amount")
    private BigDecimal cappedAmount;
    
    @JsonProperty("terms")
    private String terms;
    
    @JsonProperty("return_url")
    private String returnUrl;
    
    @JsonProperty("test")
    private Boolean test;
}