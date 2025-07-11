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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSessionCompleteInput {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("pendingReceipt")
    private String pendingReceipt;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSessionRejectInput {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("reason")
    private PaymentSessionRejectionReasonInput reason;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSessionRejectionReasonInput {
    
    @JsonProperty("code")
    private PaymentSessionRejectionReasonCode code;
    
    @JsonProperty("merchantMessage")
    private String merchantMessage;
}

public enum PaymentSessionRejectionReasonCode {
    BUYER_CANCELED,
    BUYER_REFUSED,
    CALL_ISSUER,
    CARD_DECLINED,
    CONFIG_ERROR,
    DO_NOT_HONOR,
    EXPIRED_CARD,
    FRAUDULENT,
    GENERIC_DECLINE,
    INCORRECT_ADDRESS,
    INCORRECT_CVC,
    INCORRECT_NUMBER,
    INCORRECT_PIN,
    INCORRECT_ZIP,
    INSUFFICIENT_FUNDS,
    INVALID_ACCOUNT,
    INVALID_AMOUNT,
    INVALID_CARD,
    ISSUER_UNAVAILABLE,
    LOST_CARD,
    PICK_UP_CARD,
    PROCESSING_ERROR,
    RESTRICTED_CARD,
    RISKY,
    TEST_MODE_LIVE_CARD,
    UNSUPPORTED_FEATURE
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSessionResolveInput {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("authorizationExpiresAt")
    private String authorizationExpiresAt;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSessionRetryInput {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("idempotencyKey")
    private String idempotencyKey;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMandateInput {
    
    @JsonProperty("paymentInstrumentId")
    private String paymentInstrumentId;
}

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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTermsInput {
    
    @JsonProperty("paymentTermsTemplateId")
    private String paymentTermsTemplateId;
    
    @JsonProperty("paymentSchedules")
    private List<PaymentScheduleInput> paymentSchedules;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTermsCreateInput {
    
    @JsonProperty("referenceId")
    private String referenceId;
    
    @JsonProperty("paymentTerms")
    private PaymentTermsInput paymentTerms;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTermsDeleteInput {
    
    @JsonProperty("paymentTermsId")
    private String paymentTermsId;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTermsUpdateInput {
    
    @JsonProperty("paymentTermsId")
    private String paymentTermsId;
    
    @JsonProperty("paymentTerms")
    private PaymentTermsInput paymentTerms;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyPaymentsBankAccountInput {
    
    @JsonProperty("accountNumber")
    private String accountNumber;
    
    @JsonProperty("accountType")
    private ShopifyPaymentsBankAccountType accountType;
    
    @JsonProperty("bankName")
    private String bankName;
    
    @JsonProperty("currency")
    private CurrencyCode currency;
    
    @JsonProperty("routingNumber")
    private String routingNumber;
    
    @JsonProperty("transitNumber")
    private String transitNumber;
    
    @JsonProperty("institutionNumber")
    private String institutionNumber;
}

public enum ShopifyPaymentsBankAccountType {
    CHECKING,
    SAVINGS
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyPaymentsDefaultChargeStatementDescriptorInput {
    
    @JsonProperty("value")
    private String value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyPaymentsFraudSettingsInput {
    
    @JsonProperty("declineChargeOnAvsFailure")
    private Boolean declineChargeOnAvsFailure;
    
    @JsonProperty("declineChargeOnCvcFailure")
    private Boolean declineChargeOnCvcFailure;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyPaymentsNotificationSettingsInput {
    
    @JsonProperty("payouts")
    private Boolean payouts;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyPaymentsRefundInput {
    
    @JsonProperty("orderId")
    private String orderId;
    
    @JsonProperty("amount")
    private String amount;
    
    @JsonProperty("currency")
    private CurrencyCode currency;
    
    @JsonProperty("reason")
    private RefundReason reason;
    
    @JsonProperty("parentRefundId")
    private String parentRefundId;
}

public enum RefundReason {
    CUSTOMER_REQUEST,
    DUPLICATE,
    FRAUDULENT,
    OTHER
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyPaymentsVerificationInput {
    
    @JsonProperty("subject")
    private ShopifyPaymentsVerificationSubjectInput subject;
    
    @JsonProperty("document")
    private ShopifyPaymentsVerificationDocumentInput document;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyPaymentsVerificationSubjectInput {
    
    @JsonProperty("familyName")
    private String familyName;
    
    @JsonProperty("givenName")
    private String givenName;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyPaymentsVerificationDocumentInput {
    
    @JsonProperty("back")
    private String back;
    
    @JsonProperty("front")
    private String front;
    
    @JsonProperty("type")
    private ShopifyPaymentsVerificationDocumentType type;
}