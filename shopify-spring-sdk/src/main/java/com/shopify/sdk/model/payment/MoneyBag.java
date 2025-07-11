package com.shopify.sdk.model.payment;

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
public class MoneyBag {
    
    @JsonProperty("presentmentMoney")
    private MoneyInput presentmentMoney;
    
    @JsonProperty("shopMoney")
    private MoneyInput shopMoney;
}