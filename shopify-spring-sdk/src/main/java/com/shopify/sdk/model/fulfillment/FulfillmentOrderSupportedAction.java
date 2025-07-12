package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The supported actions for a fulfillment order.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentOrderSupportedAction {
    /**
     * The action type.
     */
    @JsonProperty("action")
    private FulfillmentOrderAction action;
    
    /**
     * The external URL to be used for the action.
     */
    @JsonProperty("externalUrl")
    private String externalUrl;
}