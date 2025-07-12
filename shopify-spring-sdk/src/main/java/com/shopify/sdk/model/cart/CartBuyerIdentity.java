package com.shopify.sdk.model.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.customer.Customer;
import com.shopify.sdk.model.common.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the buyer identity for a cart.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartBuyerIdentity {
    /**
     * The customer.
     */
    @JsonProperty("customer")
    private Customer customer;
    
    /**
     * The email address.
     */
    @JsonProperty("email")
    private String email;
    
    /**
     * The phone number.
     */
    @JsonProperty("phone")
    private String phone;
    
    /**
     * The country code.
     */
    @JsonProperty("countryCode")
    private String countryCode;
    
    /**
     * The delivery address preferences.
     */
    @JsonProperty("deliveryAddressPreferences")
    private CartDeliveryAddressPreferences deliveryAddressPreferences;
}