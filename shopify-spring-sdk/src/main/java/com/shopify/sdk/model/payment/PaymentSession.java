package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import com.shopify.sdk.model.order.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSession {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("state")
    private PaymentSessionState state;
    
    @JsonProperty("paymentDetails")
    private PaymentDetails paymentDetails;
    
    @JsonProperty("nextAction")
    private PaymentSessionNextAction nextAction;
    
    @JsonProperty("amount")
    private MoneyV2 amount;
    
    @JsonProperty("currency")
    private CurrencyCode currency;
    
    @JsonProperty("customerId")
    private ID customerId;
    
    @JsonProperty("order")
    private Order order;
    
    @JsonProperty("sourceIdentifier")
    private String sourceIdentifier;
    
    @JsonProperty("test")
    private Boolean test;
    
    @JsonProperty("kind")
    private PaymentKind kind;
    
    @JsonProperty("errorMessage")
    private String errorMessage;
    
    @JsonProperty("group")
    private String group;
    
    @JsonProperty("receipt")
    private String receipt;
}
