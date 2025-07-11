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
public class RefundLineItem {
    
    @JsonProperty("lineItemId")
    private String lineItemId;
    
    @JsonProperty("locationId")
    private String locationId;
    
    @JsonProperty("quantity")
    private Integer quantity;
    
    @JsonProperty("restockType")
    private RefundLineItemRestockType restockType;
}