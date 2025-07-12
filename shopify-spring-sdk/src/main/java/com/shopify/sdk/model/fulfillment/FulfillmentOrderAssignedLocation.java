package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The assigned location for a fulfillment order.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentOrderAssignedLocation {
    /**
     * The ID of the location.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The name of the location.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The address of the location.
     */
    @JsonProperty("address")
    private Address address;
}