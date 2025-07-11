package com.shopify.sdk.model.order.response;

import lombok.*;

/**
 * Response data wrapper for fulfillment creation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentCreateData {
    private FulfillmentCreateResponse fulfillmentCreate;
}