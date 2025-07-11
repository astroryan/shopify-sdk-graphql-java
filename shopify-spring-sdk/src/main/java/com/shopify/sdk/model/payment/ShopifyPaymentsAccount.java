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