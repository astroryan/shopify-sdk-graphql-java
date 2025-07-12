package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The kind of merchant request.
 */
public enum FulfillmentOrderMerchantRequestKind {
    @JsonProperty("FULFILLMENT_REQUEST")
    FULFILLMENT_REQUEST,
    
    @JsonProperty("CANCELLATION_REQUEST")
    CANCELLATION_REQUEST
}