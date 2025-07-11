package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentScheduleInput {
    
    @JsonProperty("amount")
    private String amount;
    
    @JsonProperty("currency")
    private CurrencyCode currency;
    
    @JsonProperty("dueAt")
    private String dueAt;
    
    @JsonProperty("issuedAt")
    private String issuedAt;
}