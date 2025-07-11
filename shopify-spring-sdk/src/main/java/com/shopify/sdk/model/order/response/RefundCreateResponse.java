package com.shopify.sdk.model.order.response;

import com.shopify.sdk.model.common.UserError;
import com.shopify.sdk.model.order.Refund;
import lombok.*;

import java.util.List;

/**
 * Response for refund creation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundCreateResponse {
    private Refund refund;
    private List<UserError> userErrors;
}