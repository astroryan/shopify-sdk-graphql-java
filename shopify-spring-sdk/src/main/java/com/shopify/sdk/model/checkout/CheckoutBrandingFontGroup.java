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
public class CheckoutBrandingFontGroup {
    
    @JsonProperty("base")
    private CheckoutBrandingFont base;
    
    @JsonProperty("bold")
    private CheckoutBrandingFont bold;
    
    @JsonProperty("loadingStrategy")
    private CheckoutBrandingFontLoadingStrategy loadingStrategy;
    
    @JsonProperty("name")
    private String name;
}
