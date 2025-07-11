package com.shopify.sdk.model.markets;

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
public class MoneyV2 {
    
    @JsonProperty("amount")
    private String amount;
    
    @JsonProperty("currencyCode")
    private String currencyCode;
}
