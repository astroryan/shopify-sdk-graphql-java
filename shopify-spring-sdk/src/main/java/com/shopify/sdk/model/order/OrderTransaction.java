package com.shopify.sdk.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderTransaction {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("accountNumber")
    private String accountNumber;
    
    @JsonProperty("amountSet")
    private MoneyBag amountSet;
    
    @JsonProperty("authorizationCode")
    private String authorizationCode;
    
    @JsonProperty("authorizationExpiresAt")
    private DateTime authorizationExpiresAt;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("errorCode")
    private String errorCode;
    
    @JsonProperty("fees")
    private List<TransactionFee> fees;
    
    @JsonProperty("formattedGateway")
    private String formattedGateway;
    
    @JsonProperty("gateway")
    private String gateway;
    
    @JsonProperty("kind")
    private TransactionKind kind;
    
    @JsonProperty("manuallyCapturable")
    private Boolean manuallyCapturable;
    
    @JsonProperty("maximumRefundableV2")
    private MoneyV2 maximumRefundableV2;
    
    @JsonProperty("order")
    private Order order;
    
    @JsonProperty("parentTransaction")
    private OrderTransaction parentTransaction;
    
    @JsonProperty("paymentDetails")
    private PaymentDetails paymentDetails;
    
    @JsonProperty("paymentIcon")
    private Image paymentIcon;
    
    @JsonProperty("paymentId")
    private String paymentId;
    
    @JsonProperty("paymentMethod")
    private String paymentMethod;
    
    @JsonProperty("processedAt")
    private DateTime processedAt;
    
    @JsonProperty("receipt")
    private String receipt;
    
    @JsonProperty("refundedAmountSet")
    private MoneyBag refundedAmountSet;
    
    @JsonProperty("settlementCurrency")
    private CurrencyCode settlementCurrency;
    
    @JsonProperty("settlementCurrencyRate")
    private Double settlementCurrencyRate;
    
    @JsonProperty("shopifyPaymentsSet")
    private ShopifyPaymentsTransactionSet shopifyPaymentsSet;
    
    @JsonProperty("status")
    private TransactionStatus status;
    
    @JsonProperty("test")
    private Boolean test;
    
    @JsonProperty("totalUnsettledSet")
    private MoneyBag totalUnsettledSet;
    
    @JsonProperty("user")
    private StaffMember user;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class TransactionFee {
    
    @JsonProperty("amount")
    private MoneyV2 amount;
    
    @JsonProperty("flatFee")
    private MoneyV2 flatFee;
    
    @JsonProperty("flatFeeName")
    private String flatFeeName;
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("rate")
    private String rate;
    
    @JsonProperty("rateName")
    private String rateName;
    
    @JsonProperty("taxAmount")
    private MoneyV2 taxAmount;
    
    @JsonProperty("type")
    private String type;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class PaymentDetails {
    
    @JsonProperty("avsResultCode")
    private String avsResultCode;
    
    @JsonProperty("creditCardBin")
    private String creditCardBin;
    
    @JsonProperty("creditCardCompany")
    private String creditCardCompany;
    
    @JsonProperty("creditCardExpirationMonth")
    private Integer creditCardExpirationMonth;
    
    @JsonProperty("creditCardExpirationYear")
    private Integer creditCardExpirationYear;
    
    @JsonProperty("creditCardName")
    private String creditCardName;
    
    @JsonProperty("creditCardNumber")
    private String creditCardNumber;
    
    @JsonProperty("creditCardWallet")
    private String creditCardWallet;
    
    @JsonProperty("cvvResultCode")
    private String cvvResultCode;
    
    @JsonProperty("paymentMethodName")
    private String paymentMethodName;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsTransactionSet {
    
    @JsonProperty("extendedAuthorizationSet")
    private ShopifyPaymentsExtendedAuthorization extendedAuthorizationSet;
    
    @JsonProperty("refundSet")
    private ShopifyPaymentsRefundSet refundSet;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsExtendedAuthorization {
    
    @JsonProperty("extendedAuthorizationExpiresAt")
    private DateTime extendedAuthorizationExpiresAt;
    
    @JsonProperty("standardAuthorizationExpiresAt")
    private DateTime standardAuthorizationExpiresAt;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsRefundSet {
    
    @JsonProperty("acquirerReferenceNumber")
    private String acquirerReferenceNumber;
}