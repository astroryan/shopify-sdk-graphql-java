package com.shopify.sdk.model.order.response;

import lombok.*;

/**
 * Response data wrapper for fulfillment cancellation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentCancelData {
    private FulfillmentCancelResponse fulfillmentCancel;
}