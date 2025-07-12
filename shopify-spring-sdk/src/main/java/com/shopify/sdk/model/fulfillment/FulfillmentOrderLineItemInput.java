package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for specifying a fulfillment order line item.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentOrderLineItemInput {
    /**
     * The ID of the fulfillment order line item.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The quantity to be fulfilled.
     */
    @JsonProperty("quantity")
    private Integer quantity;
}