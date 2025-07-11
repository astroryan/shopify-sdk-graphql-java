package com.shopify.sdk.model.order.response;

import com.shopify.sdk.model.common.UserError;
import com.shopify.sdk.model.order.Fulfillment;
import lombok.*;

import java.util.List;

/**
 * Response for fulfillment cancellation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentCancelResponse {
    private Fulfillment fulfillment;
    private List<UserError> userErrors;
}