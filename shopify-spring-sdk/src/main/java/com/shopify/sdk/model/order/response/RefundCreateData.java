package com.shopify.sdk.model.order.response;

import lombok.*;

/**
 * Response data wrapper for refund creation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundCreateData {
    private RefundCreateResponse refundCreate;
}