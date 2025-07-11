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

public enum PaymentSessionState {
    RESOLVED,
    REJECTED,
    PENDING,
    UNKNOWN
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class PaymentDetails {
    
    @JsonProperty("creditCardBrand")
    private String creditCardBrand;
    
    @JsonProperty("creditCardLastFourDigits")
    private String creditCardLastFourDigits;
    
    @JsonProperty("creditCardMaskedNumber")
    private String creditCardMaskedNumber;
    
    @JsonProperty("creditCardName")
    private String creditCardName;
    
    @JsonProperty("paymentMethodName")
    private String paymentMethodName;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class PaymentSessionNextAction {
    
    @JsonProperty("action")
    private PaymentSessionActionType action;
    
    @JsonProperty("context")
    private PaymentSessionActionContext context;
}

public enum PaymentSessionActionType {
    REDIRECT,
    RETRY,
    UNKNOWN
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class PaymentSessionActionContext {
    
    @JsonProperty("redirectUrl")
    private String redirectUrl;
}

public enum PaymentKind {
    AUTHORIZATION,
    CAPTURE,
    REFUND,
    SALE,
    VOID,
    UNKNOWN
}