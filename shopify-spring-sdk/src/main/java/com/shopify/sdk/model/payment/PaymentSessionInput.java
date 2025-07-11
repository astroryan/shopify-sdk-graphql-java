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
public class PaymentSessionInput {
    
    @JsonProperty("amount")
    private String amount;
    
    @JsonProperty("currency")
    private CurrencyCode currency;
    
    @JsonProperty("customerId")
    private String customerId;
    
    @JsonProperty("orderId")
    private String orderId;
    
    @JsonProperty("paymentMethodId")
    private String paymentMethodId;
    
    @JsonProperty("test")
    private Boolean test;
    
    @JsonProperty("kind")
    private PaymentKind kind;
    
    @JsonProperty("sourceIdentifier")
    private String sourceIdentifier;
    
    @JsonProperty("group")
    private String group;
}