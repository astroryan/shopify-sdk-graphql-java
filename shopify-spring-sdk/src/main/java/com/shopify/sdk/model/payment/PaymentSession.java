package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.MoneyV2;
import com.shopify.sdk.model.common.Node;
import com.shopify.sdk.model.order.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a payment session for processing payments.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSession implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The state of the payment session.
     */
    @JsonProperty("state")
    private PaymentSessionState state;
    
    /**
     * Payment details for the session.
     */
    @JsonProperty("paymentDetails")
    private PaymentDetails paymentDetails;
    
    /**
     * The next action to be taken for the payment session.
     */
    @JsonProperty("nextAction")
    private PaymentSessionNextAction nextAction;
    
    /**
     * The amount for the payment session.
     */
    @JsonProperty("amount")
    private MoneyV2 amount;
    
    /**
     * The currency for the payment session.
     */
    @JsonProperty("currency")
    private String currency;
    
    /**
     * The customer ID associated with the payment session.
     */
    @JsonProperty("customerId")
    private String customerId;
    
    /**
     * The order associated with the payment session.
     */
    @JsonProperty("order")
    private Order order;
    
    /**
     * The source identifier for the payment session.
     */
    @JsonProperty("sourceIdentifier")
    private String sourceIdentifier;
    
    /**
     * Whether this is a test payment session.
     */
    @JsonProperty("test")
    private Boolean test;
    
    /**
     * The kind of payment session.
     */
    @JsonProperty("kind")
    private PaymentSessionKind kind;
    
    /**
     * Any error message associated with the payment session.
     */
    @JsonProperty("errorMessage")
    private String errorMessage;
    
    /**
     * The payment group for the session.
     */
    @JsonProperty("group")
    private String group;
    
    /**
     * The receipt information for the payment.
     */
    @JsonProperty("receipt")
    private String receipt;
}

/**
 * Payment details for a session.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class PaymentDetails {
    /**
     * The credit card brand.
     */
    @JsonProperty("creditCardBrand")
    private String creditCardBrand;
    
    /**
     * The last four digits of the credit card.
     */
    @JsonProperty("creditCardLastFourDigits")
    private String creditCardLastFourDigits;
    
    /**
     * The masked credit card number.
     */
    @JsonProperty("creditCardMaskedNumber")
    private String creditCardMaskedNumber;
    
    /**
     * The name on the credit card.
     */
    @JsonProperty("creditCardName")
    private String creditCardName;
    
    /**
     * The payment method name.
     */
    @JsonProperty("paymentMethodName")
    private String paymentMethodName;
}

/**
 * The next action for a payment session.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class PaymentSessionNextAction {
    /**
     * The action to be taken.
     */
    @JsonProperty("action")
    private PaymentSessionAction action;
    
    /**
     * The context for the action.
     */
    @JsonProperty("context")
    private PaymentSessionActionContext context;
}


/**
 * The context for a payment session action.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class PaymentSessionActionContext {
    /**
     * The redirect URL for the action.
     */
    @JsonProperty("redirectUrl")
    private String redirectUrl;
}