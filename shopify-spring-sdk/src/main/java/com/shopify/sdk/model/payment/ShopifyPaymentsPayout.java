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
