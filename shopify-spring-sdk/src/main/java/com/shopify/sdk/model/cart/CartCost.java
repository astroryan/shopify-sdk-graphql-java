package com.shopify.sdk.model.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartCost {
    
    @JsonProperty("checkoutChargeAmount")
    private Money checkoutChargeAmount;
    
    @JsonProperty("subtotalAmount")
    private Money subtotalAmount;
    
    @JsonProperty("subtotalAmountEstimated")
    private Boolean subtotalAmountEstimated;
    
    @JsonProperty("totalAmount")
    private Money totalAmount;
    
    @JsonProperty("totalAmountEstimated")
    private Boolean totalAmountEstimated;
    
    @JsonProperty("totalDutyAmount")
    private Money totalDutyAmount;
    
    @JsonProperty("totalDutyAmountEstimated")
    private Boolean totalDutyAmountEstimated;
    
    @JsonProperty("totalTaxAmount")
    private Money totalTaxAmount;
    
    @JsonProperty("totalTaxAmountEstimated")
    private Boolean totalTaxAmountEstimated;
}