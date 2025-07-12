package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * A bank account for Shopify Payments.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyPaymentsBankAccount {
    /**
     * The globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The country of the bank.
     */
    @JsonProperty("country")
    private String country;
    
    /**
     * The currency of the bank account.
     */
    @JsonProperty("currency")
    private String currency;
    
    /**
     * The last four digits of the account number.
     */
    @JsonProperty("accountNumberLastDigits")
    private String accountNumberLastDigits;
    
    /**
     * The name of the bank.
     */
    @JsonProperty("bankName")
    private String bankName;
    
    /**
     * The routing number of the bank.
     */
    @JsonProperty("routingNumber")
    private String routingNumber;
    
    /**
     * The status of the bank account.
     */
    @JsonProperty("status")
    private String status;
    
    /**
     * When the bank account was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
}