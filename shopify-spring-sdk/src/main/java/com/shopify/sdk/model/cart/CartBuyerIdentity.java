package com.shopify.sdk.model.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.CountryCode;
import com.shopify.sdk.model.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartBuyerIdentity {
    
    @JsonProperty("customer")
    private Customer customer;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("phone")
    private String phone;
    
    @JsonProperty("countryCode")
    private CountryCode countryCode;
    
    @JsonProperty("deliveryAddressPreferences")
    private List<DeliveryAddressInput> deliveryAddressPreferences;
}