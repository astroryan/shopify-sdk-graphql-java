package com.shopify.sdk.model.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents delivery address preferences for a cart.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDeliveryAddressPreferences {
    /**
     * The delivery addresses.
     */
    @JsonProperty("deliveryAddress")
    private List<Address> deliveryAddress;
}