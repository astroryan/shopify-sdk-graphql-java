package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A fulfillment hold on a fulfillment order.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentHold {
    /**
     * The reason for the hold.
     */
    @JsonProperty("reason")
    private FulfillmentHoldReason reason;
    
    /**
     * Additional details about the hold.
     */
    @JsonProperty("reasonNotes")
    private String reasonNotes;
}