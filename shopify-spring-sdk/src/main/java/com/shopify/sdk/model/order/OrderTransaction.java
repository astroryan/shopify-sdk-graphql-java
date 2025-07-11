package com.shopify.sdk.model.order;

import com.shopify.sdk.model.common.*;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a transaction associated with an order.
 * A transaction represents money movement on an order, such as authorizations, captures, refunds, etc.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderTransaction implements Node {
    
    @GraphQLQuery(name = "id", description = "A globally-unique ID")
    private String id;
    
    @GraphQLQuery(name = "kind", description = "The kind of transaction")
    private OrderTransactionKind kind;
    
    @GraphQLQuery(name = "amountSet", description = "The amount and currency of the transaction in shop and presentment currencies")
    private MoneyBag amountSet;
    
    @GraphQLQuery(name = "amountRoundingSet", description = "The rounding adjustment applied on the cash amount in shop and presentment currencies")
    private MoneyBag amountRoundingSet;
    
    @GraphQLQuery(name = "accountNumber", description = "The masked account number associated with the payment method")
    private String accountNumber;
    
    @GraphQLQuery(name = "authorizationCode", description = "Authorization code associated with the transaction")
    private String authorizationCode;
    
    @GraphQLQuery(name = "authorizationExpiresAt", description = "The time when the authorization expires")
    private Instant authorizationExpiresAt;
    
    @GraphQLQuery(name = "createdAt", description = "Date and time when the transaction was created")
    private Instant createdAt;
    
    @GraphQLQuery(name = "processedAt", description = "Date and time when the transaction was processed")
    private Instant processedAt;
    
    @GraphQLQuery(name = "errorCode", description = "A standardized error code, independent of the payment provider")
    private OrderTransactionErrorCode errorCode;
    
    @GraphQLQuery(name = "fees", description = "The transaction fees charged on the order transaction")
    private List<TransactionFee> fees;
    
    @GraphQLQuery(name = "gateway", description = "The payment gateway used to process the transaction")
    private String gateway;
    
    @GraphQLQuery(name = "formattedGateway", description = "The human-readable payment gateway name used to process the transaction")
    private String formattedGateway;
    
    @GraphQLQuery(name = "manuallyCapturable", description = "Whether the transaction can be manually captured")
    private Boolean manuallyCapturable;
    
    @GraphQLQuery(name = "multiCapturable", description = "Whether the transaction can be captured multiple times")
    private Boolean multiCapturable;
    
    @GraphQLQuery(name = "maximumRefundableV2", description = "Specifies the available amount with currency to refund on the gateway")
    private MoneyV2 maximumRefundableV2;
    
    @GraphQLQuery(name = "order", description = "The associated order")
    private Order order;
    
    @GraphQLQuery(name = "parentTransaction", description = "The associated parent transaction")
    private OrderTransaction parentTransaction;
    
    @GraphQLQuery(name = "paymentDetails", description = "The payment details for the transaction")
    private PaymentDetails paymentDetails;
    
    @GraphQLQuery(name = "paymentIcon", description = "The payment icon to display for the transaction")
    private Image paymentIcon;
    
    @GraphQLQuery(name = "paymentId", description = "The payment ID associated with the transaction")
    private String paymentId;
    
    @GraphQLQuery(name = "receiptJson", description = "The transaction receipt from the payment gateway")
    private Map<String, Object> receiptJson;
    
    @GraphQLQuery(name = "settlementCurrency", description = "The settlement currency")
    private CurrencyCode settlementCurrency;
    
    @GraphQLQuery(name = "settlementCurrencyRate", description = "The rate used when converting the transaction amount to settlement currency")
    private BigDecimal settlementCurrencyRate;
    
    /**
     * The different kinds of order transactions
     */
    public enum OrderTransactionKind {
        /**
         * An amount reserved against the cardholder's funding source.
         * Money does not change hands until the authorization is captured.
         */
        AUTHORIZATION,
        
        /**
         * A transfer of the money that was reserved by an authorization
         */
        CAPTURE,
        
        /**
         * The money returned to the customer when they've paid too much during a cash transaction
         */
        CHANGE,
        
        /**
         * An authorization for a payment taken with an EMV credit card reader
         */
        EMV_AUTHORIZATION,
        
        /**
         * A partial or full return of captured funds to the cardholder.
         * A refund can happen only after a capture is processed.
         */
        REFUND,
        
        /**
         * An authorization and capture performed together in a single step
         */
        SALE,
        
        /**
         * A suggested refund transaction that can be used to create a refund
         */
        SUGGESTED_REFUND,
        
        /**
         * A cancelation of an authorization transaction
         */
        VOID
    }
    
    /**
     * Standardized error codes for order transactions
     */
    public enum OrderTransactionErrorCode {
        INCORRECT_NUMBER,
        INVALID_NUMBER,
        INVALID_EXPIRY_DATE,
        INVALID_CVC,
        EXPIRED_CARD,
        INCORRECT_CVC,
        INCORRECT_ZIP,
        INCORRECT_ADDRESS,
        CARD_DECLINED,
        PROCESSING_ERROR,
        CALL_ISSUER,
        PICK_UP_CARD,
        GENERIC_ERROR
    }
    
    /**
     * Transaction fee details
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionFee {
        @GraphQLQuery(name = "amount", description = "The amount of the fee")
        private MoneyV2 amount;
        
        @GraphQLQuery(name = "flatFee", description = "The flat fee for the transaction")
        private MoneyV2 flatFee;
        
        @GraphQLQuery(name = "flatFeeName", description = "The name of the flat fee")
        private String flatFeeName;
        
        @GraphQLQuery(name = "rate", description = "The percentage rate for the fee")
        private BigDecimal rate;
        
        @GraphQLQuery(name = "rateName", description = "The name of the rate")
        private String rateName;
        
        @GraphQLQuery(name = "taxAmount", description = "The tax amount for the fee")
        private MoneyV2 taxAmount;
        
        @GraphQLQuery(name = "type", description = "The type of fee")
        private String type;
    }
    
    /**
     * Payment details for a transaction
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentDetails {
        @GraphQLQuery(name = "creditCardCompany", description = "The name of the company that issued the credit card")
        private String creditCardCompany;
        
        @GraphQLQuery(name = "creditCardNumber", description = "The last four digits of the credit card")
        private String creditCardNumber;
        
        @GraphQLQuery(name = "avsResultCode", description = "The response code from the address verification system")
        private String avsResultCode;
        
        @GraphQLQuery(name = "cvvResultCode", description = "The response code from the credit card company")
        private String cvvResultCode;
        
        @GraphQLQuery(name = "creditCardName", description = "The name on the credit card")
        private String creditCardName;
        
        @GraphQLQuery(name = "creditCardWallet", description = "The wallet used for the payment")
        private String creditCardWallet;
    }
}