package com.shopify.sdk.model.checkout;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.ID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Represents checkout branding border configuration
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingBorder {
    private CheckoutBrandingBorderStyle style;
    private String color;
    private Integer width;
}
