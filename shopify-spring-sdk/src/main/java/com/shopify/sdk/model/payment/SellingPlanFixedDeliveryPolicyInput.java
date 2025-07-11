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
public class SellingPlanFixedDeliveryPolicyInput {
    
    @JsonProperty("anchors")
    private List<SellingPlanAnchorInput> anchors;
    
    @JsonProperty("cutoff")
    private String cutoff;
    
    @JsonProperty("fulfillmentExactTime")
    private String fulfillmentExactTime;
    
    @JsonProperty("fulfillmentTrigger")
    private SellingPlanFulfillmentTrigger fulfillmentTrigger;
    
    @JsonProperty("intent")
    private SellingPlanFixedDeliveryPolicyIntent intent;
    
    @JsonProperty("preAnchorBehavior")
    private SellingPlanFixedDeliveryPolicyPreAnchorBehavior preAnchorBehavior;
}