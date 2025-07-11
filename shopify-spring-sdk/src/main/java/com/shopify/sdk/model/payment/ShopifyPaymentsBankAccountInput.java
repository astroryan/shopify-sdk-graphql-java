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