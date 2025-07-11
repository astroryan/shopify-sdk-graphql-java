package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a collection of monetary values in their respective currencies
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoneyBag {
    /**
     * The shop money amount
     */
    @JsonProperty("shopMoney")
    private Money shopMoney;
    
    /**
     * The presentment money amount (customer's currency)
     */
    @JsonProperty("presentmentMoney")
    private Money presentmentMoney;
}