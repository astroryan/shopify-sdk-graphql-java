package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A merchant request for a fulfillment order.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentOrderMerchantRequest {
    /**
     * The globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The kind of request.
     */
    @JsonProperty("kind")
    private FulfillmentOrderMerchantRequestKind kind;
    
    /**
     * The message from the merchant.
     */
    @JsonProperty("message")
    private String message;
}