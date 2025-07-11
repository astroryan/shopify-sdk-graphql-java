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
public class ShopifyPaymentsFraudSettingsInput {
    
    @JsonProperty("declineChargeOnAvsFailure")
    private Boolean declineChargeOnAvsFailure;
    
    @JsonProperty("declineChargeOnCvcFailure")
    private Boolean declineChargeOnCvcFailure;
}