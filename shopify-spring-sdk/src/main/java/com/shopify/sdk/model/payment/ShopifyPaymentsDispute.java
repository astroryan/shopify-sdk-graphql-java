package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import com.shopify.sdk.model.order.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyPaymentsDispute {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("amount")
    private MoneyV2 amount;
    
    @JsonProperty("evidenceDueBy")
    private Date evidenceDueBy;
    
    @JsonProperty("evidenceSentOn")
    private Date evidenceSentOn;
    
    @JsonProperty("finalizedOn")
    private Date finalizedOn;
    
    @JsonProperty("initiatedAt")
    private DateTime initiatedAt;
    
    @JsonProperty("legacyResourceId")
    private String legacyResourceId;
    
    @JsonProperty("networkReasonCode")
    private String networkReasonCode;
    
    @JsonProperty("order")
    private Order order;
    
    @JsonProperty("reasonDetails")
    private ShopifyPaymentsDisputeReasonDetails reasonDetails;
    
    @JsonProperty("status")
    private DisputeStatus status;
    
    @JsonProperty("type")
    private DisputeType type;
}
