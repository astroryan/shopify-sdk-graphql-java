package com.shopify.sdk.model.payment;

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
public class ShopifyPaymentsBalance {
    
    @JsonProperty("available")
    private List<MoneyV2> available;
    
    @JsonProperty("onHold")
    private List<MoneyV2> onHold;
    
    @JsonProperty("pending")
    private List<MoneyV2> pending;
}