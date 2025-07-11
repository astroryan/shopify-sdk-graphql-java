package com.shopify.sdk.model.localization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketLocalization {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("locale")
    private String locale;
    
    @JsonProperty("isVisible")
    private Boolean isVisible;
    
    @JsonProperty("isPrimary")
    private Boolean isPrimary;
}
