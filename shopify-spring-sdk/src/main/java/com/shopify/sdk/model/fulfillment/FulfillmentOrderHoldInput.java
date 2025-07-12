package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for holding a fulfillment order.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentOrderHoldInput {
    /**
     * The reason for the fulfillment hold.
     */
    @JsonProperty("reason")
    private FulfillmentHoldReason reason;
    
    /**
     * Additional details about the fulfillment hold reason.
     */
    @JsonProperty("reasonNotes")
    private String reasonNotes;
    
    /**
     * Whether to send a notification to the merchant about the fulfillment hold.
     */
    @JsonProperty("notifyMerchant")
    private Boolean notifyMerchant;
    
    /**
     * A configurable ID used to track the automation system that issued the hold.
     */
    @JsonProperty("externalId")
    private String externalId;
}