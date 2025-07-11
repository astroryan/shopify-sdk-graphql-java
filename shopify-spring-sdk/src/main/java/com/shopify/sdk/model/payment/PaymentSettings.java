package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSettings {
    
    @JsonProperty("supportedDigitalWallets")
    private List<DigitalWallet> supportedDigitalWallets;
}