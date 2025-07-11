package com.shopify.sdk.model.checkout;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.ID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutBrandingColorSchemes {
    
    @JsonProperty("scheme1")
    private CheckoutBrandingColorScheme scheme1;
    
    @JsonProperty("scheme2")
    private CheckoutBrandingColorScheme scheme2;
}
