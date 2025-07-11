package com.shopify.sdk.model.payment;

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
public class ShopifyPaymentsPayout {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("bankAccount")
    private ShopifyPaymentsBankAccount bankAccount;
    
    @JsonProperty("gross")
    private MoneyV2 gross;
    
    @JsonProperty("issuedAt")
    private DateTime issuedAt;
    
    @JsonProperty("legacyResourceId")
    private String legacyResourceId;
    
    @JsonProperty("net")
    private MoneyV2 net;
    
    @JsonProperty("status")
    private ShopifyPaymentsPayoutStatus status;
    
    @JsonProperty("summary")
    private ShopifyPaymentsPayoutSummary summary;
    
    @JsonProperty("transactionType")
    private ShopifyPaymentsPayoutTransactionType transactionType;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsBankAccount {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("country")
    private CountryCode country;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("currency")
    private CurrencyCode currency;
    
    @JsonProperty("status")
    private ShopifyPaymentsBankAccountStatus status;
    
    @JsonProperty("accountNumber")
    private String accountNumber;
    
    @JsonProperty("accountNumberLastDigits")
    private String accountNumberLastDigits;
    
    @JsonProperty("bankName")
    private String bankName;
    
    @JsonProperty("routingNumber")
    private String routingNumber;
}

public enum ShopifyPaymentsPayoutStatus {
    CANCELED,
    FAILED,
    IN_TRANSIT,
    PAID,
    PENDING,
    SCHEDULED
}

public enum ShopifyPaymentsBankAccountStatus {
    ERRORED,
    NEW,
    VALIDATED,
    VERIFIED
}

public enum ShopifyPaymentsPayoutTransactionType {
    DEPOSIT,
    WITHDRAWAL
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsPayoutSummary {
    
    @JsonProperty("adjustmentsFee")
    private MoneyV2 adjustmentsFee;
    
    @JsonProperty("adjustmentsGross")
    private MoneyV2 adjustmentsGross;
    
    @JsonProperty("chargesFee")
    private MoneyV2 chargesFee;
    
    @JsonProperty("chargesGross")
    private MoneyV2 chargesGross;
    
    @JsonProperty("refundsFee")
    private MoneyV2 refundsFee;
    
    @JsonProperty("refundsFeeGross")
    private MoneyV2 refundsFeeGross;
    
    @JsonProperty("reservedFundsFee")
    private MoneyV2 reservedFundsFee;
    
    @JsonProperty("reservedFundsGross")
    private MoneyV2 reservedFundsGross;
    
    @JsonProperty("retriedPayoutsFee")
    private MoneyV2 retriedPayoutsFee;
    
    @JsonProperty("retriedPayoutsGross")
    private MoneyV2 retriedPayoutsGross;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyPaymentsBalance {
    
    @JsonProperty("available")
    private List<MoneyV2> available;
    
    @JsonProperty("onHold")
    private List<MoneyV2> onHold;
    
    @JsonProperty("pending")
    private List<MoneyV2> pending;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyPaymentsAccount {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("activated")
    private Boolean activated;
    
    @JsonProperty("balance")
    private ShopifyPaymentsBalance balance;
    
    @JsonProperty("bankAccounts")
    private ShopifyPaymentsBankAccountConnection bankAccounts;
    
    @JsonProperty("chargeStatementDescriptor")
    private String chargeStatementDescriptor;
    
    @JsonProperty("chargeStatementDescriptors")
    private ShopifyPaymentsChargeStatementDescriptor chargeStatementDescriptors;
    
    @JsonProperty("country")
    private CountryCode country;
    
    @JsonProperty("defaultCurrency")
    private CurrencyCode defaultCurrency;
    
    @JsonProperty("fraudSettings")
    private ShopifyPaymentsFraudSettings fraudSettings;
    
    @JsonProperty("notificationSettings")
    private ShopifyPaymentsNotificationSettings notificationSettings;
    
    @JsonProperty("onboardable")
    private Boolean onboardable;
    
    @JsonProperty("payoutSchedule")
    private ShopifyPaymentsPayoutSchedule payoutSchedule;
    
    @JsonProperty("payoutStatementDescriptor")
    private String payoutStatementDescriptor;
    
    @JsonProperty("payouts")
    private ShopifyPaymentsPayoutConnection payouts;
    
    @JsonProperty("permittedVerificationDocuments")
    private List<ShopifyPaymentsVerificationDocument> permittedVerificationDocuments;
    
    @JsonProperty("verifications")
    private List<ShopifyPaymentsVerification> verifications;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsBankAccountConnection {
    
    @JsonProperty("edges")
    private List<ShopifyPaymentsBankAccountEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsBankAccountEdge {
    
    @JsonProperty("node")
    private ShopifyPaymentsBankAccount node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsChargeStatementDescriptor {
    
    @JsonProperty("default")
    private String defaultValue;
    
    @JsonProperty("prefix")
    private String prefix;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsFraudSettings {
    
    @JsonProperty("declineChargeOnAvsFailure")
    private Boolean declineChargeOnAvsFailure;
    
    @JsonProperty("declineChargeOnCvcFailure")
    private Boolean declineChargeOnCvcFailure;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsNotificationSettings {
    
    @JsonProperty("payouts")
    private Boolean payouts;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsPayoutSchedule {
    
    @JsonProperty("interval")
    private ShopifyPaymentsPayoutInterval interval;
    
    @JsonProperty("monthlyAnchor")
    private Integer monthlyAnchor;
    
    @JsonProperty("weeklyAnchor")
    private ShopifyPaymentsPayoutWeeklyAnchor weeklyAnchor;
}

public enum ShopifyPaymentsPayoutInterval {
    DAILY,
    MANUAL,
    MONTHLY,
    WEEKLY
}

public enum ShopifyPaymentsPayoutWeeklyAnchor {
    FRIDAY,
    MONDAY,
    SATURDAY,
    SUNDAY,
    THURSDAY,
    TUESDAY,
    WEDNESDAY
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsPayoutConnection {
    
    @JsonProperty("edges")
    private List<ShopifyPaymentsPayoutEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsPayoutEdge {
    
    @JsonProperty("node")
    private ShopifyPaymentsPayout node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsVerificationDocument {
    
    @JsonProperty("backRequired")
    private Boolean backRequired;
    
    @JsonProperty("type")
    private ShopifyPaymentsVerificationDocumentType type;
}

public enum ShopifyPaymentsVerificationDocumentType {
    BUSINESS_REGISTRATION,
    DRIVERS_LICENSE,
    GOVERNMENT_IDENTIFICATION,
    PASSPORT,
    VOIDED_CHECK
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsVerification {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("status")
    private ShopifyPaymentsVerificationStatus status;
    
    @JsonProperty("subject")
    private ShopifyPaymentsVerificationSubject subject;
}

public enum ShopifyPaymentsVerificationStatus {
    PENDING,
    SUCCESS,
    FAILED
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsVerificationSubject {
    
    @JsonProperty("familyName")
    private String familyName;
    
    @JsonProperty("givenName")
    private String givenName;
}