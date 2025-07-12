package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.MoneyV2;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents a payout from Shopify Payments to a bank account.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyPaymentsPayout implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The bank account the payout is sent to.
     */
    @JsonProperty("bankAccount")
    private ShopifyPaymentsBankAccount bankAccount;
    
    /**
     * The gross amount of the payout.
     */
    @JsonProperty("gross")
    private MoneyV2 gross;
    
    /**
     * The date and time when the payout was issued.
     */
    @JsonProperty("issuedAt")
    private OffsetDateTime issuedAt;
    
    /**
     * The legacy resource ID.
     */
    @JsonProperty("legacyResourceId")
    private String legacyResourceId;
    
    /**
     * The net amount of the payout.
     */
    @JsonProperty("net")
    private MoneyV2 net;
    
    /**
     * The status of the payout.
     */
    @JsonProperty("status")
    private ShopifyPaymentsPayoutStatus status;
    
    /**
     * The summary of the payout.
     */
    @JsonProperty("summary")
    private ShopifyPaymentsPayoutSummary summary;
    
    /**
     * The transaction type of the payout.
     */
    @JsonProperty("transactionType")
    private ShopifyPaymentsPayoutTransactionType transactionType;
}


/**
 * The summary of a Shopify Payments payout.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsPayoutSummary {
    /**
     * The adjustments fee amount.
     */
    @JsonProperty("adjustmentsFee")
    private MoneyV2 adjustmentsFee;
    
    /**
     * The adjustments gross amount.
     */
    @JsonProperty("adjustmentsGross")
    private MoneyV2 adjustmentsGross;
    
    /**
     * The charges fee amount.
     */
    @JsonProperty("chargesFee")
    private MoneyV2 chargesFee;
    
    /**
     * The charges gross amount.
     */
    @JsonProperty("chargesGross")
    private MoneyV2 chargesGross;
    
    /**
     * The refunds fee amount.
     */
    @JsonProperty("refundsFee")
    private MoneyV2 refundsFee;
    
    /**
     * The refunds fee gross amount.
     */
    @JsonProperty("refundsFeeGross")
    private MoneyV2 refundsFeeGross;
    
    /**
     * The reserved funds fee amount.
     */
    @JsonProperty("reservedFundsFee")
    private MoneyV2 reservedFundsFee;
    
    /**
     * The reserved funds gross amount.
     */
    @JsonProperty("reservedFundsGross")
    private MoneyV2 reservedFundsGross;
    
    /**
     * The retried payouts fee amount.
     */
    @JsonProperty("retriedPayoutsFee")
    private MoneyV2 retriedPayoutsFee;
    
    /**
     * The retried payouts gross amount.
     */
    @JsonProperty("retriedPayoutsGross")
    private MoneyV2 retriedPayoutsGross;
}

