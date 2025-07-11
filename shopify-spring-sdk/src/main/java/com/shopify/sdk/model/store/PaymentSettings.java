package com.shopify.sdk.model.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Payment settings for a shop.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSettings {
    /**
     * The digital wallets supported by the shop
     */
    private List<DigitalWallet> supportedDigitalWallets;
}